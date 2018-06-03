package com.oliveoa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.pojo.DepartmentInfo;

import java.util.ArrayList;

public class DepartmentSelectActivity extends AppCompatActivity {

    private ArrayList<DepartmentInfo> departmentInfos;
    private ImageView back,add;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDPlistView;
    private TextView tvname,tvid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_select);

        departmentInfos = getIntent().getParcelableArrayListExtra("ParcelableDepartment");
        System.out.println(departmentInfos);

        initview();
    }

    public void initview(){
        back = (ImageView)findViewById(R.id.null_back);
        add = (ImageView)findViewById(R.id.null_add);
        addDPlistView = (LinearLayout)findViewById(R.id.depart_list);

        //默认添加一个Item
        //addViewItem(null);

        //监听事件
//        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DepartmentActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        add.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DepartmentActivity.this, CreateDepartmentActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }


}
