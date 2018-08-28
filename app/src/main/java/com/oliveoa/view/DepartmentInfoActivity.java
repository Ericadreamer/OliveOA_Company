package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DepartmentInfoActivity extends AppCompatActivity {

    private DepartmentInfo departmentInfo;
    private List<DutyInfo> dutyInfo;
    private String id,dname;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDutylistView;
    private TextView tname,tnum;
    private DepartmentInfoDao departmentInfoDao;
    private DutyInfoDao dutyInfoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_info);
        id = getIntent().getStringExtra("id");//部门索引
        Log.e("Tid",id);
        initData();
      }

    //初始化
    public void initData(){
        departmentInfoDao = EntityManager.getInstance().departmentInfoDao;
        departmentInfo = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(id)).unique();
        Log.e("department::::",departmentInfo.toString());


        if(departmentInfo.getDpid()==null){
            dname ="无";
        }else{
            dname=  departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(departmentInfo.getDpid())).unique().getName();
        }

        dutyInfoDao = EntityManager.getInstance().dutyInfoDao;
        dutyInfo = dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Dcid.eq(id)).list();

        initView();
    }
    public void initView() {
        ImageView back = (ImageView)findViewById(R.id.null_back);
        TextView edit = (TextView)findViewById(R.id.depart_edit);
       // TextView add = (TextView)findViewById(R.id.duty_add);
        TextView dutyadd = (TextView)findViewById(R.id.duty_add);
        addDutylistView = (LinearLayout)findViewById(R.id.duty_list);

      
        //默认添加一个Item
        addViewItem(null);

       //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentInfoActivity.this, DepartmentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

        dutyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dutyadd();
            }
        });

        TextView tid = (TextView) findViewById(R.id.text_num);
        tid.setText(departmentInfo.getId());
        TextView tname = (TextView) findViewById(R.id.text_name);
        tname.setText(departmentInfo.getName());
        TextView ttelephone = (TextView) findViewById(R.id.text_tel);
        ttelephone.setText(departmentInfo.getTelephone());
        TextView tfax = (TextView) findViewById(R.id.text_fax);
        tfax.setText(departmentInfo.getFax());
        TextView tdpid = (TextView) findViewById(R.id.text_superior);
        tdpid.setText(dname);
    }

    //编辑部门操作
    public void edit() {
        Intent intent = new Intent(DepartmentInfoActivity.this, RedactDepartmentActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
        finish();

    }

    //添加职务操作
    public void dutyadd() {

        DutyInfo dt = new DutyInfo();
        dt.setDcid(departmentInfo.getDcid());
        dutyInfoDao.insert(dt);

        Intent intent = new Intent(DepartmentInfoActivity.this, AddDutyActivity.class);
        intent.putExtra("index",0);

        startActivity(intent);
        finish();

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addDutylistView.getChildCount(); i++) {
            final View childAt = addDutylistView.getChildAt(i);
            //删除操作
            final Button btn_remove = (Button) childAt.findViewById(R.id.btnDelete);
            btn_remove.setTag("remove");//设置删除标记
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    tname = (TextView)childAt.findViewById(R.id.duty_name);
                    int i ; final int f;
                    String dutyname = tname.getText().toString().trim();
                    Log.i("dutyname=",dutyname);
                    for (i=0;i<dutyInfo.size();i++) {
                        if(dutyname.equals(dutyInfo.get(i).getName())){
                            break;
                        }
                    }
                    f=i;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                            String s = pref.getString("sessionid", "");

                            DutyInfoService dutyInfoService = new DutyInfoService();
                            StatusAndMsgJsonBean statusAndMsgJsonBean = dutyInfoService.deleteduty(s,dutyInfo.get(f).getPcid());
                            Log.d("delete", statusAndMsgJsonBean.getMsg() + "");
                            if (statusAndMsgJsonBean.getStatus() == 0) {
                                dutyInfo.remove(dutyInfo.get(f));
                                dutyInfoDao.deleteAll();
                                for(int i=0;i<dutyInfo.size();i++){
                                    dutyInfoDao.insertOrReplace(dutyInfo.get(i));
                                }

                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "删除成功！点击返回键返回部门列表", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                addDutylistView.removeView(childAt);
                            } else {
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();
                }

            });
            //转到详情页面
            Button btn_info = (Button) childAt.findViewById(R.id.btnEdit);
            btn_info.setTag("btnEdit");
            btn_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tname = (TextView)childAt.findViewById(R.id.duty_name);

                    String dutyname = tname.getText().toString().trim();

                    Intent intent = new Intent(DepartmentInfoActivity.this, DutyInfoActivity.class);
                    intent.putExtra("dutyname",dutyname);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (dutyInfo != null) {///如果有部门则按数组大小加载布局
            for(int i = 0;i <dutyInfo.size(); i ++){
                View hotelEvaluateView = View.inflate(this, R.layout.activity_department_info_dutyitem, null);
                addDutylistView.addView(hotelEvaluateView);
                InitDataViewItem();

            }
            sortHotelViewItem();

        }
    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {
        int i;
        for (i = 0; i < addDutylistView.getChildCount(); i++) {
            View childAt = addDutylistView.getChildAt(i);
            tname= (TextView)childAt.findViewById(R.id.duty_name);
            tnum = (TextView)childAt.findViewById(R.id.duty_num);

            tname.setText(dutyInfo.get(i).getName());
            tnum.setText(String.valueOf(dutyInfo.get(i).getLimit()));
        }
        Log.e(TAG, "职务名称：" + tname.getText().toString() + "-----人数限制："
                + tnum.getText().toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
            //调用双击退出函数
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 双击退出函数
     */
    private static Boolean isESC = false;

    private void exitBy2Click() {
        Timer tExit ;
        if (!isESC) {
            isESC = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isESC = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            System.exit(0);
        }
    }
}
