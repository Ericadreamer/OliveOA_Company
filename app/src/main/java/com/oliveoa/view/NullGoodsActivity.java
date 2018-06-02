package com.oliveoa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.erica.oliveoa_company.R;
public class NullGoodsActivity extends AppCompatActivity {

    private ImageView back,add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_goods);
    }

    public void initView() {

        back = (ImageView)findViewById(R.id.back);
        add = (ImageView)findViewById(R.id.add);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NullGoodsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NullGoodsActivity.this, AddGoodsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
