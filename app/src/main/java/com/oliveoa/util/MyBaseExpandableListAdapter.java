package com.oliveoa.util;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.daoimpl.EmployeeDAOImpl;
import com.oliveoa.pojo.Item;
import com.oliveoa.pojo.Group;
import com.oliveoa.view.EmployeeinfoActivity;
import com.oliveoa.view.EmployeelistActivity;

import java.util.ArrayList;

public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {

        private ArrayList<Group> gData; //父列表项
        private ArrayList<ArrayList<Item>> iData;  //子列表项
        private Context mContext; //mContext为ExpandableListView所在的Activity，通过构造方法的参数传递进来，可以获得该Activity对应的Inflater

        public MyBaseExpandableListAdapter(ArrayList<Group> gData, ArrayList<ArrayList<Item>> iData, Context mContext) {
            this.gData = gData;
            this.iData = iData;
            this.mContext = mContext;
        }

        @Override
        public int getGroupCount() {
            return gData.size();
        }

        @Override //获得子列表项的数目
        public int getChildrenCount(int groupPosition) {
            return iData.get(groupPosition).size();
        }

        @Override
        public Group getGroup(int groupPosition) {
            return gData.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) { //获取子列表项对应的Item
            return iData.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override   //获取子列表项id
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            ViewHolderGroup groupHolder;
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.item_exlist_group, parent, false);
                groupHolder = new ViewHolderGroup();
                groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.tv_group_name);
                convertView.setTag(groupHolder);
            }else{
                groupHolder = (ViewHolderGroup) convertView.getTag();
            }
            groupHolder.tv_group_name.setText(gData.get(groupPosition).getgName());
            return convertView;
        }

        //取得显示给定分组给定子位置的数据用的视图
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final ViewHolderItem itemHolder;
            if(convertView == null){ //如果第一次调用则convertView为null，需要获得对应的layout布局文件：子列表项的布局R.layout.item_exlist_item
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.item_exlist_item, parent, false);
                itemHolder = new ViewHolderItem();
                itemHolder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
                itemHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                itemHolder.edit = (Button) convertView.findViewById(R.id.btnEdit);
                itemHolder.delete = (Button) convertView.findViewById(R.id.btnDelete);
                convertView.setTag(itemHolder);
            }else{
                itemHolder = (ViewHolderItem) convertView.getTag();
            }
            itemHolder.img_icon.setImageResource(iData.get(groupPosition).get(childPosition).getiId());
            itemHolder.tv_name.setText(iData.get(groupPosition).get(childPosition).getiName());

            return convertView;
        }

        //设置子列表是否可选中
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }


        private static class ViewHolderGroup{
            private TextView tv_group_name;
        }

        private static class ViewHolderItem{
            private ImageView img_icon;
            private TextView tv_name;
            private Button edit;
            private Button delete;
        }



}
