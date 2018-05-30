package com.oliveoa.view;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.erica.oliveoa_company.R;

import java.util.ArrayList;

public class AddEmployeeinfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dcid,sex,pcid;
    private String[] dcidlist;
    private ArrayList<String> pcidlist;
    private String[] sexlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employeeinfo);
        initView();
        initData();
        initListener();
    }

    public  void initView(){

    }

    /**
     * 初始化数据
     */
    private void initData() {

        dcidlist = new String[]{"董事长办公室","财务部","人事部"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, dcidlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dcid.setAdapter(adapter);

        pcidlist = new ArrayList<>();
        pcidlist.add("傻猫");
        pcidlist.add("泉麻麻");
        pcidlist.add("只蕉");
        pcid.setAdapter(new MyAdapter());
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        dcid.setOnItemSelectedListener(this);
        pcid.setOnItemSelectedListener(this);
        sex.setOnItemSelectedListener(this);
    }

    /**
     *
     * @param parent parent是你当前所操作的Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.content_dcid:
                Log.i("所属部门点击------",dcidlist[position]);
                break;
            case R.id.content_pcid:
                Log.i("担任职务点击------",pcidlist.get(position));
                break;
            case R.id.content_sex:
                String[] letter = {"男","女"};
                Log.i("性别点击------",letter[position]);
                break;
        }
    }

    /**
     * 没有数据的时候执行
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 自定义的Adapter
     */
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pcidlist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder holder ;
            if(convertView==null){
                convertView = LayoutInflater.from(AddEmployeeinfoActivity.this).inflate(R.layout.activity_add_employeeinfo, viewGroup, false);
                holder = new ViewHolder();
                //holder.itemText= (TextView) convertView.findViewById(R.id.activity_add_employeeinfo);
                //convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.itemText.setText(pcidlist.get(position));
            return convertView;
        }
    }
    class ViewHolder {
        TextView itemText;
    }

}
