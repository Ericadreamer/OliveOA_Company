package com.oliveoa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erica.oliveoa_company.R;

public class RedactDepartmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redact_department);

        ImageView back = (ImageView)findViewById(R.id.null_back);



        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RedactDepartmentActivity.this, DepartmentInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void save() {

    }
}
