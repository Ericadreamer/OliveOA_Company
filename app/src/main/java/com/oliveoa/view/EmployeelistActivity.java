package com.oliveoa.view;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.pojo.Group;
import com.oliveoa.pojo.Item;
import com.oliveoa.util.MyBaseExpandableListAdapter;

import java.util.ArrayList;

public class EmployeelistActivity extends AppCompatActivity {

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Context mContext;
    private ExpandableListView exlist_lol;
    private MyBaseExpandableListAdapter myAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = EmployeelistActivity.this;
        exlist_lol = (ExpandableListView) findViewById(R.id.exlist_staff);


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
        exlist_lol.setAdapter(myAdapter);

        //为列表设置点击事件
        exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext, "你点击了：" + iData.get(groupPosition).get(childPosition).getiName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }
}
