package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.os.PatternMatcher;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.CompanyInfoService;
import com.oliveoa.jsonbean.CompanyLoginJsonBean;
import com.oliveoa.jsonbean.UpdateCompanyInfoJsonBean;
import com.oliveoa.pojo.CompanyInfo;

import static com.oliveoa.util.Validator.isEmail;
import static com.oliveoa.util.Validator.isFixPhone;
import static com.oliveoa.util.Validator.isMobile;
import static com.oliveoa.util.Validator.isUrl;
import static com.oliveoa.util.Validator.isZipCode;

public class CompanyinfoEditActivity extends AppCompatActivity {

    private CompanyInfo companyInfo;
    private EditText tfullname,ttel,tfax,tzip,taddress,tweb,temail,tintro;
    private TextView tname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyinfo_edit);

        companyInfo = getIntent().getParcelableExtra("ParcelableCompany");
        // Log.d("name",companyInfo.getUsername());
        initView();
        ImageView back = (ImageView)findViewById(R.id.info_back);
        ImageView save = (ImageView)findViewById(R.id.info_save);

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyinfoEditActivity.this, MainActivity.class);
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
    }

    private void initView() {
        if (companyInfo != null) {
            tname = (TextView) findViewById(R.id.content_username);
            tname.setText(companyInfo.getUsername());
            tfullname = (EditText)findViewById(R.id.content_allname);
            tfullname.setText(companyInfo.getFullname());
            ttel = (EditText)findViewById(R.id.content_tel);
            ttel.setText(companyInfo.getTelephone());
            tfax = (EditText)findViewById(R.id.content_fax);
            tfax.setText(companyInfo.getFax());
            tzip = (EditText)findViewById(R.id.content_zip);
            tzip.setText(companyInfo.getZipcode());
            taddress = (EditText)findViewById(R.id.content_address);
            taddress.setText(companyInfo.getAddress());
            tweb = (EditText)findViewById(R.id.content_website);
            tweb.setText(companyInfo.getWebsite());
            temail = (EditText)findViewById(R.id.content_email);
            temail.setText(companyInfo.getEmail());
            tintro = (EditText)findViewById(R.id.content_intro);
            tintro.setText(companyInfo.getIntroduction());
        }
    }

    public void save() {
        companyInfo.setFullname(tfullname.getText().toString().trim());
        companyInfo.setTelephone( ttel.getText().toString().trim());
        companyInfo.setFax(tfax.getText().toString().trim());
        companyInfo.setZipcode(tzip.getText().toString().trim());
        companyInfo.setAddress(taddress.getText().toString().trim());
        companyInfo.setWebsite(tweb.getText().toString().trim());
        companyInfo.setEmail( temail.getText().toString().trim());
        companyInfo.setIntroduction(tintro.getText().toString().trim());

        if (TextUtils.isEmpty(companyInfo.getFullname())||TextUtils.isEmpty(companyInfo.getTelephone())||TextUtils.isEmpty(companyInfo.getFax())||TextUtils.isEmpty(companyInfo.getZipcode())||TextUtils.isEmpty(companyInfo.getAddress())||TextUtils.isEmpty(companyInfo.getEmail())||TextUtils.isEmpty(companyInfo.getWebsite())||TextUtils.isEmpty(companyInfo.getIntroduction())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else if(!isMobile(companyInfo.getTelephone())){
            Toast.makeText(getApplicationContext(), "公司电话格式输入错误！请以手机格式重新输入", Toast.LENGTH_SHORT).show();
        } else if(!isFixPhone(companyInfo.getFax())){
            Toast.makeText(getApplicationContext(), "传真机格式输入错误！请以固话格式重新输入", Toast.LENGTH_SHORT).show();
        } else if(!isZipCode(companyInfo.getZipcode())){
            Toast.makeText(getApplicationContext(), "邮编号码格式输入错误！请重新输入", Toast.LENGTH_SHORT).show();
        } else if(!isUrl(companyInfo.getWebsite())){
            Toast.makeText(getApplicationContext(), "网址格式输入错误！请以http://开头重新输入", Toast.LENGTH_SHORT).show();
        } else if(!isEmail(companyInfo.getEmail())) {
            Toast.makeText(getApplicationContext(), "邮箱格式输入错误！请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    CompanyInfoService companyInfoService = new CompanyInfoService();
                    UpdateCompanyInfoJsonBean updateCompanyInfoJsonBean = companyInfoService.updatecompanyinfo(s,companyInfo);
                    Log.d("update" ,updateCompanyInfoJsonBean.getMsg()+"");

                    if (updateCompanyInfoJsonBean.getStatus() == 0) {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "保存成功！点击返回键返回主页", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), updateCompanyInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                }
            }).start();

        }
    }

}
