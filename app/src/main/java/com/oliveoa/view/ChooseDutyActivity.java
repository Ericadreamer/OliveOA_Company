package com.oliveoa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.erica.oliveoa_company.R;

public class ChooseDutyActivity extends AppCompatActivity {

    private ImageView back;
    private TextView tvShow;
    private Spinner spDown;
    private List<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_duty);

        //initView();
    }

//    public void initView() {
//        back = (ImageView)findViewById(R.id.back);
//        tvShow=(TextView) findViewById(R.id.tvShow);
//        spDown=(Spinner) findViewById(R.id.spDown);
//
//        /*设置数据源*/
//        list=new ArrayList<String>();
//        list.add("北京");
//        list.add("上海");
//        list.add("广州");
//        list.add("深圳");
//
//        /*新建适配器*/
//        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
//
//        /*adapter设置一个下拉列表样式，参数为系统子布局*/
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//
//        /*spDown加载适配器*/
//        spDown.setAdapter(adapter);
//
//        /*soDown的监听器*/
//        spDown.setOnItemSelectedListener((OnItemSelectedListener) this);
//
//        //点击事件
//        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ChooseDutyActivity.this, AddGoodsActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptions(Bundle savedInstanceState) {
//        getMenuInflater().inflate(R.layout.department_duty_list, menu);
//        return true;
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position,
//                               long id) {
//        String cityName=adapter.getItem(position);   //获取选中的那一项
//        tvShow.setText("您选择的城市是"+cityName);
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//    }
}
