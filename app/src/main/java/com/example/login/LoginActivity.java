package com.example.login;

import android.content.Context;
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


/**
 *   登录内容
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mLayoutUsername, mLayoutPwd;
    private EditText mEtUser, mEtPwd;
    private Button mBtnLogin;

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
                mLayoutUsername.setError("请填写正确用户名！");
                return;
            }
            if (!isPasswordValid()) {
                mLayoutPwd.setError("请填写正确密码！");
                return;
            }

            mLayoutUsername.setErrorEnabled(false);
            mLayoutPwd.setErrorEnabled(false);
            Toast.makeText(getApplicationContext(), "OK! I'm performing login.", Toast.LENGTH_SHORT).show();
            //showMessage(getString(R.string.login_success));
        }

        /**
         * 检查输入的用户名是否为空以及是否存在
         *
         * @return
         */
        public boolean isNameValid() {
            String name = mEtUser.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                mLayoutUsername.setErrorEnabled(true);
                mLayoutUsername.setError("用户名不得为空！");
                mEtUser.requestFocus();
                return false;
            }
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
                mLayoutPwd.setErrorEnabled(true);
                mLayoutPwd.setError("密码不得为空！");
                mEtPwd.requestFocus();
                return false;
            }
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
