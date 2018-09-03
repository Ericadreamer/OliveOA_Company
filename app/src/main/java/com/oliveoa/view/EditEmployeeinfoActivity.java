package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.util.EntityManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

import static com.oliveoa.util.Validator.isEmail;
import static com.oliveoa.util.Validator.isMobile;


public class EditEmployeeinfoActivity extends AppCompatActivity {

    private ArrayList<EmployeeInfo> employeeInfos;
    private ArrayList<DepartmentInfo> departmentInfos;
    private ArrayList<DutyInfo> dutyInfos;

    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private EmployeeInfo employeeInfo;

    private TextView dpcid, sex, birth;
    private EditText ename, etel, eemail, eaddress;
    private TextView tid;
    private ImageView back, save;

    private String dname,pname;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    private EmployeeInfoDao employeeInfoDao;
    private RelativeLayout pacmanIndicator_layout;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private Handler mHandler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    //在这里可以进行UI操作

                    break;
                default:
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeeinfo_edit);

        employeeInfo = getIntent().getParcelableExtra("ep");
        index = getIntent().getIntExtra("index",index);//详情为0，选择为1
        dname = getIntent().getStringExtra("dname");
        pname = getIntent().getStringExtra("pname");

        Log.d(TAG,"dname="+dname+"pname="+pname);
        Log.d("ParcelableEmployee", employeeInfo.toString());

        initview();
    }
    public void initData() {
        ename.setText(employeeInfo.getName());
        tid.setText(employeeInfo.getId());
        etel.setText(employeeInfo.getTel());
        eemail.setText(employeeInfo.getEmail());
        eaddress.setText(employeeInfo.getAddress());
        dpcid.setText(dname + ":" + pname);
        sex.setText(employeeInfo.getSex());
        birth.setText(employeeInfo.getBirth());

    }
    public void initview() {
        back = (ImageView) findViewById(R.id.info_back);
        save = (ImageView) findViewById(R.id.info_save);
        pacmanIndicator_layout = (RelativeLayout)findViewById(R.id.pacmanIndicator_layout);

        dpcid = (TextView) findViewById(R.id.content_dpcid);
        sex = (TextView) findViewById(R.id.content_sex);
        birth = (TextView) findViewById(R.id.content_birth);
        tid = (TextView) findViewById(R.id.content_id);

        ename = (EditText) findViewById(R.id.content_name);
        etel = (EditText) findViewById(R.id.content_tel);
        eemail = (EditText) findViewById(R.id.content_email);
        eaddress = (EditText) findViewById(R.id.content_address);



        initData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditEmployeeinfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出编辑,直接返回员工列表页面？");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        back();
                    }
                });
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });


    }
    public void back(){
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        pacmanIndicator_layout.setVisibility(View.VISIBLE);

        avLoadingIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.avi);
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
                    Intent intent = new Intent(EditEmployeeinfoActivity.this, EmployeelistActivity.class);
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


    public void save() {
        //Toast.makeText(mContext, "成功保存", Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(ename.getText().toString().trim()) && TextUtils.isEmpty(sex.getText().toString().trim())
                || TextUtils.isEmpty(etel.getText().toString().trim()) || TextUtils.isEmpty(birth.getText().toString().trim())
                || TextUtils.isEmpty(eemail.getText().toString().trim()) || TextUtils.isEmpty(eaddress.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        }else if(dpcid.getText().toString().trim().equals("无:无")) {
            Toast.makeText(getApplicationContext(), "请选择部门和职务", Toast.LENGTH_SHORT).show();
        } else {
            employeeInfo.setName(ename.getText().toString().trim());
            employeeInfo.setId(tid.getText().toString().trim());
            employeeInfo.setSex(sex.getText().toString().trim());
            employeeInfo.setTel(etel.getText().toString().trim());
            employeeInfo.setBirth(birth.getText().toString().trim());
            employeeInfo.setEmail(eemail.getText().toString().trim());
            employeeInfo.setAddress(eaddress.getText().toString().trim());
            if ( TextUtils.isEmpty(employeeInfo.getPcid()) || TextUtils.isEmpty(employeeInfo.getDcid())) {
                Toast.makeText(getApplicationContext(), "部门和职务信息有误，请再次选择！", Toast.LENGTH_SHORT).show();
            } else if (!isMobile(employeeInfo.getTel())) {
                Toast.makeText(getApplicationContext(), "联系方式格式输入错误！请以手机格式重新输入", Toast.LENGTH_SHORT).show();
            } else if (!isEmail(employeeInfo.getEmail())) {
                Toast.makeText(getApplicationContext(), "邮箱格式输入错误！请重新输入", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //读取SharePreferences的cookies
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");
                        //编辑员工的id不能重复，故设为空，默认员工id不可修改
                        employeeInfo.setId("");
                        Log.e(TAG,"employeeInfo=="+employeeInfo.toString());
                        EmployeeInfoService employeeInfoService = new EmployeeInfoService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = employeeInfoService.updateemployeeinfo(s, employeeInfo);
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "保存成功！点击返回键返回员工信息", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        }
        }

           //年月选择器
            public void onYearMonthPicker (View view){
                DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
                //picker.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                picker.setTitleTextSize(16);
                picker.setTitleTextColor(Color.BLACK);
                picker.setTitleText("年月选择");
                picker.setWidth((int) (picker.getScreenWidthPixels() * 1));
                picker.setRangeStart(1900, 01, 01);
                picker.setRangeEnd(2020, 11, 11);
                picker.setSelectedItem(1996, 12);
                picker.setTopLineColor(Color.WHITE);
                picker.setSubmitTextSize(16);
                picker.setCancelTextSize(16);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {
                        birth.setText(year + "-" + month);
                        //showToast(year + "-" + month);
                    }
                });
                picker.show();
            }

            //年月日选择器
            public void onYearMonthDayPicker (View view){
                final DatePicker picker = new DatePicker(this);
                picker.setCanceledOnTouchOutside(true);
                picker.setUseWeight(true);
                picker.setTopPadding(ConvertUtils.toPx(this, 15));
                picker.setRangeEnd(2111, 1, 1);
                picker.setRangeStart(1900, 1, 1);
                picker.setSelectedItem(1996, 07, 03);
                picker.setResetWhileWheel(false);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        birth.setText(year + "-" + month + "-" + day);
                        // showToast(year + "-" + month + "-" + day);
                    }
                });
                picker.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
                    }
                });
                picker.show();
            }

            //年月日时分选择器
            public void onYearMonthDayTimePicker (View view){
                DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
                picker.setDateRangeStart(1900, 1, 1);
                picker.setDateRangeEnd(2025, 11, 11);
                picker.setTimeRangeStart(9, 0);
                picker.setTimeRangeEnd(20, 30);
                // picker.setTopLineColor(0x99FF0000);
                picker.setLabelTextColor(0xFFFF0000);
                picker.setDividerColor(0xFFFF0000);
                picker.setTopLineColor(Color.WHITE);
                picker.setSubmitTextSize(16);
                picker.setCancelTextSize(16);
                picker.setTitleTextColor(Color.BLACK);
                picker.setTitleText("年月日选择");
                picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        //birth.setText(year+"-"+month+"-"+day + " " + hour + ":" + minute);
                        //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                    }
                });
                picker.show();
            }

            //单项选择器
            public void onOptionPicker (View view){
                OptionPicker picker = new OptionPicker(this, new String[]{
                        "男", "女"
                });
                picker.setTitleTextColor(Color.BLACK);
                picker.setTitleText("性别选择");
                picker.setCanceledOnTouchOutside(false);
                picker.setDividerRatio(WheelView.DividerConfig.FILL);
                picker.setShadowColor(Color.WHITE, 40);
                picker.setSelectedIndex(0);
                picker.setCycleDisable(true);
                picker.setTextSize(18);
                picker.setTitleTextSize(16);
                picker.setTopLineColor(Color.WHITE);
                picker.setSubmitTextSize(16);
                picker.setCancelTextSize(16);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        sex.setText(item);
                        //showToast("index=" + index + ", item=" + item);
                    }
                });
                picker.show();
            }

            //重写showToast
            private void showToast (String s){
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            }

            //部门及职务选择
            public void onDpDtPicker(View view){

                /**
                 *   存储表中除了部门职务一项的所有数据
                 */
                EmployeeInfoDao employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();

                EmployeeInfo ep = new EmployeeInfo();
                ep.setAddress(eaddress.getText().toString().trim());
                ep.setBirth(birth.getText().toString().trim());
                ep.setName(ename.getText().toString().trim());
                ep.setId(tid.getText().toString().trim());
                ep.setEmail(eemail.getText().toString().trim());
                ep.setSex(sex.getText().toString().trim());
                ep.setTel(etel.getText().toString().trim());
                ep.setEid(employeeInfo.getEid());
                ep.setDcid(employeeInfo.getDcid());
                ep.setPcid(employeeInfo.getPcid());


                employeeInfoDao.deleteAll();
                employeeInfoDao.insert(ep);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                        String s = pref.getString("sessionid","");
                        DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                        DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);
                        if (departmentInfoJsonBean.getStatus() == 0) {
                            ArrayList<DepartmentInfo> departmentInfos= departmentInfoJsonBean.getData();
                            Intent intent = new Intent(EditEmployeeinfoActivity.this, DepartmentSelectActivity.class);
                            intent.putExtra("index", 2);
                            intent.putParcelableArrayListExtra("alldp", departmentInfos);
                            startActivity(intent);
                            finish();
                        } else {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(),departmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                }).start();
            }




    @Override
            public boolean onKeyDown ( int keyCode, KeyEvent event){

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

            private void exitBy2Click () {
                Timer tExit;
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
