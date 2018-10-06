package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.OneDepartmentInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.oliveoa.util.Validator.isNumstr;

public class AddDutyActivity extends AppCompatActivity {

    private List<DutyInfo> dutyInfo;

    private ImageView back, save, dtselect;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private EditText etname, etnum;
    private TextView tppid;

    private String dtname;//上级职务
    private String dpname;//上级部门
    private int index;
    private DutyInfo dt;
    private DutyInfoDao dutyInfoDao;
    private DepartmentInfoDao departmentInfoDao;
    private DepartmentInfo departmentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_duty);

        index = getIntent().getIntExtra("index", index);//详情0，职务选择返回1
        dtname = getIntent().getStringExtra("dtname");

        Log.e(TAG, String.valueOf(index) + ":::" + dtname);
        initData();
    }

    private void initData() {

        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        departmentInfo = departmentInfoDao.queryBuilder().unique();//该职务的所属部门信息

        dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();



            dt = dutyInfoDao.queryBuilder().unique();
            Log.e(TAG, dt.toString());


        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.null_back);
        save = (ImageView) findViewById(R.id.info_save);
        dtselect = (ImageView) findViewById(R.id.duty_select);

        etname = (EditText) findViewById(R.id.edit_duty_name);
        etnum = (EditText) findViewById(R.id.edit_limit);
        tppid = (TextView) findViewById(R.id.edit_superior);

        if (index == 1) {
            etname.setText(dt.getName());
            etnum.setText(String.valueOf(dt.getLimit()));
            tppid.setText(dtname);
        }


        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddDutyActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出创建,直接返回职务列表页面？");
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
            }
        });
        save.setOnClickListener(new View.OnClickListener() {   //点击保存键，提示保存是否成功
            @Override
            public void onClick(View view) {
                save();
            }
        });

        tppid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dutySelect();
            }
        });
        dtselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dutySelect();
            }
        });

    }

    //返回部门信息
    public void back() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Handler mHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                //获取被点击一项的职务信息
                DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                DutyInfoService dutyInfoService = new DutyInfoService();
                OneDepartmentInfoJsonBean oneDepartmentInfoJsonBean = departmentInfoService.getdepartmentinfo(s,departmentInfo.getDpid());
                if(oneDepartmentInfoJsonBean.getStatus()==0) {
                    DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s, departmentInfo.getDcid());
                    if (dutyInfoJsonBean.getStatus() == 0) {
                        ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                        Intent intent = new Intent(AddDutyActivity.this, DepartmentInfoActivity.class);
                        intent.putExtra("dp", departmentInfo);
                        intent.putExtra("dpname",oneDepartmentInfoJsonBean.getData().getName() );
                        intent.putParcelableArrayListExtra("alldt", dutyInfos);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), oneDepartmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "uiThread2------" + Thread.currentThread());//子线程
            }
        };
        Log.d(TAG, "uiThread1------" + Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);
    }

    public void dutySelect() {
        DutyInfo t = new DutyInfo();
        t.setDcid(departmentInfo.getDcid());
        t.setName(etname.getText().toString());
        t.setPpid(dt.getPpid());
        if (!etnum.getText().toString().trim().equals("")) {
            t.setLimit(Integer.parseInt(etnum.getText().toString().trim()));
        }

        dutyInfoDao.deleteAll();
        dutyInfoDao.insert(t);

        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Handler mHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                DutyInfoService dutyInfoService = new DutyInfoService();
                DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s, departmentInfo.getDcid());
                if (dutyInfoJsonBean.getStatus() == 0) {
                    ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                    if (dutyInfos.size() == 0) {
                        Toast.makeText(getApplicationContext(), "当前无更多部门，无法选择，请创建新部门！", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(AddDutyActivity.this, DutySelectActivity.class);
                        intent.putParcelableArrayListExtra("alldt", dutyInfos);
                        intent.putExtra("index", 1);//判断是编辑页面0还是添加页面1
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                Log.d(TAG, "uiThread2------" + Thread.currentThread());//子线程
            }
        };

        Log.d(TAG, "uiThread1------" + Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);

    }

    //保存编辑
    public void save() {

        if (isNumstr(etnum.getText().toString().trim())) {
            dt.setName(etname.getText().toString().trim());
            dt.setLimit(Integer.parseInt(etnum.getText().toString().trim()));

            if (TextUtils.isEmpty(dt.getName())) {
                Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
            } else if (dt.getLimit() <= 0) {
                Toast.makeText(getApplicationContext(), "职务限制人数不得小于1人，请输入人数", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        Boolean flag =false;
                        if(tppid.getText().toString().trim().equals("无")||tppid.getText().toString().trim().equals("")){
                            dt.setPpid("");
                        }
                        DutyInfoService dutyInfoService = new DutyInfoService();
                        DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s, departmentInfo.getDcid());
                        if (dutyInfoJsonBean.getStatus() == 0) {
                            ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                            if (dutyInfos.size() == 0) {
                               // Toast.makeText(getApplicationContext(), "当前无更多部门，无法选择，请创建新部门！", Toast.LENGTH_SHORT).show();
                            } else {
                                 for (int i=0;i<dutyInfos.size();i++){
                                     if(dutyInfos.get(i).getName().equals(dt.getName())){
                                         flag = true;
                                         Looper.prepare();
                                         Toast.makeText(getApplicationContext(), "该职务已存在，请创建新职务！", Toast.LENGTH_SHORT).show();
                                         Looper.loop();
                                     }
                                 }
                            }
                            if(flag==false) {
                                StatusAndMsgJsonBean statusAndMsgJsonBean = dutyInfoService.addduty(s, dt);
                                // Log.d("add", statusAndMsgJsonBean.getMsg() + "");
                                if (statusAndMsgJsonBean.getStatus() == 0) {
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), "创建成功！点击返回键返回部门列表", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        } else {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        } else {
            Toast.makeText(getApplicationContext(), "请输入阿拉伯数字形式的限制人数", Toast.LENGTH_SHORT).show();
        }


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
