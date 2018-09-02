package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.EmployeeInfoService;
import com.oliveoa.greendao.EmployeeInfoDao;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.EmployeeInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.util.EntityManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EmployeeinfoActivity extends AppCompatActivity {

    private ImageView back,edit;
    private TextView tname,dname,pname,id,sex,birth,tel,email,address;

    private String TAG = this.getClass().getSimpleName();
    private ArrayList<EmployeeInfo> employeeInfos;
    private ArrayList<DepartmentInfo> departmentInfos;
    private ArrayList<DutyInfo> dutynfos;
    private EmployeeInfo employeeInfo;
    private String departmentname,positionname;

    private RelativeLayout pacmanIndicator_layout;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private Handler mHandler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    //在这里可以进行UI操作
                    avLoadingIndicatorView.hide();
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeeinfo);

        employeeInfo = getIntent().getParcelableExtra("ep");
        positionname = getIntent().getStringExtra("pname");
        departmentname = getIntent().getStringExtra("dname");

        Log.d(TAG,"ParcelableEmployee:"+ employeeInfo.toString()+"pname:"+positionname+"dname:"+departmentname);
        initData();
    }
    public void initData(){

        initView();
    }

    public void initView(){
        pacmanIndicator_layout = (RelativeLayout)findViewById(R.id.pacmanIndicator_layout);

        back =(ImageView)findViewById(R.id.info_back);
        edit =(ImageView)findViewById(R.id.info_edit);

        tname =(TextView) findViewById(R.id.content_name);
        dname = (TextView)findViewById(R.id.content_dcid);
        pname =(TextView) findViewById(R.id.content_pcid);
        id =(TextView)findViewById(R.id.content_id);
        sex = (TextView)findViewById(R.id.content_sex);
        birth =(TextView)findViewById(R.id.content_birth);
        tel =(TextView)findViewById(R.id.content_tel);
        email = (TextView)findViewById(R.id.content_email);
        address =(TextView)findViewById(R.id.content_address);


        tname.setText(employeeInfo.getName());
        dname.setText(departmentname);
        pname.setText(positionname);
        id.setText(employeeInfo.getId());
        sex.setText(employeeInfo.getSex());
        birth.setText(employeeInfo.getBirth());
        tel.setText(employeeInfo.getTel());
        email.setText(employeeInfo.getEmail());
        address.setText(employeeInfo.getAddress());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();

                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

    }

    /**
     *   MethodName： back()
     *   Description： 返回员工列表
     *   @Author： Erica
     */
    private void back() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        pacmanIndicator_layout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avLoadingIndicatorView.show();

        Handler mHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);

                EmployeeInfoService employeeInfoService = new EmployeeInfoService();
                EmployeeInfoDao employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();

                if (departmentInfoJsonBean.getStatus()==0) {
                    departmentInfos = departmentInfoJsonBean.getData();
                    employeeInfoDao.deleteAll();
                    for(int i= 0;i<departmentInfos.size();i++){

                        EmployeeInfoJsonBean employeeInfoJsonBean = employeeInfoService.employeeinfo(s,departmentInfos.get(i).getDcid());
                        if(employeeInfoJsonBean.getStatus()==0){
                            employeeInfos = employeeInfoJsonBean.getData();
                            for(int j =0;j<employeeInfos.size();j++){
                                employeeInfoDao.insert(employeeInfos.get(j));
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), employeeInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    mHandler1.sendEmptyMessage(2);
                    Intent intent = new Intent(EmployeeinfoActivity.this, EmployeelistActivity.class);
                    intent.putParcelableArrayListExtra("alldp",departmentInfos);
                    //intent.putParcelableArrayListExtra("allep",employeeInfos);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(), departmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG,"uiThread2------"+Thread.currentThread());//子线程
            }
        };

        Log.d(TAG,"uiThread1------"+Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);
    }

    /**
     *   MethodName： edit()
     *   Description： 编辑指定员工信息
     *   @Author： Erica
     */
    public void edit(){
        Log.e(TAG,"editEpinfo="+employeeInfo.toString());
        Intent intent = new Intent(EmployeeinfoActivity.this, EditEmployeeinfoActivity.class);
        intent.putExtra("ep",employeeInfo);
        intent.putExtra("dname",departmentname);
        intent.putExtra("pname",positionname);
        intent.putExtra("index",0);
        startActivity(intent);
        finish();
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
