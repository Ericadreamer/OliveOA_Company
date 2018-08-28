//package com.oliveoa.view;
//
//import android.content.Intent;
//import android.os.TestLooperManager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.ExpandableListView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.erica.oliveoa_company.R;
//import com.oliveoa.dao.DepartmentDAO;
//import com.oliveoa.daoimpl.DepartmentDAOImpl;
//import com.oliveoa.daoimpl.DutyDAOImpl;
//import com.oliveoa.daoimpl.EmployeeDAOImpl;
//import com.oliveoa.pojo.DepartmentInfo;
//import com.oliveoa.pojo.DutyInfo;
//import com.oliveoa.pojo.EmployeeInfo;
//import com.oliveoa.pojo.Group;
//import com.oliveoa.pojo.Item;
//import com.oliveoa.util.MyBaseExpandableListAdapter;
//
//import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class EmployeeinfoActivity extends AppCompatActivity {
//
//    private ImageView back,edit;
//    private TextView tname,dname,pname,id,sex,birth,tel,email,address;
//
//
//    private ArrayList<EmployeeInfo> employeeInfos;
//    private ArrayList<DepartmentInfo> departmentInfos;
//    private ArrayList<DutyInfo> dutynfos;
//    private EmployeeInfo employeeInfo;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_employeeinfo);
//
//        employeeInfo = getIntent().getParcelableExtra("employee");
//        Log.d("ParcelableEmployee", employeeInfo.toString());
//        initView();
//    }
//
//
//    public void initView(){
//
//        back =(ImageView)findViewById(R.id.info_back);
//        edit =(ImageView)findViewById(R.id.info_edit);
//
//        tname =(TextView) findViewById(R.id.content_name);
//        //dname =(TextView) findViewById(R.id.content_id);
//        pname =(TextView) findViewById(R.id.content_pcid);
//        id =(TextView)findViewById(R.id.content_id);
//        sex = (TextView)findViewById(R.id.content_sex);
//        birth =(TextView)findViewById(R.id.content_birth);
//        tel =(TextView)findViewById(R.id.content_tel);
//        email = (TextView)findViewById(R.id.content_email);
//        address =(TextView)findViewById(R.id.content_address);
//
//
//        initData();
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(EmployeeinfoActivity.this, EmployeelistActivity.class);
//                startActivity(intent);
//                finish();
//                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(EmployeeinfoActivity.this, EditEmployeeinfoActivity.class);
//                intent.putExtra("employee",employeeInfo);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//    }
//    public void initData(){
//        final EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl(EmployeeinfoActivity.this);
//        final DepartmentDAO departmentDAO = new DepartmentDAOImpl(EmployeeinfoActivity.this);
//        final DutyDAOImpl dutyDAO = new DutyDAOImpl(EmployeeinfoActivity.this);
//
//        tname.setText(employeeInfo.getName());
//
//        String dname=null;
//        departmentInfos = departmentDAO.getDepartments();
//        for(int i =0;i<departmentInfos.size();i++){
//            if(employeeInfo.getDcid().equals(departmentInfos.get(i).getDcid())){
//                dname=departmentInfos.get(i).getName();
//            }
//        }
//
//        for(int i =0;i<departmentInfos.size();i++) {
//            dutynfos = dutyDAO.getDutys(departmentInfos.get(i).getDcid());
//            for (int j = 0; j < dutynfos.size(); j++) {
//                if(employeeInfo.getPcid().equals(dutynfos.get(j).getPcid())){
//                    pname.setText(dname+":"+dutynfos.get(j).getName());
//                }
//            }
//        }
//
//        id.setText(employeeInfo.getId());
//        sex.setText(employeeInfo.getSex());
//        birth.setText(employeeInfo.getBirth());
//        tel.setText(employeeInfo.getTel());
//        email.setText(employeeInfo.getEmail());
//        address.setText(employeeInfo.getAddress());
//
//    }
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
