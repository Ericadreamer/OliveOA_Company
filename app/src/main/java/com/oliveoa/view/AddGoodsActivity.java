package com.oliveoa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.erica.oliveoa_company.R;


public class AddGoodsActivity extends AppCompatActivity {

    private ImageView back,save,next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);

        initView();
    }

    public void initView() {

        back = (ImageView)findViewById(R.id.back);
        save = (ImageView)findViewById(R.id.save);
        next = (ImageView)findViewById(R.id.next);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGoodsActivity.this, GoodsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {   //点击保存键，提示保存是否成功
            @Override
            public void onClick(View view) {
                save();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddGoodsActivity.this, ChooseDutyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void save() {

    }



}
