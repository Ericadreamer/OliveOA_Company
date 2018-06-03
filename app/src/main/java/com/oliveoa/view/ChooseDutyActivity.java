package com.oliveoa.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;

public class ChooseDutyActivity extends AppCompatActivity {

    private ImageView back;
    private TextView department,duty;
    private ExpandableListView expandableListView;
    private List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> data_list1 = new ArrayList<Map<String, Object>>();
    private String[] content = { "技术顾问", "主任", "秘书", "设备", "程序猿"};
    private String[] content1 = { "技术部"};
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_duty);

        initView();

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        data();
        data1();
        adapter = new MyAdapter(data_list, data_list1);
        expandableListView.setAdapter(adapter);
    }

    public void initView() {
        back = (ImageView)findViewById(R.id.back);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseDutyActivity.this, AddGoodsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public class MyAdapter extends BaseExpandableListAdapter {
        List<Map<String, Object>> data_list;
        List<Map<String, Object>> data_list1;

        public MyAdapter(List<Map<String, Object>> data_list,
                         List<Map<String, Object>> data_list1) {
            this.data_list = data_list;
            this.data_list1 = data_list1;
        }

        @Override
        public int getGroupCount() {
            return data_list1.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return data_list.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return data_list1.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return data_list.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        /**
         * 显示：group
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            ViewGroupHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ChooseDutyActivity.this).inflate(
                        R.layout.lv_group, null);
                holder = new ViewGroupHolder();
                holder.tv_group = (TextView) convertView
                        .findViewById(R.id.tv_group);
                convertView.setTag(holder);
            } else {
                holder = (ViewGroupHolder) convertView.getTag();
            }
            holder.tv_group.setText((String) data_list1.get(groupPosition).get(
                    "content1"));

            return convertView;

        }


        /**
         * 显示：child
         */
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ViewChildrenHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(ChooseDutyActivity.this).inflate(
                        R.layout.lv_children, null);
                holder = new ViewChildrenHolder();
                holder.tv_name = (TextView) convertView
                        .findViewById(R.id.tv_duty);

                convertView.setTag(holder);
            } else {
                holder = (ViewChildrenHolder) convertView.getTag();
            }
            holder.tv_name.setText((String) data_list.get(childPosition).get(
                    "content"));

            return convertView;
        }

        class ViewGroupHolder {
            TextView tv_group;
        }

        class ViewChildrenHolder {
            TextView tv_name;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

    }

    private void data() {
        for (int i = 0; i < content.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content[i]);
            data_list.add(map);
        }
    }

    private void data1() {
        for (int j = 0; j < content1.length; j++) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("content1", content1[j]);
            data_list1.add(map1);
        }
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
