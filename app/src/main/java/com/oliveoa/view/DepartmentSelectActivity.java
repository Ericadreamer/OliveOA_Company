package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
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
import com.oliveoa.greendao.EmployeeInfoDao;
import com.oliveoa.greendao.PropertiesInfoDao;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.pojo.PropertiesInfo;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  ClassName DepartmentSelectActivity
 *  Description 部门选择Activity
 *  @Author  Erica
 */
public class DepartmentSelectActivity extends AppCompatActivity {

    private List<DepartmentInfo> departmentInfos;
    private ImageView back;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDPlistView;
    private int index;
    private TextView tvname;
    private DepartmentInfoDao departmentInfoDao;
    private DepartmentInfo temp;
    private EmployeeInfoDao employeeInfoDao;
    private EmployeeInfo ep;
    private PropertiesInfoDao propertiesInfoDao;
    private PropertiesInfo pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_select);

        departmentInfos= getIntent().getParcelableArrayListExtra("alldp");
        index = getIntent().getIntExtra("index",index);//编辑部门0，添加部门1，编辑员工2，添加员工3，编辑资产4，添加资产5

        initData();
    }

    public void initData(){
        if(index==0||index==1) {
            departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
            temp = departmentInfoDao.queryBuilder().unique();
        }
        if(index==2||index==3) {
            employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();
            ep = employeeInfoDao.queryBuilder().unique();
        }
        if(index==4||index==5) {
            propertiesInfoDao = EntityManager.getInstance().getPropertiesDao();
            pp = propertiesInfoDao.queryBuilder().unique();
        }
        initview();
    }

    public void initview(){
        back =(ImageView)findViewById(R.id.info_back);
        addDPlistView = (LinearLayout)findViewById(R.id.depart_list);

       // 默认添加一个Item
        addViewItem(null);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DepartmentSelectActivity.this);
                dialog.setTitle("提示");
                if(index==0) {
                    dialog.setMessage("是否确定退出选择,直接返回部门编辑页面？");
                }
                if(index==1){
                    dialog.setMessage("是否确定退出选择,直接返回部门创建页面？");
                }
                if(index==2){
                    dialog.setMessage("是否确定退出选择,直接返回员工编辑页面？");
                }
                if(index==3){
                    dialog.setMessage("是否确定退出选择,直接返回员工创建页面？");
                }
                if(index==4){
                    dialog.setMessage("是否确定退出选择,直接返回物品编辑页面？");
                }
                if(index==5){
                    dialog.setMessage("是否确定退出选择,直接返回物品创建页面？");
                }
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {back();
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

    }

    /**
     *   MethodName  back()
     *   Description 返回相应页面
     *   @Author Erica
     */
    private void back() {
        if(index==0) {
            if(temp!=null) {
                Log.e(TAG,temp.toString());
                temp.setDpid("");
                departmentInfoDao.deleteAll();
                departmentInfoDao.insert(temp);
                Log.e(TAG,departmentInfoDao.queryBuilder().unique().toString());
            }
            Intent intent = new Intent(DepartmentSelectActivity.this, RedactDepartmentActivity.class);
            intent.putExtra("dpname", "无");
            intent.putExtra("index", 1);
            intent.putExtra("dp",temp);
            startActivity(intent);
            finish();
        }
        if(index==1){
            if(temp!=null) {
                Log.e(TAG,temp.toString());
                temp.setDpid("");
                departmentInfoDao.deleteAll();
                departmentInfoDao.insert(temp);
                Log.e(TAG,departmentInfoDao.queryBuilder().unique().toString());
            }
            Intent intent = new Intent(DepartmentSelectActivity.this, CreateDepartmentActivity.class);
            intent.putExtra("dpname", "无");
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==2){
            Intent intent = new Intent(DepartmentSelectActivity.this,EditEmployeeinfoActivity.class);
            intent.putExtra("dname", "无");
            intent.putExtra("pname", "无");
            intent.putExtra("index", 1);
            intent.putExtra("ep",ep);
            startActivity(intent);
            finish();
        }
        if(index==3){
            Intent intent = new Intent(DepartmentSelectActivity.this,AddEmployeeinfoActivity.class);
            intent.putExtra("dname", "无");
            intent.putExtra("pname", "无");
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
        if(index==4){
            Intent intent = new Intent(DepartmentSelectActivity.this,EditGoodsActivity.class);
            intent.putExtra("dname", "无");
            intent.putExtra("pname", "无");
            intent.putExtra("index", 1);
            intent.putExtra("pp",pp);
            startActivity(intent);
            finish();
        }
        if(index==5){
            Intent intent = new Intent(DepartmentSelectActivity.this,AddGoodsActivity.class);
            intent.putExtra("dname", "无");
            intent.putExtra("pname", "无");
            intent.putExtra("index", 1);
            startActivity(intent);
            finish();
        }
    }

   /**
    *   MethodName  sortHotelViewItem()
    *   Description Item排序
    *   @Author Erica
    */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addDPlistView.getChildCount(); i++) {
            final View childAt = addDPlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout)childAt.findViewById(R.id.depart_item);

            final TextView tname = (TextView)childAt.findViewById(R.id.dname);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        // Log.e(TAG,tname.getText().toString());
                        if(index==0)  {  //编辑部门选择
                            EditDpSelect(tname);
                        }
                        if(index==1){ //创建部门选择
                            AddDpSelect(tname);
                        }
                        if(index==2){ //编辑员工选择
                            EditEpSelect(tname);
                        }
                        if(index==3){//创建员工选择
                            AddEpSelect(tname);
                        }
                        if(index==4){ //编辑物品选择
                            EditPpSelect(tname);
                        }
                        if(index==5){//创建物品选择
                            AddPpSelect(tname);

                        }
                    }
            });
        }
    }


    /**
     *   MethodName  EditPpSelect(TextView tname)
     *   Description 编辑物品选择
     *   @Author Erica
     */
    private void EditPpSelect(TextView tname) {
        if(pp!=null) {
            Log.e(TAG,pp.toString());
            String dcid =null;
            for(int i=0;i<departmentInfos.size();i++){
                Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
                if(tname.getText().toString().equals(departmentInfos.get(i).getName())){
                    dcid = departmentInfos.get(i).getDcid();
                    Log.e(TAG,departmentInfos.get(i).getDcid());
                    break;
                }
            }
            final String finalDcid = dcid;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                    String s = pref.getString("sessionid","");

                    DutyInfoService dutyInfoService = new DutyInfoService();
                    DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s, finalDcid);
                    if(dutyInfoJsonBean.getStatus()==0){
                        ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                        if(dutyInfos.size()==0){
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该部门下无职位，请选择其他部门", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }else {
                            Intent intent = new Intent(DepartmentSelectActivity.this, DutySelectActivity.class);
                            intent.putExtra("index", 4);
                            intent.putParcelableArrayListExtra("alldt", dutyInfos);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                }
            }).start();
        }
    }

    /**
     *   MethodName  AddPpSelect(TextView tname)
     *   Description 创建物品选择
     *   @Author Erica
     */
    private void AddPpSelect(TextView tname) {
        if(pp!=null) {
            Log.e(TAG,pp.toString());
            String dcid =null;
            for(int i=0;i<departmentInfos.size();i++){
                Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
                if(tname.getText().toString().equals(departmentInfos.get(i).getName())){
                    dcid = departmentInfos.get(i).getDcid();
                    Log.e(TAG,departmentInfos.get(i).getDcid());
                    break;
                }
            }

            final String finalDcid = dcid;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                    String s = pref.getString("sessionid","");

                    DutyInfoService dutyInfoService = new DutyInfoService();
                    DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s, finalDcid);
                    if(dutyInfoJsonBean.getStatus()==0){
                        ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                        if(dutyInfos.size()==0){
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该部门下无职位，请选择其他部门", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }else {
                            Intent intent = new Intent(DepartmentSelectActivity.this, DutySelectActivity.class);
                            intent.putExtra("index", 5);
                            intent.putParcelableArrayListExtra("alldt", dutyInfos);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                }
            }).start();
        }
    }



    /**
     *   MethodName  EditEpSelect(TextView tname)
     *   Description 编辑员工选择
     *   @Author Erica
     */
    private void EditEpSelect(TextView tname) {
        if(ep!=null) {
            Log.e(TAG,ep.toString());

            for(int i=0;i<departmentInfos.size();i++){
                Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
                if(tname.getText().toString().equals(departmentInfos.get(i).getName())){
                    ep.setDcid(departmentInfos.get(i).getDcid());
                    Log.e(TAG,departmentInfos.get(i).getDcid());
                    break;
                }
            }
            employeeInfoDao.deleteAll();
            employeeInfoDao.insert(ep);
            Log.e(TAG,"employeeInfoDao.queryBuilder().unique().toString()======"+employeeInfoDao.queryBuilder().unique().toString());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                    String s = pref.getString("sessionid","");

                    DutyInfoService dutyInfoService = new DutyInfoService();
                    DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s,ep.getDcid());
                    if(dutyInfoJsonBean.getStatus()==0){
                        ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                        if(dutyInfos.size()==0){
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该部门下无职位，请选择其他部门", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }else {
                            Intent intent = new Intent(DepartmentSelectActivity.this, DutySelectActivity.class);
                            intent.putExtra("index", 2);
                            intent.putParcelableArrayListExtra("alldt", dutyInfos);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                }
            }).start();
        }
    }

    /**
     *   MethodName  AddEpSelect(TextView tname)
     *   Description 创建员工选择
     *   @Author Erica
     */
    private void AddEpSelect(TextView tname) {
        if(ep!=null) {
            Log.e(TAG,ep.toString());

            for(int i=0;i<departmentInfos.size();i++){
                Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
                if(tname.getText().toString().equals(departmentInfos.get(i).getName())){
                    ep.setDcid(departmentInfos.get(i).getDcid());
                    Log.e(TAG,departmentInfos.get(i).getDcid());
                    break;
                }
            }
            employeeInfoDao.deleteAll();
            employeeInfoDao.insert(ep);
            Log.e(TAG,"employeeInfoDao.queryBuilder().unique().toString()======"+employeeInfoDao.queryBuilder().unique().toString());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                    String s = pref.getString("sessionid","");

                    DutyInfoService dutyInfoService = new DutyInfoService();
                    DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s,ep.getDcid());
                    if(dutyInfoJsonBean.getStatus()==0){
                        ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                        if(dutyInfos.size()==0){
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "该部门下无职位，请选择其他部门", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }else {
                            Intent intent = new Intent(DepartmentSelectActivity.this, DutySelectActivity.class);
                            intent.putExtra("index", 3);
                            intent.putParcelableArrayListExtra("alldt", dutyInfos);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }

                }
            }).start();
        }
    }

    /**
     *   MethodName  AddDpSelect(TextView tname)
     *   Description 创建部门选择
     *   @Author Erica
     */
    private void AddDpSelect(TextView tname) {
        if(temp!=null) {
            Log.e(TAG,temp.toString());

            for(int i=0;i<departmentInfos.size();i++){
                Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
                if(tname.getText().toString().equals(departmentInfos.get(i).getName())){
                    temp.setDpid(departmentInfos.get(i).getDcid());
                    Log.e(TAG,departmentInfos.get(i).getDcid());
                    break;
                }
            }

           departmentInfoDao.deleteAll();
            departmentInfoDao.insert(temp);
            Log.e(TAG,departmentInfoDao.queryBuilder().unique().toString());
        }
        Intent intent = new Intent(DepartmentSelectActivity.this, CreateDepartmentActivity.class);
        intent.putExtra("index", 1);
        intent.putExtra("dpname", tname.getText().toString());
        startActivity(intent);
        finish();
    }

    /**
    *   MethodName  EditDpSelect(TextView tname)
    *   Description 编辑部门选择
    *   @Author Erica
    */
    private void EditDpSelect(TextView tname) {
        if(temp!=null) {
            Log.e(TAG,temp.toString());
            if(tname.getText().toString().equals(temp.getName())) {
                Toast.makeText(getApplicationContext(), "请选择除"+tname.getText().toString()+"以外的其他部门", Toast.LENGTH_SHORT).show();
                return ;
            }
            for(int i=0;i<departmentInfos.size();i++){
                Log.e(TAG,departmentInfos.get(i).getName()+tname.getText().toString());
                if(tname.getText().toString().equals(departmentInfos.get(i).getName())){
                    temp.setDpid(departmentInfos.get(i).getDcid());
                    Log.e(TAG,departmentInfos.get(i).getDcid());
                    break;
                }
            }

            departmentInfoDao.deleteAll();
            departmentInfoDao.insert(temp);
            Log.e(TAG,departmentInfoDao.queryBuilder().unique().toString());
        }
        Intent intent = new Intent(DepartmentSelectActivity.this,RedactDepartmentActivity.class);
        intent.putExtra("index", 1);
        intent.putExtra("dp", temp);
        intent.putExtra("dpname", tname.getText().toString());
        startActivity(intent);
        finish();
    }


    //添加ViewItem
    private void addViewItem(View view) {
        if (departmentInfos == null) {//如果部门列表为0，加载空布局
            View hotelEvaluateView = View.inflate(this, R.layout.activity_null_department, null);
            addDPlistView.addView(hotelEvaluateView);
            //sortHotelViewItem();
        } else {//如果有部门则按数组大小加载布局
            for(int i = 0;i <departmentInfos.size(); i ++){
                View hotelEvaluateView = View.inflate(this, R.layout.activity_department_selectitem, null);
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

            tvname.setText(departmentInfos.get(i).getName());
        }
        Log.e(TAG, "部门名称：" + tvname.getText().toString());
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
