package com.oliveoa.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.common.HttpResponseObject;
import com.oliveoa.controller.LoginService;
import com.oliveoa.jsonbean.LogoutJsonBean;
import com.oliveoa.pojo.CompanyInfo;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MainActivity extends Activity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private SystemBarTintManager tintManager;
    private NavigationView navigationView;
    ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initWindow();
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_na);
        navigationView = (NavigationView) findViewById(R.id.nav);
        menu= (ImageView) findViewById(R.id.main_menu);
        View headerView = navigationView.getHeaderView(0);//获取头布局
        menu.setOnClickListener(this);
        //设置菜单图标为默认原图颜色
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //item.setChecked(true);
                //Toast.makeText(MainActivity.this,item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                //
                // startActivity(intent);
                // finish();
                int id = item.getItemId();
                String string = null;
                switch (id){
                    case R.id.nav_name:
                        companyinfo();
                        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        break;
                    case R.id.nav_advise:

                        break;
                    case R.id.nav_aboutus:

                        break;
                    case R.id.nav_update:
                        Toast.makeText(getApplicationContext(), "您的版本已是最新版本！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_exit:
                         logout();
                        break;


                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });

}
    private void companyinfo(){
        Intent intent = new Intent(MainActivity.this, CompanyinfoActivity.class);
        startActivity(intent);
        finish();
    }

    private void logout() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                LoginService loginService = new LoginService();
                LogoutJsonBean logoutJsonBean = loginService.logout(s);
                //System.out.println("logoutisSuccess = "+ logoutJsonBean.getStatus());

                if (logoutJsonBean.getStatus()==0){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(), "网络错误，请重试", Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列

                }
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_menu://点击菜单，跳出侧滑菜单
                if (drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.closeDrawer(navigationView);
                }else{
                    drawerLayout.openDrawer(navigationView);
                }
                break;
        }
    }
    private void initWindow() {//初始化窗口属性，让状态栏和导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
          //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            int statusColor = Color.parseColor("#373B3E");
            tintManager.setStatusBarTintColor(statusColor);
            tintManager.setStatusBarTintEnabled(true);

            //设置图标大小
           //公文查看
           Resources res = getResources() ;
            RadioButton gwRB = (RadioButton)findViewById(R.id.fileicon) ;
            Drawable gwDrawable = res.getDrawable(R.drawable.liebiao) ;
            gwDrawable.setBounds(0,0,120,120);//这里就可以控制drawable资源的大小了
            gwRB.setCompoundDrawables(null,gwDrawable,null,null);
            //部门管理
            RadioButton bmRB = (RadioButton)findViewById(R.id.departionicon) ;
            Drawable bmDrawable = res.getDrawable(R.drawable.gongwenbao) ;
            bmDrawable.setBounds(0,0,120,120);//这里就可以控制drawable资源的大小了
            bmRB.setCompoundDrawables(null,bmDrawable,null,null);
            //员工管理
            RadioButton ygRB = (RadioButton)findViewById(R.id.stafficon) ;
            Drawable ygDrawable = res.getDrawable(R.drawable.caihong) ;
            ygDrawable.setBounds(0,0,120,120);//这里就可以控制drawable资源的大小了
            ygRB.setCompoundDrawables(null,ygDrawable,null,null);
            //资产管理
            RadioButton zcRB = (RadioButton)findViewById(R.id.moneyicon) ;
            Drawable zcDrawable = res.getDrawable(R.drawable.wenjian) ;
            zcDrawable.setBounds(0,0,120,120);//这里就可以控制drawable资源的大小了
            zcRB.setCompoundDrawables(null,zcDrawable,null,null);
            //更多期待
            RadioButton gdRB = (RadioButton)findViewById(R.id.moreicon) ;
            Drawable gdDrawable = res.getDrawable(R.drawable.more) ;
            gdDrawable.setBounds(0,0,100,100);//这里就可以控制drawable资源的大小了
            gdRB.setCompoundDrawables(null,gdDrawable,null,null);


        }
    }


}
