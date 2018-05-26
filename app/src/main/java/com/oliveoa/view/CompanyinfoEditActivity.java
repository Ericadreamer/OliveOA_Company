package com.oliveoa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.pojo.CompanyInfo;

public class CompanyinfoEditActivity extends AppCompatActivity {

    private CompanyInfo companyInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyinfo_edit);

        companyInfo = getIntent().getParcelableExtra("ParcelableCompany");
        // Log.d("name",companyInfo.getUsername());
        initView();
        ImageView back = (ImageView)findViewById(R.id.info_back);
        ImageView edit = (ImageView)findViewById(R.id.info_edit);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyinfoEditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {   //点击保存键，提示保存是否成功
            @Override
            public void onClick(View view) {
                if(save()){
                    Toast.makeText(getApplicationContext(), "保存成功！点击返回键返回主页", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "保存失败！请检查网络问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        if (companyInfo != null) {
            EditText tname = (EditText) findViewById(R.id.content_username);
            tname.setText(companyInfo.getUsername());
            EditText tfullname = (EditText)findViewById(R.id.content_allname);
            tfullname.setText(companyInfo.getFullname());
            EditText ttel = (EditText)findViewById(R.id.content_tel);
            ttel.setText(companyInfo.getTelephone());
            EditText tfax = (EditText)findViewById(R.id.content_fax);
            tfax.setText(companyInfo.getFax());
            EditText tzip = (EditText)findViewById(R.id.content_zip);
            tzip.setText(companyInfo.getZipcode());
            EditText taddress = (EditText)findViewById(R.id.content_address);
            taddress.setText(companyInfo.getAddress());
            EditText tweb = (EditText)findViewById(R.id.content_website);
            tweb.setText(companyInfo.getWebsite());
            EditText temail = (EditText)findViewById(R.id.content_email);
            temail.setText(companyInfo.getEmail());
            EditText tintro = (EditText)findViewById(R.id.content_intro);
            tintro.setText(companyInfo.getIntroduction());
        }
    }

    public boolean save() {


        return true;
    }

}
