//package com.oliveoa.view;
//
//import android.content.Context;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Looper;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.KeyEvent;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ExpandableListView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//
//import com.example.erica.oliveoa_company.R;
//import com.oliveoa.controller.EmployeeInfoService;
//import com.oliveoa.dao.DepartmentDAO;
//import com.oliveoa.daoimpl.DepartmentDAOImpl;
//import com.oliveoa.daoimpl.EmployeeDAOImpl;
//import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
//import com.oliveoa.pojo.DepartmentInfo;
//import com.oliveoa.pojo.EmployeeInfo;
//import com.oliveoa.pojo.Group;
//import com.oliveoa.pojo.Item;
//import com.oliveoa.util.MyBaseExpandableListAdapter;
//
//
//public class EmployeelistActivity extends AppCompatActivity {
//
//    private transient ArrayList<Group> gData = null;
//    private transient ArrayList<ArrayList<Item>> iData = null;
//    private transient ArrayList<Item> lData = null;
//
//    private Context mContext;
//    private ExpandableListView exlist_staff;
//    private MyBaseExpandableListAdapter myAdapter = null;
//    private ImageView back,add;
//
//    private ArrayList<EmployeeInfo> employeeInfo;
//    private ArrayList<DepartmentInfo> departmentInfo;
//
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_employeelist);
//        initView();
//    }
//
//    public void initView(){
//        mContext = EmployeelistActivity.this;
//
//        exlist_staff = (ExpandableListView) findViewById(R.id.exlist_staff);
//        back =(ImageView)findViewById(R.id.info_back);
//        add =(ImageView)findViewById(R.id.add);
//
//        final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl(EmployeelistActivity.this);
//        final DepartmentDAOImpl departmentDAO = new DepartmentDAOImpl(EmployeelistActivity.this);
//        departmentInfo = departmentDAO.getDepartments();
//
//        //数据准备
//        gData = new ArrayList<Group>();
//        iData = new ArrayList<ArrayList<Item>>();
//        for (int i=0;i<departmentInfo.size();i++){
//            gData.add(new Group(departmentInfo.get(i).getName()));
//
//            lData = new ArrayList<Item>();
//            employeeInfo = employeeDAO.getEmployees(departmentInfo.get(i).getDcid());
//
//            Log.i("employeeSize=", String.valueOf(employeeInfo.size()));
//
//            for (int j=0;j<employeeInfo.size();j++){
//                lData.add(new Item(R.drawable.yonghu,employeeInfo.get(j).getName()+""));
//            }
//            iData.add(lData);
//        }
//
//        myAdapter = new MyBaseExpandableListAdapter(gData,iData,mContext);
//        exlist_staff.setAdapter(myAdapter);
//        exlist_staff.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//            Log.i("" + EmployeelistActivity.this, "group " + groupPosition + " child " + childPosition);
//            employeeInfo = employeeDAO.getEmployees(departmentInfo.get(groupPosition).getDcid());
//            Intent intent = new Intent(EmployeelistActivity.this, EmployeeinfoActivity.class);
//            intent.putExtra("employee",employeeInfo.get(childPosition));
//            startActivity(intent);
//            finish();
//            return false;
//        }
//        });
//       exlist_staff.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
//            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//                                           int arg2, long arg3) {
//                final TextView name = (TextView)arg1.findViewById(R.id.tv_name);
//                final String name_str= name.getText().toString().trim();
//                AlertDialog.Builder builder= new AlertDialog.Builder(EmployeelistActivity.this);
//                builder.setTitle("警告");
//                builder.setMessage("您生在试图删除"+name_str+"这名职员，确定删除吗？");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                         deleteEmployee(name_str);
//            }});
//                builder.setNegativeButton("取消",null);
//                builder.show();
//                return true;
//                }
//       });
//
//        exlist_staff.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//            Log.i("" + EmployeelistActivity.this, "group " + groupPosition);
//            return false;
//        }
//        });
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(EmployeelistActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
//            }
//        });
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(EmployeelistActivity.this, AddEmployeeinfoActivity.class);
//                startActivity(intent);
//                finish();
//                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//    public void  deleteEmployee(String name_str){
//        final String name = name_str;
//        final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl(EmployeelistActivity.this);
//
//       // Log.e("departmentSize", String.valueOf(departmentInfo.size()));
//        Log.e("employeeSize", String.valueOf(employeeInfo.size()));
//        Log.e("employeeInfo", employeeInfo.toString());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//            for(int i=0;i<departmentInfo.size();i++) {
//                employeeInfo = employeeDAO.getEmployees(departmentInfo.get(i).getDcid());
//                for (int j = 0; j < employeeInfo.size(); j++) {
//                    Log.e("employeeName", employeeInfo.get(j).getName());
//                    if (employeeInfo.get(j).getName().equals(name)) {
//                        //读取SharePreferences的cookies
//                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//                        String s = pref.getString("sessionid", "");
//
//                        EmployeeInfoService employeeInfoService = new EmployeeInfoService();
//                        StatusAndMsgJsonBean statusAndMsgJsonBean = employeeInfoService.deleteemployee(s, employeeInfo.get(i).getEid());
//
//
//                        if (statusAndMsgJsonBean.getStatus() == 0) {
//                            employeeDAO.deleteEmployee(employeeInfo.get(j).getEid());
//                            employeeInfo.remove(employeeInfo.get(j));
//
//                            Looper.prepare();//解决子线程弹toast问题
//                            Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_SHORT).show();
//                            Looper.loop();// 进入loop中的循环，查看消息队列
//
//                        } else {
//                            Looper.prepare();//解决子线程弹toast问题
//                            Toast.makeText(getApplicationContext(), "网络错误，获取部门信息失败", Toast.LENGTH_SHORT).show();
//                            Looper.loop();// 进入loop中的循环，查看消息队列
//                        }
//                    }
//                }
//            }
//            }
//        }).start();
//    }
//
//
//
//    /**
//     *  对于这种include下还包含着expendlistview，expendlistview下又有两个xml文件，尝试直接在本Activity用findviewbyid获取item_exlist_iten.xml里的button
//     *  ，然而显示空指针，使用inflater虽然能获取到button，但是捆绑监听事件。最后解决方案很简单，直接在item_exlist_iten.xml里的button添加onclick事件就好了……
//     *  然后在本Activity里写好相应的onclick方法
//     *
//     */
//    //详情单击触发函数
////    public void StaffEdit(View view){
////        //Toast.makeText(mContext, "你点击了详情", Toast.LENGTH_SHORT).show();
////
////        Intent intent = new Intent(EmployeelistActivity.this, EmployeeinfoActivity.class);
////        startActivity(intent);
////        finish();
////    }
//    //删除单机触发函数
////    public void StaffDelete(View view){
////        Toast.makeText(mContext, "你点击了删除", Toast.LENGTH_SHORT).show();
////    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exitBy2Click();
//            return true;
//            //调用双击退出函数
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//    /**
//     * 双击退出函数
//     */
//    private static Boolean isESC = false;
//
//    private void exitBy2Click() {
//        Timer tExit ;
//        if (!isESC) {
//            isESC = true; // 准备退出
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//            tExit = new Timer();
//            tExit.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    isESC = false; // 取消退出
//                }
//            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
//
//        } else {
//            System.exit(0);
//        }
//    }
//}
