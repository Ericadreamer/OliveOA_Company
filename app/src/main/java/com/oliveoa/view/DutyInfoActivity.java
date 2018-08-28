package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;

import java.util.Timer;
import java.util.TimerTask;

public class DutyInfoActivity extends AppCompatActivity {

    private DutyInfo dutyInfo;
    private String dutyname,ppname;
    private String TAG = this.getClass().getSimpleName();
    private TextView tname,tnum,tppid;
    private DutyInfoDao dutyInfoDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutyinfo);
        dutyname = getIntent().getStringExtra("dutyname");
        initData();

    }

    //初始化
    public  void initData(){
        dutyInfoDao = EntityManager.getInstance().dutyInfoDao;
        dutyInfo = dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Name.eq(dutyname)).unique();
        if(dutyInfo.getPpid()==null){
            ppname="无";
        }else{
            ppname = dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Pcid.eq(dutyInfo.getPpid())).unique().getName();
        }

        initView();
    }

    public void initView() {
        ImageView back = (ImageView)findViewById(R.id.null_back);
        ImageView edit = (ImageView) findViewById(R.id.info_edit);
        //TextView add = (TextView)findViewById(R.id.duty_add);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DutyInfoActivity.this, DepartmentActivity.class);
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
        tname.setText(dutyInfo.getName());
        tnum = (TextView) findViewById(R.id.tv_num);
        tnum.setText(dutyInfo.getLimit());
        tppid = (TextView) findViewById(R.id.text_superior);
        tppid.setText(ppname);

    }

    //编辑操作
    public void edit() {
        Intent intent = new Intent(DutyInfoActivity.this, EditDutyInfoActivity.class);
        intent.putExtra("pcid",dutyInfo.getPcid());
        intent.putExtra("index",1);
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
