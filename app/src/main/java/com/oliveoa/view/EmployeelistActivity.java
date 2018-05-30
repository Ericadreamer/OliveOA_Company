package com.oliveoa.view;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.EmployeeInfoService;
import com.oliveoa.jsonbean.EmployeeInfoJsonBean;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.pojo.Group;
import com.oliveoa.pojo.Item;
import com.oliveoa.util.MyBaseExpandableListAdapter;


public class EmployeelistActivity extends AppCompatActivity {

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Context mContext;
    private ExpandableListView exlist_staff;
    private MyBaseExpandableListAdapter myAdapter = null;
    private Button edit,delete;
    private EmployeeInfo employeeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeelist);
        employeeInfo = getIntent().getParcelableExtra("ParcelableEmployeeInfo");
        Log.d("ParcelableEmployeeInfo", employeeInfo.toString());
        initView();

    }

    public void initView(){
        mContext = EmployeelistActivity.this;

        exlist_staff = (ExpandableListView) findViewById(R.id.exlist_staff);
        edit =(Button)findViewById(R.id.btnEdit);
        delete =(Button)findViewById(R.id.btnDelete);

        //数据准备
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();
        gData.add(new Group("董事长办公室"));
        gData.add(new Group("财务部"));
        gData.add(new Group("人事部"));

        lData = new ArrayList<Item>();

        //董事长办公室组
        lData.add(new Item(R.drawable.yonghu,"长腿傻猫"));
        lData.add(new Item(R.drawable.yonghu,"机智蕉蕉"));
        iData.add(lData);
        //财务部组
        lData = new ArrayList<Item>();
        lData.add(new Item(R.drawable.yonghu,"长腿傻猫"));
        lData.add(new Item(R.drawable.yonghu,"机智蕉蕉"));
        iData.add(lData);
        //人事部组
        lData = new ArrayList<Item>();
        lData.add(new Item(R.drawable.yonghu,"泉麻麻"));
        lData.add(new Item(R.drawable.yonghu,"泉麻麻"));
        lData.add(new Item(R.drawable.yonghu,"泉麻麻"));
        lData.add(new Item(R.drawable.yonghu,"泉麻麻"));
        iData.add(lData);

        myAdapter = new MyBaseExpandableListAdapter(gData,iData,mContext);
        exlist_staff.setAdapter(myAdapter);

        //为列表设置点击事件
        exlist_staff.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext, "你点击了：" + iData.get(groupPosition).get(childPosition).getiName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "你点击了编辑", Toast.LENGTH_SHORT).show();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "你点击了删除", Toast.LENGTH_SHORT).show();

            }
        });



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
