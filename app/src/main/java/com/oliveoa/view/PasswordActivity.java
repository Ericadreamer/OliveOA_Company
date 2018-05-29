package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.CompanyInfoService;
import com.oliveoa.jsonbean.UpdateCompanyInfoJsonBean;

import java.util.Timer;
import java.util.TimerTask;

import static com.oliveoa.util.Validator.isPassword;

public class PasswordActivity extends AppCompatActivity {

    private String companypwd;
    private EditText eoriginpwd,enewpwd,ecomfirmpwd;//分别为原密码，新密码，确认密码
    private ImageView back,save;
    private  String comfirmpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        companypwd = getIntent().getStringExtra("CompanyPassword");
        initView();
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
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
         back = (ImageView)findViewById(R.id.info_back);
         save = (ImageView)findViewById(R.id.info_save);
         eoriginpwd= (EditText)findViewById(R.id.content_originpwd);
         enewpwd= (EditText)findViewById(R.id.content_newpwd);
         ecomfirmpwd= (EditText)findViewById(R.id.content_comfirmpwd);
    }
    public void save() {
       String originpwd = eoriginpwd.getText().toString().trim();
       String newpwd = enewpwd.getText().toString().trim();
       comfirmpwd = ecomfirmpwd.getText().toString().trim();

        if (TextUtils.isEmpty(originpwd)||TextUtils.isEmpty(newpwd)||TextUtils.isEmpty(comfirmpwd)) {
            Toast.makeText(getApplicationContext(), "信息不得为空！请检查输入信息", Toast.LENGTH_SHORT).show();
        } else if(!newpwd.equals(comfirmpwd)){
            Toast.makeText(getApplicationContext(), "新密码与确认密码输入不一致！请重新输入", Toast.LENGTH_SHORT).show();
        } else if(!companypwd.equals(originpwd)) {
            Toast.makeText(getApplicationContext(), "原密码输入错误！请重新输入", Toast.LENGTH_SHORT).show();
        }else if(!isPassword(comfirmpwd)){
            Toast.makeText(getApplicationContext(), "密码格式输入错误！请按错提示输入", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    CompanyInfoService companyInfoService = new CompanyInfoService();
                    UpdateCompanyInfoJsonBean updateCompanyInfoJsonBean = companyInfoService.updatepassword(s,comfirmpwd);
                    Log.d("update" ,updateCompanyInfoJsonBean.getMsg()+"");

                    if (updateCompanyInfoJsonBean.getStatus() == 0) {
                        pref.edit().remove("sessionid").commit();//移除指定数值
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "修改成功！请重新登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
            //调用双击退出函数
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 双击退出函数
     */
    private static Boolean isESC = false;

    private void exitBy2Click() {
        Timer tExit ;
        if (!isESC) {
            isESC = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isESC = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            System.exit(0);
        }
    }
}
