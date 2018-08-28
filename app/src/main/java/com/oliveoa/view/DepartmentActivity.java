package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
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
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.DBOpreator;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DepartmentActivity extends AppCompatActivity {

    private List<DepartmentInfo> departmentInfos;
    private ImageView back,add;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDPlistView;
    private TextView tvname,tvid;
    private DepartmentInfoDao departmentInfoDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        initData();
    }
    public void initData(){
        departmentInfoDao = EntityManager.getInstance().departmentInfoDao;
        departmentInfos = departmentInfoDao.queryBuilder().orderAsc(DepartmentInfoDao.Properties.Orderby).list();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //读取SharePreferences的cookies
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                DutyInfoDao dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
                DutyInfoService dutyInfoService = new DutyInfoService();
                DBOpreator dbOpreator = new DBOpreator();
                dutyInfoDao.deleteAll();
                Log.e(TAG,"  dutyInfoDao.deleteAll()");
                for (int i = 0; i < departmentInfos.size(); i++) {
                    DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s, departmentInfos.get(i).getDcid());
                    if (dutyInfoJsonBean.getStatus() == 0) {
                        ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                        dbOpreator.DutyDaoUpdate(dutyInfos);

                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "网络错误，获取职务信息失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                }
            }
        });

        initView();
    }
    public void initView(){
        back = (ImageView)findViewById(R.id.null_back);
        add = (ImageView)findViewById(R.id.null_add);
        //depart_list =(LinearLayout)findViewById(R.id.depart_list);
        addDPlistView = (LinearLayout)findViewById(R.id.depart_list);

        //默认添加一个Item
        addViewItem(null);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DepartmentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentActivity.this, CreateDepartmentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addDPlistView.getChildCount(); i++) {
            final View childAt = addDPlistView.getChildAt(i);
            //删除操作
//            final Button btn_remove = (Button) childAt.findViewById(R.id.btnDelete);
//            btn_remove.setTag("remove");//设置删除标记
//            btn_remove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //从LinearLayout容器中删除当前点击到的ViewItem
//                    addDPlistView.removeView(childAt);
//                }
//
//            });
            //转到详情页面
            Button btn_info = (Button) childAt.findViewById(R.id.btnInfo);
            btn_info.setTag("edit");
            btn_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvid = (TextView)childAt.findViewById(R.id.did);
                    String id = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Id.eq(tvid.getText().toString().trim().substring(3))).unique().getDcid();
                    Intent intent = new Intent(DepartmentActivity.this, DepartmentInfoActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (departmentInfos == null) {//如果部门列表为0，加载空布局
            View hotelEvaluateView = View.inflate(this, R.layout.activity_null_department, null);
            addDPlistView.addView(hotelEvaluateView);
            //sortHotelViewItem();
        } else {//如果有部门则按数组大小加载布局
            for(int i = 0;i <departmentInfos.size(); i ++){
                View hotelEvaluateView = View.inflate(this, R.layout.activity_department_listitem, null);
                addDPlistView.addView(hotelEvaluateView);
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
        for (i = 0; i < addDPlistView.getChildCount(); i++) {
            View childAt = addDPlistView.getChildAt(i);
            tvname = (TextView)childAt.findViewById(R.id.dname);
            tvid = (TextView)childAt.findViewById(R.id.did);

            tvname.setText(departmentInfos.get(i).getName());
            tvid.setText("编号："+departmentInfos.get(i).getId());
        }
        Log.e(TAG, "部门名称：" + tvname.getText().toString() + "-----部门编号："
                + tvid.getText().toString());
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
