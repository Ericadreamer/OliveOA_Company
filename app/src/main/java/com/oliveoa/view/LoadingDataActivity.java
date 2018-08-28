package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.CompanyInfoService;
import com.oliveoa.greendao.CompanyInfoDao;
import com.oliveoa.greendao.DaoManager;
import com.oliveoa.greendao.DaoSession;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.EmployeeInfoDao;
import com.oliveoa.greendao.PropertiesInfoDao;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.pojo.CompanyInfo;
import com.oliveoa.util.DataCleanManager;
import com.oliveoa.util.EntityManager;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingDataActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private CompanyInfo companyInfo;

    private CompanyInfoDao companyInfoDao;
    private DepartmentInfoDao departmentInfoDao;
    private EmployeeInfoDao employeeInfoDao;
    private DutyInfoDao dutyInfoDao;
    private PropertiesInfoDao propertiesInfoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingview);
        initdata();
    }
    public void initdata(){

        DataCleanManager dataCleanManager = new DataCleanManager();
        dataCleanManager.cleanDatabases();

        DaoManager.getInstance().getSession().clear();

        companyInfoDao = EntityManager.getInstance().getCompanyInfo();
        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        dutyInfoDao = EntityManager.getInstance().getDutyInfoInfo();
        employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();
        propertiesInfoDao = EntityManager.getInstance().getPropertiesDao();

        companyInfoDao.deleteAll();
        departmentInfoDao.deleteAll();
        dutyInfoDao.deleteAll();
        employeeInfoDao.deleteAll();
        propertiesInfoDao.deleteAll();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //读取SharePreferences的cookies
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");


                CompanyInfoService companyInfoService = new CompanyInfoService();
                CompanyLoginJsonBean companyloginJsonBean = companyInfoService.companyinfo(s);

                if (companyloginJsonBean.getStatus()==0){
                    companyInfo = companyloginJsonBean.getData();
                    companyInfoDao.insert(companyInfo);
                }else{
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "数据加载失败，请重试", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        }).start();

        final  Intent intent = new Intent(LoadingDataActivity.this,MainActivity.class);
        Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task,3000);//此处的Delay可以是3*1000，代表三秒


    }
}
