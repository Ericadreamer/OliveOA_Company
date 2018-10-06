package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DepartmentInfoActivity extends AppCompatActivity {

    private DepartmentInfo departmentInfo;
    private List<DutyInfo> dutyInfo;
    private String dpname;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDutylistView;
    private TextView tname,tnum;
    private DepartmentInfoDao departmentInfoDao;
    private DutyInfoDao dutyInfoDao;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //在这里可以进行UI操作
                    addDutylistView.removeView((View) msg.obj);
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_info);

        departmentInfo = getIntent().getParcelableExtra("dp");
        dpname = getIntent().getStringExtra("dpname");
        dutyInfo = getIntent().getParcelableArrayListExtra("alldt");
        Log.e(TAG,"dp:::"+dpname);
        initData();
      }

    //初始化
    public void initData(){
        //方便添加职务用到dp传值
        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        departmentInfoDao.deleteAll();
        departmentInfoDao.insert(departmentInfo);

        dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();

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
                back();
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
        tdpid.setText(dpname);
    }

   //返回部门列表操作
    public void back(){
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Handler mHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");
                DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);
                if (departmentInfoJsonBean.getStatus()==0) {
                    ArrayList<DepartmentInfo> departmentInfos = departmentInfoJsonBean.getData();
                    Intent intent = new Intent(DepartmentInfoActivity.this, DepartmentActivity.class);
                    intent.putParcelableArrayListExtra("alldp",departmentInfos);
                    startActivity(intent);
                    finish();
                }

                Log.d(TAG,"uiThread2------"+Thread.currentThread());//子线程
            }
        };

        Log.d(TAG,"uiThread1------"+Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);
    }

    //编辑部门操作
    public void edit() {
        Intent intent = new Intent(DepartmentInfoActivity.this, RedactDepartmentActivity.class);
        intent.putExtra("dp",departmentInfo);
        intent.putExtra("dpname",dpname);
        intent.putExtra("index",0);
        startActivity(intent);
        finish();

    }

    //添加职务操作
    public void dutyadd() {

        DutyInfo dt = new DutyInfo();
        dt.setDcid(departmentInfo.getDcid());
        dt.setPpid("");
        dutyInfoDao.deleteAll();
        dutyInfoDao.insert(dt);

        Intent intent = new Intent(DepartmentInfoActivity.this, AddDutyActivity.class);
        intent.putExtra("index",0);
        intent.putExtra("dtname","");//上级职务
        //intent.putExtra("dpname",dpname);//上级部门
        startActivity(intent);
        finish();

    }

    //删除职务操作
    public void dutydelete(final String pcid, final View childAt){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");
                DutyInfoService dutyInfoService = new DutyInfoService();
                StatusAndMsgJsonBean statusAndMsgJsonBean = dutyInfoService.deleteduty(s,pcid);
                Log.d("delete", statusAndMsgJsonBean.getMsg() + "");
                if (statusAndMsgJsonBean.getStatus() == 0) {
                    //新建一个Message对象，存储需要发送的消息
                    Message message=new Message();
                    message.what=1;
                    message.obj = childAt;
                    //然后将消息发送出去
                    handler.sendMessage(message);
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "删除成功！点击返回键返回首页", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                } else {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();
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
                    String dutyname = tname.getText().toString().trim();
                    Log.i("dutyname=",dutyname);
                    for (int j=0;j<dutyInfo.size();j++) {
                        if(dutyname.equals(dutyInfo.get(j).getName())){
                            dutydelete(dutyInfo.get(j).getPcid(),childAt);
                            break;
                        }
                    }
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
                    String dtname = "";
                    Log.e(TAG,"alldt:"+dutyInfo);
                    for(int j =0;j<dutyInfo.size();j++){
                        if(dutyname.equals(dutyInfo.get(j).getName())){
                            if(dutyInfo.get(j).getPpid()!=null) {
                                for (int k = 0; k < dutyInfo.size(); k++) {
                                    if (dutyInfo.get(j).getPpid().equals(dutyInfo.get(k).getPcid())) {
                                        dtname = dutyInfo.get(k).getName();
                                        break;
                                    }
                                }
                                if (dtname == "") {
                                    dtname = "无";
                                }
                            }else{
                                dtname = "无";
                            }
                            Log.e(TAG,"dtname="+dtname);
                            Log.e(TAG,"dutyInfo.get(j)="+dutyInfo.get(j));
                            Intent intent = new Intent(DepartmentInfoActivity.this, DutyInfoActivity.class);
                            intent.putExtra("dtname",dtname);
                            intent.putExtra("dpname",dpname);
                            intent.putExtra("dt",dutyInfo.get(j));
                            startActivity(intent);
                            finish();
                        }
                    }


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
