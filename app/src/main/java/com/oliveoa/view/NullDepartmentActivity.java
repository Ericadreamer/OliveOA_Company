package com.oliveoa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.erica.oliveoa_company.R;


public class NullDepartmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_department);

        ImageView back = (ImageView)findViewById(R.id.null_back);
        ImageView add = (ImageView)findViewById(R.id.null_add);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NullDepartmentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

    }

    public void add() {
        Intent intent = new Intent(NullDepartmentActivity.this, CreateDepartmentActivity.class);
        startActivity(intent);
        finish();

    }

}
