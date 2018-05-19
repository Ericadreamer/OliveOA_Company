package com.oliveoa.view;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.common.HttpResponseObject;
import com.oliveoa.controller.LoginService;
import com.oliveoa.pojo.CompanyInfo;

/**
 *   登录内容
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mLayoutUsername, mLayoutPwd;
    private EditText mEtUser, mEtPwd;
    private Button mBtnLogin;
    private View loadingLayout1,loadingLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    //初始化
    private void initView() {
        mLayoutUsername = (TextInputLayout) findViewById(R.id.usernameWrapper);
        mLayoutPwd = (TextInputLayout) findViewById(R.id.passwordWrapper);


        mEtUser = (EditText) findViewById(R.id.username);
        mEtPwd = (EditText) findViewById(R.id.password);

        mBtnLogin = (Button) findViewById(R.id.login_btn);

        //直线隐藏
        loadingLayout1 =findViewById(R.id.ll_control1);
        loadingLayout2 =findViewById(R.id.ll_control2);

        //设置监听事件
        mBtnLogin.setOnClickListener(this);
        mEtUser.addTextChangedListener(new MyTextWatcher(mEtUser));
        mEtPwd.addTextChangedListener(new MyTextWatcher(mEtPwd));

    }

        @Override
        public void onClick(View v) {
            hideKeyboard();//隐藏键盘
            switch (v.getId()) {
                case R.id.login_btn:
                    Login();
                    break;
                default:
                    break;
            }
        }

        /**
         * 隐藏键盘
         */
        private void hideKeyboard() {
            View view = getCurrentFocus();
            if (view != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        /**
         * 登录
         */
        private void Login() {
            if (!isNameValid()) {
                loadingLayout1.setVisibility(View.GONE);//隐藏直线
                mLayoutUsername.setError("请填写正确用户名！");
                return;
            }
            if (!isPasswordValid()) {
                loadingLayout2.setVisibility(View.GONE);//隐藏直线
                mLayoutPwd.setError("请填写正确密码！");
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String idvalu1e = mEtUser.getText().toString().trim();
                    String pwdvalue = mEtPwd.getText().toString().trim();
                    LoginService loginService = new LoginService();
                    HttpResponseObject<CompanyInfo> httpResponseObject = loginService.login(idvalu1e,pwdvalue);
                    System.out.println("httpResponseObject.getStatus() = "+httpResponseObject.getStatus());

                    if (httpResponseObject.getStatus()==0){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), "用户名或密码错误，请重新登录", Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循环，查看消息队列

                    }
                }
            }).start();
            mLayoutUsername.setErrorEnabled(false);
            mLayoutPwd.setErrorEnabled(false);
            loadingLayout1.setVisibility(View.VISIBLE);//显示直线
            loadingLayout2.setVisibility(View.VISIBLE);//显示直线
            //showMessage(getString(R.string.login_success));


        }

        /**
         * 检查输入的用户名是否为空
         *
         * @return
         */
        public boolean isNameValid() {
            String name = mEtUser.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                loadingLayout1.setVisibility(View.GONE);//隐藏直线
                mLayoutUsername.setErrorEnabled(true);
                mLayoutUsername.setError("用户名不得为空！");
                mEtUser.requestFocus();
                return false;
            }
            loadingLayout1.setVisibility(View.VISIBLE);//显示直线
            mLayoutUsername.setErrorEnabled(false);
            return true;
        }

        /**
         * 检查输入的密码是否为空
         *
         * @return
         */
        public boolean isPasswordValid() {
            String pwd = mEtPwd.getText().toString().trim();
            if (TextUtils.isEmpty(pwd)) {
                loadingLayout2.setVisibility(View.GONE);//隐藏直线
                mLayoutPwd.setErrorEnabled(true);
                mLayoutPwd.setError("密码不得为空！");
                mEtPwd.requestFocus();
                return false;
            }
            loadingLayout2.setVisibility(View.VISIBLE);//显示直线
            mLayoutPwd.setErrorEnabled(false);
            return true;
        }


        //动态监听输入过程
        private class MyTextWatcher implements TextWatcher {

            private View view;

            private MyTextWatcher(View view) {
                this.view = view;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (view.getId()) {
                    case R.id.usernameWrapper:
                        isNameValid();
                        break;
                    case R.id.passwordWrapper:
                        isPasswordValid();
                        break;
                }
            }
        }

        private void showMessage(String message) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

}
