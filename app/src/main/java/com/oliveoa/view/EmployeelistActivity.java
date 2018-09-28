package com.oliveoa.view;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.controller.EmployeeInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.EmployeeInfoDao;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.EmployeeInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.pojo.Group;
import com.oliveoa.pojo.Item;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.MyBaseExpandableListAdapter;


public class EmployeelistActivity extends AppCompatActivity {

    private transient ArrayList<Group> gData = null;
    private transient ArrayList<ArrayList<Item>> iData = null;
    private transient ArrayList<Item> lData = null;

    private Context mContext;
    private ExpandableListView exlist_staff;
    private MyBaseExpandableListAdapter myAdapter = null;
    private ImageView back,add;
    private String TAG = this.getClass().getSimpleName();
    private List<EmployeeInfo> employeeInfos;
    private ArrayList<DepartmentInfo> departmentInfos;

    private EmployeeInfoDao employeeInfoDao;
    private DepartmentInfoDao departmentInfoDao;

    private EmployeeInfo employeeInfo;
    private DepartmentInfo departmentInfo;

    private String dname,pname;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeelist);
        departmentInfos= getIntent().getParcelableArrayListExtra("alldp");

        Log.e(TAG,"departmentInfos:"+departmentInfos.toString());
        initData();
    }

    public void initData(){
        employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();
        departmentInfoDao =EntityManager.getInstance().getDepartmentInfo();
        Log.e(TAG,""+employeeInfoDao.queryBuilder().count());

        initView();
    }


    public void initView() {
        mContext = EmployeelistActivity.this;
        exlist_staff = (ExpandableListView) findViewById(R.id.exlist_staff);
        back = (ImageView) findViewById(R.id.info_back);
        add = (ImageView) findViewById(R.id.add);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeelistActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        if(employeeInfoDao.queryBuilder().count()>0){
        //数据准备
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();
        for (int i = 0; i < departmentInfos.size(); i++) {
            gData.add(new Group(departmentInfos.get(i).getName()));

            lData = new ArrayList<Item>();

            employeeInfos = employeeInfoDao.queryBuilder().where(EmployeeInfoDao.Properties.Dcid.eq(departmentInfos.get(i).getDcid())).list();
            if (employeeInfos != null) {
                for (int j = 0; j < employeeInfos.size(); j++)
                    lData.add(new Item(R.drawable.yonghu, employeeInfos.get(j).getName() + "", employeeInfos.get(j).getEid()));
            }

            iData.add(lData);
        }

        myAdapter = new MyBaseExpandableListAdapter(gData, iData, mContext);
        exlist_staff.setAdapter(myAdapter);
        exlist_staff.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Log.i("" + EmployeelistActivity.this, "group " + groupPosition + " child " + childPosition);
                //employeeInfo = employeeDAO.getEmployees(departmentInfo.get(groupPosition).getDcid());
                Log.i(TAG, "被点击的员工Eid：" + iData.get(groupPosition).get(childPosition).getiEid());
                info(iData.get(groupPosition).get(childPosition).getiEid());
                return false;
            }
        });
        exlist_staff.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                final TextView name = (TextView) arg1.findViewById(R.id.tv_name);
                final String name_str = name.getText().toString().trim();
                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeelistActivity.this);
                builder.setTitle("警告");
                builder.setMessage("您正在试图删除" + name_str + "这名职员，确定删除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*deleteEmployee(name_str);*/
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                return true;
            }
        });

        exlist_staff.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //Log.i("" + EmployeelistActivity.this, "group " + groupPosition);
                Log.i(TAG,"GroupSize="+gData.get(groupPosition).getgName()+"_________ChildSize="+iData.get(groupPosition).size());
                if(iData.get(groupPosition).size()==0)
                   Toast.makeText(getApplicationContext(), "提示：没有员工在"+gData.get(groupPosition).getgName()+"门哦！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    }

    /**
     *  添加员工页面
     */
    public void add(){
        EmployeeInfoDao employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();
        EmployeeInfo ep = new EmployeeInfo();
        employeeInfoDao.deleteAll();
        employeeInfoDao.insert(ep);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(EmployeelistActivity.this, AddEmployeeinfoActivity.class);
                intent.putExtra("index",0);
                //intent.putParcelableArrayListExtra("alldp", departmentInfos);
                intent.putExtra("dname", "无");
                intent.putExtra("pname", "无");
                startActivity(intent);
                finish();
            }
        }).start();
    }

     /**
      *  员工信息页面
     */
    public void info(String Eid){
        employeeInfo = employeeInfoDao.queryBuilder().where(EmployeeInfoDao.Properties.Eid.eq(Eid)).unique();
        if(employeeInfo!=null){
            for(int i =0;i<departmentInfos.size();i++){
                 if(departmentInfos.get(i).getDcid().equals(employeeInfo.getDcid())){
                     dname = departmentInfos.get(i).getName();
                 }
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                    String s = pref.getString("sessionid","");

                    DutyInfoService dutyInfoService = new DutyInfoService();
                    DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s,employeeInfo.getDcid());
                    if(dutyInfoJsonBean.getStatus()==0){
                        ArrayList<DutyInfo> dutyInfos = dutyInfoJsonBean.getData();
                        for(int j = 0 ;j<dutyInfos.size();j++){
                            if(dutyInfos.get(j).getPcid().equals(employeeInfo.getPcid())){
                                Intent intent = new Intent(EmployeelistActivity.this, EmployeeinfoActivity.class);
                                intent.putExtra("ep",employeeInfo);
                                intent.putExtra("dname",dname);
                                intent.putExtra("pname",dutyInfos.get(j).getName());
                                startActivity(intent);
                                finish();
                                break;
                            }
                        }

                    }else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), dutyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列
                    }
                }
            }).start();
        }else{
            Toast.makeText(mContext, "哎呀，出错了，没有该部门的信息！", Toast.LENGTH_SHORT).show();
        }


    }

    /*public void  deleteEmployee(String name_str){
        final String name = name_str;
      *//*  final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl(EmployeelistActivity.this);*//*

       // Log.e("departmentSize", String.valueOf(departmentInfo.size()));
        Log.e("employeeSize", String.valueOf(employeeInfo.size()));
        Log.e("employeeInfo", employeeInfo.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
            for(int i=0;i<departmentInfo.size();i++) {
                employeeInfo = employeeDAO.getEmployees(departmentInfo.get(i).getDcid());
                for (int j = 0; j < employeeInfo.size(); j++) {
                    Log.e("employeeName", employeeInfo.get(j).getName());
                    if (employeeInfo.get(j).getName().equals(name)) {
                        //读取SharePreferences的cookies
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        EmployeeInfoService employeeInfoService = new EmployeeInfoService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = employeeInfoService.deleteemployee(s, employeeInfo.get(i).getEid());


                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            employeeDAO.deleteEmployee(employeeInfo.get(j).getEid());
                            employeeInfo.remove(employeeInfo.get(j));

                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列

                        } else {
                            Looper.prepare();//解决子线程弹toast问题
                            Toast.makeText(getApplicationContext(), "网络错误，获取部门信息失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }
                    }
                }
            }
            }
        }).start();
    }

*/

    /**
     *  对于这种include下还包含着expendlistview，expendlistview下又有两个xml文件，尝试直接在本Activity用findviewbyid获取item_exlist_iten.xml里的button
     *  ，然而显示空指针，使用inflater虽然能获取到button，但是捆绑监听事件。最后解决方案很简单，直接在item_exlist_iten.xml里的button添加onclick事件就好了……
     *  然后在本Activity里写好相应的onclick方法
     *
     */
    //详情单击触发函数
//    public void StaffEdit(View view){
//        //Toast.makeText(mContext, "你点击了详情", Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(EmployeelistActivity.this, EmployeeinfoActivity.class);
//        startActivity(intent);
//        finish();
//    }
    //删除单机触发函数
//    public void StaffDelete(View view){
//        Toast.makeText(mContext, "你点击了删除", Toast.LENGTH_SHORT).show();
//    }

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
