package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.CompanyInfoService;
import com.oliveoa.controller.LoginService;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.jsonbean.LogoutJsonBean;
import com.oliveoa.pojo.CompanyInfo;

public class CompanyinfoActivity extends AppCompatActivity {

    private  CompanyInfo companyInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyinfo);

        companyInfo = getIntent().getParcelableExtra("ParcelableCompany");
       // Log.d("name",companyInfo.getUsername());
        initView();


        ImageView back = (ImageView)findViewById(R.id.info_back);
        ImageView edit = (ImageView)findViewById(R.id.info_edit);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyinfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });
    }

    //初始化
    private void initView() {
        if (companyInfo != null) {
            TextView tname = (TextView) findViewById(R.id.content_username);
            tname.setText(companyInfo.getUsername());
            TextView tfullname = (TextView)findViewById(R.id.content_allname);
            tfullname.setText(companyInfo.getFullname());
            TextView ttel = (TextView)findViewById(R.id.content_tel);
            ttel.setText(companyInfo.getTelephone());
            TextView tfax = (TextView)findViewById(R.id.content_fax);
            tfax.setText(companyInfo.getFax());
            TextView tzip = (TextView)findViewById(R.id.content_zip);
            tzip.setText(companyInfo.getZipcode());
            TextView taddress = (TextView)findViewById(R.id.content_address);
            taddress.setText(companyInfo.getAddress());
            TextView tweb = (TextView)findViewById(R.id.content_website);
            tweb.setText(companyInfo.getWebsite());
            TextView temail = (TextView)findViewById(R.id.content_email);
            temail.setText(companyInfo.getEmail());
            TextView tintro = (TextView)findViewById(R.id.content_intro);
            tintro.setText(companyInfo.getIntroduction());
        }

    }

    public void edit() {
            Intent intent = new Intent(CompanyinfoActivity.this, CompanyinfoEditActivity.class);
            intent.putExtra("ParcelableCompany",companyInfo);
            startActivity(intent);
            finish();

    }


}
