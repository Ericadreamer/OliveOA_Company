package com.oliveoa.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.Adapter.GridViewAdapter;
import com.oliveoa.controller.CompanyInfoService;
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.controller.EmployeeInfoService;
import com.oliveoa.controller.GoodInfoService;
import com.oliveoa.controller.LoginService;
import com.oliveoa.greendao.CompanyInfoDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.EmployeeInfoDao;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.EmployeeInfoJsonBean;
import com.oliveoa.jsonbean.GoodInfoJsonBean;
import com.oliveoa.jsonbean.LogoutJsonBean;
import com.oliveoa.pojo.CompanyInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.pojo.PropertiesInfo;
import com.oliveoa.util.DBOpreator;
import com.oliveoa.util.EntityManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener {

    GridViewAdapter adapter;
    GridView gridView;

    private DrawerLayout drawerLayout;
    private SystemBarTintManager tintManager;
    private NavigationView navigationView;
    private RadioButton departionbtn, staffbtn, propertybtn, documentbtn;
    ImageView menu;

    private ArrayList<EmployeeInfo> employeeInfos;
    private ArrayList<DepartmentInfo> departmentInfos;
    private ArrayList<PropertiesInfo> properties;
    private ArrayList<DutyInfo> dutyInfos;

    private String TAG = this.getClass().getSimpleName();
    private RelativeLayout pacmanIndicator_layout;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private Handler mHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        adapter = new GridViewAdapter(this);

        initview();

    }

    public void initview() {
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(adapter);

        initWindow();
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_na);
        navigationView = (NavigationView) findViewById(R.id.nav);
        menu = (ImageView) findViewById(R.id.main_menu);
        pacmanIndicator_layout = (RelativeLayout) findViewById(R.id.pacmanIndicator_layout);

        //newton_cradle_loading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        //newton_cradle_loading.setVisibility(View.GONE);

        View headerView = navigationView.getHeaderView(0);//获取头布局
        menu.setOnClickListener(this);

        //九宫格跳转
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0://点击图片加班跳转
                    {
                        departioninfo();
                    }
                    break;
                    case 1://点击图片请假跳转
                    {
                        employeeinfo();
                    }
                    break;
                    case 2://点击图片出差跳转
                    {
                        propertyinfo();
                    }
                    break;
                    case 3://点击图片会议跳转
                    {
                        documentinfo();
                    }
                    break;

                    default:
                        break;
                }
            }

        });


        //设置菜单图标为默认原图颜色
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                String string = null;
                switch (id) {
                    case R.id.nav_name:
                        companyinfo();
                        break;
                    case R.id.nav_password:
                        updatepassword();
                        break;
                    case R.id.nav_advise:
                        Intent intent = new Intent(MainActivity.this, AdviseActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_aboutus:
                        intent = new Intent(MainActivity.this, AboutusActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_update:
                        Toast.makeText(getApplicationContext(), "您的版本已是最新版本！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_exit:
                        logout();
                        break;


                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }

    /**
     * MethodName： companyinfo()
     * Description： 公司资料
     *
     * @Author： Erica
     */
    public void companyinfo() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                CompanyInfoService companyInfoService = new CompanyInfoService();
                CompanyLoginJsonBean companyloginJsonBean = companyInfoService.companyinfo(s);
                CompanyInfo companyInfo = companyloginJsonBean.getData();
                if (companyloginJsonBean.getStatus() == 0) {
                    CompanyInfoDao companyInfoDao = EntityManager.getInstance().getCompanyInfo();
                    companyInfoDao.deleteAll();
                    companyInfoDao.insertOrReplace(companyInfo);
                    Intent intent = new Intent(MainActivity.this, CompanyinfoActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "数据加载失败，请重试", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        }).start();
    }

    /**
     * MethodName： updatepassword()
     * Description： 修改密码
     *
     * @Author： Erica
     */
    public void updatepassword() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                CompanyInfoService companyInfoService = new CompanyInfoService();
                CompanyLoginJsonBean companyloginJsonBean = companyInfoService.companyinfo(s);
                CompanyInfo company = companyloginJsonBean.getData();

                if (companyloginJsonBean.getStatus() == 0) {
                    Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                    intent.putExtra("CompanyPassword", company.getPassword());
                    startActivity(intent);
                    finish();
                } else {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "数据加载失败，请重试", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        }).start();

    }

    /**
     * MethodName： logout()
     * Description： 登出系统
     *
     * @Author： Erica
     */
    private void logout() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                LoginService loginService = new LoginService();
                LogoutJsonBean logoutJsonBean = loginService.logout(s);
                //System.out.println("logoutisSuccess = "+ logoutJsonBean.getStatus());

                if (logoutJsonBean.getStatus() == 0) {
                    pref.edit().remove("sessionid").commit();//移除指定数值
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，请重试", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列

                }
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu://点击菜单，跳出侧滑菜单
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
                break;
        }
    }


    private void initWindow() {//初始化窗口属性，让状态栏和导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            int statusColor = Color.parseColor("#373B3E");
            tintManager.setStatusBarTintColor(statusColor);
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    //部门管理
    private void departioninfo() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Handler mHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");
                DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);
                if (departmentInfoJsonBean.getStatus() == 0) {
                    departmentInfos = departmentInfoJsonBean.getData();
                    Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
                    intent.putParcelableArrayListExtra("alldp", departmentInfos);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), departmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "uiThread2------" + Thread.currentThread());//子线程
            }
        };

        Log.d(TAG, "uiThread1------" + Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);
    }

    //员工管理
    private void employeeinfo() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        pacmanIndicator_layout.setVisibility(View.VISIBLE);

        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avLoadingIndicatorView.show();
        Handler mHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);

                EmployeeInfoService employeeInfoService = new EmployeeInfoService();
                EmployeeInfoDao employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();


                if (departmentInfoJsonBean.getStatus() == 0) {
                    departmentInfos = departmentInfoJsonBean.getData();
                    employeeInfoDao.deleteAll();
                    for (int i = 0; i < departmentInfos.size(); i++) {

                        EmployeeInfoJsonBean employeeInfoJsonBean = employeeInfoService.employeeinfo(s, departmentInfos.get(i).getDcid());
                        if (employeeInfoJsonBean.getStatus() == 0) {
                            employeeInfos = employeeInfoJsonBean.getData();
                            for (int j = 0; j < employeeInfos.size(); j++) {
                                employeeInfoDao.insert(employeeInfos.get(j));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), employeeInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    mHandler1.sendEmptyMessage(2);
                    Intent intent = new Intent(MainActivity.this, EmployeelistActivity.class);
                    intent.putParcelableArrayListExtra("alldp", departmentInfos);
                    //intent.putParcelableArrayListExtra("allep",employeeInfos);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), departmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "uiThread2------" + Thread.currentThread());//子线程
            }
        };

        Log.d(TAG, "uiThread1------" + Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);

    }

    //资产管理
    private void propertyinfo() {
        //Toast.makeText(getApplicationContext(), "资产管理", Toast.LENGTH_SHORT).show();

        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Handler mHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String s = pref.getString("sessionid", "");

                GoodInfoService goodInfoService = new GoodInfoService();
                GoodInfoJsonBean goodInfoJsonBean = goodInfoService.properties(s);
                if (goodInfoJsonBean.getStatus() == 0) {
                    ArrayList<PropertiesInfo> propertiesInfos = goodInfoJsonBean.getData();

                    Intent intent = new Intent(MainActivity.this, GoodsActivity.class);
                    intent.putExtra("allpp", propertiesInfos);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), goodInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                }


                Log.d(TAG, "uiThread2------" + Thread.currentThread());//子线程
            }
        };

        Log.d(TAG, "uiThread1------" + Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);
    }

    //公文管理
    private void documentinfo() {
        Intent intent = new Intent(MainActivity.this, DocumentManagementActivity.class);
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
            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
            pref.edit().remove("sessionid").commit();//移除指定数值
            System.exit(0);

        }
    }


}
