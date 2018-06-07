package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DutyInfoActivity extends AppCompatActivity {

    private ArrayList<DutyInfo> dutyInfo;
    private ArrayList<DepartmentInfo> departmentInfo;
    private int dpindex,dtindex;
    private String TAG = this.getClass().getSimpleName();
    private TextView tname,tnum,tppid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutyinfo);

        dutyInfo = getIntent().getParcelableArrayListExtra("ParcelableDuty");
        Log.e("dutyInfos",dutyInfo.toString());
        dtindex = getIntent().getIntExtra("duty_index",dtindex);
        Log.e("dtindex", String.valueOf(dtindex));

        departmentInfo = getIntent().getParcelableArrayListExtra("ParcelableDepartment");
        dpindex = getIntent().getIntExtra("department_index",dpindex);


        initView();




    }

    //初始化
    public void initView() {
        ImageView back = (ImageView)findViewById(R.id.null_back);
        ImageView edit = (ImageView) findViewById(R.id.info_edit);
        //TextView add = (TextView)findViewById(R.id.duty_add);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DutyInfoActivity.this, DepartmentActivity.class);
                intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfo);
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


        SharedPreferences pref = getSharedPreferences("duty",MODE_PRIVATE);
        tname = (TextView) findViewById(R.id.tv_duty_name);
        tname.setText(pref.getString("name["+dtindex+"]",""));
        tnum = (TextView) findViewById(R.id.tv_num);
        tnum.setText(pref.getString("limit["+dtindex+"]",""));
        tppid = (TextView) findViewById(R.id.text_superior);
        tppid.setText(pref.getString("pname["+dtindex+"]",""));

    }

    //编辑操作
    public void edit() {
        Intent intent = new Intent(DutyInfoActivity.this, EditDutyInfoActivity.class);
        intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfo);
        intent.putParcelableArrayListExtra("ParcelableDuty",dutyInfo);
        intent.putExtra("dpindex",dpindex);
        intent.putExtra("dtindex",dtindex);
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
