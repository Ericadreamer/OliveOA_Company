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
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.controller.EmployeeInfoService;
import com.oliveoa.controller.GoodInfoService;
import com.oliveoa.greendao.CompanyInfoDao;
import com.oliveoa.greendao.DaoManager;
import com.oliveoa.greendao.DaoSession;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.EmployeeInfoDao;
import com.oliveoa.greendao.PropertiesInfoDao;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.EmployeeInfoJsonBean;
import com.oliveoa.jsonbean.GoodInfoJsonBean;
import com.oliveoa.pojo.CompanyInfo;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.pojo.PropertiesInfo;
import com.oliveoa.util.DataCleanManager;
import com.oliveoa.util.EntityManager;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LoadingDataActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
   /* private ArrayList<EmployeeInfo> employeeInfos;
    private ArrayList<DepartmentInfo> departmentInfos;
    private ArrayList<DutyInfo> dutyInfos;
    private ArrayList<PropertiesInfo> propertiesInfo;*/
    private CompanyInfo companyInfo;

    private CompanyInfoDao companyInfoDao;
    private DepartmentInfoDao departmentInfoDao;
    private EmployeeInfoDao employeeInfoDao;
    private DutyInfoDao dutyInfoDao;
    private PropertiesInfoDao propertiesInfoDao;*

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

                //获取部门数据
              /*  DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);
                if (departmentInfoJsonBean.getStatus()==0) {
                    departmentInfos = departmentInfoJsonBean.getData();

                    Log.d("departmentInfo", departmentInfos.toString());

                    for (int i = 0; i < departmentInfos.size(); i++) {
                        departmentInfoDao.insert(departmentInfos.get(i));

                    }
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取部门信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }


                //获取职务数据
                DutyInfoService dutyInfoService = new DutyInfoService();
                //获取员工数据
                EmployeeInfoService employeeInfoService = new EmployeeInfoService();


                //通过部门寻找对应的员工和职务
                dutyInfoDao.deleteAll();
                for (int i=0;i<departmentInfos.size();i++){
                    DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s,departmentInfos.get(i).getDcid());
                    if (dutyInfoJsonBean.getStatus() == 0) {
                        dutyInfos = dutyInfoJsonBean.getData();

                        Log.d("dutyInfo", dutyInfos.toString());
                        for (int j = 0; j < dutyInfos.size(); j++) {
                            //Log.d("dutyInfo["+j+"]", dutyInfos.get(j).toString());
                            dutyInfoDao.insert(dutyInfos.get(j));
                        }
                    }else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "网络错误，获取职务信息失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                    EmployeeInfoJsonBean employeeInfoJsonBean = employeeInfoService.employeeinfo(s, departmentInfos.get(i).getDcid());
                    if (employeeInfoJsonBean.getStatus() == 0) {
                        employeeInfos = employeeInfoJsonBean.getData();
                        Log.d("employeeInfo", employeeInfos.toString());
                        for (int j = 0; j < employeeInfos.size(); j++) {
                            //Log.d("employeeInfo["+j+"]", employeeInfos.get(j).toString());
                            employeeInfoDao.insert(employeeInfos.get(j));
                        }
                    }
                    else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "网络错误，获取员工信息失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                }

                //获取资产信息
                GoodInfoService goodInfoService = new GoodInfoService();
                GoodInfoJsonBean goodInfoJsonBean =  goodInfoService.properties(s);
                if (goodInfoJsonBean.getStatus() == 0) {
                    propertiesInfo = goodInfoJsonBean.getData();
                    Log.d("propertiesInfo", propertiesInfo.toString());
                    for (int j = 0; j < propertiesInfo.size(); j++) {
                        //Log.d("propertiesInfo["+j+"]", propertiesInfo.get(j).toString());
                        propertiesInfoDao.insert(propertiesInfo.get(j));
                    }
                }
                else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，获取资产信息失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }*/

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
