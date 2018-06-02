package com.oliveoa.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DepartmentInfoActivity extends AppCompatActivity {

    private ArrayList<DepartmentInfo> departmentInfo;
    private DutyInfo dutyInfo;
    private String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_info);

        //departmentInfo = getIntent().getParcelableArrayListExtra("ParcelableDepartment");
        //System.out.println(departmentInfo);


        initView();

        ImageView back = (ImageView)findViewById(R.id.null_back);
        TextView edit = (TextView)findViewById(R.id.depart_edit);
        TextView add = (TextView)findViewById(R.id.duty_add);


        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentInfoActivity.this, DepartmentActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
    }

    //初始化
    public void initView() {
//        if(departmentInfo != null){
//            TextView tid = (TextView) findViewById(R.id.text_num);
//            tid.setText(departmentInfo.getId());
//            TextView tname = (TextView) findViewById(R.id.text_name);
//            tname.setText(departmentInfo.getName());
//            TextView ttelephone = (TextView) findViewById(R.id.text_tel);
//            ttelephone.setText(departmentInfo.getTelephone());
//            TextView tfax = (TextView) findViewById(R.id.text_fax);
//            tfax.setText(departmentInfo.getFax());
//            TextView tdpid = (TextView) findViewById(R.id.text_superior);
//            tdpid.setText(departmentInfo.getDpid());
//        }
    }

    public void edit() {
        Intent intent = new Intent(DepartmentInfoActivity.this, RedactDepartmentActivity.class);
        intent.putExtra("ParcelableDepartment",departmentInfo);
        startActivity(intent);
        finish();

    }

    public void add() {
        Intent intent = new Intent(DepartmentInfoActivity.this, AddDutyActivity.class);
        intent.putExtra("ParcelableDuty",dutyInfo);
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
