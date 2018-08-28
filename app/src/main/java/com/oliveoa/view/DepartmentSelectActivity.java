package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DepartmentSelectActivity extends AppCompatActivity {

    private List<DepartmentInfo> departmentInfo;
    private ImageView back;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDPlistView;
    private int index;
    private TextView tvname;
    private String dname;
    private DepartmentInfoDao departmentInfoDao;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_select);

        dname = getIntent().getStringExtra("dname");//被编辑的部门名
        index = getIntent().getIntExtra("index",index);//判断是编辑页面0还是添加页面1

        initData();
    }

    public void initData(){
        departmentInfoDao = EntityManager.getInstance().departmentInfoDao;
        if(dname=="") {
            departmentInfo = departmentInfoDao.queryBuilder().orderAsc(DepartmentInfoDao.Properties.Orderby).list();
        }else{
            departmentInfo = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Name.notEq(dname)).list();

        }
       initview();
    }

    public void initview(){
        back =(ImageView)findViewById(R.id.info_back);
        addDPlistView = (LinearLayout)findViewById(R.id.depart_list);

       // 默认添加一个Item
        addViewItem(null);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DepartmentSelectActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出选择,直接返回部门编辑页面？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(index==0) {
                            Intent intent = new Intent(DepartmentSelectActivity.this, RedactDepartmentActivity.class);
                            intent.putExtra("id",id );//dcid
                            intent.putExtra("index", 0);
                            startActivity(intent);
                            finish();
                        }
                        if(index==1){
                            Intent intent = new Intent(DepartmentSelectActivity.this, CreateDepartmentActivity.class);
                            intent.putExtra("index", 1);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addDPlistView.getChildCount(); i++) {
            final View childAt = addDPlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout)childAt.findViewById(R.id.depart_item);

            final TextView tname = (TextView)childAt.findViewById(R.id.dname);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                         Log.e(TAG,tname.getText().toString());
                        if(index==0)  {  //编辑部门选择
                            DepartmentInfo dp =departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Name.eq(dname)).unique();
                            DepartmentInfo temp  = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(tname.getText().toString())).unique();
                            Log.e(TAG,temp.toString());
                            if(temp!=null) {
                                dp.setDpid(temp.getDcid());
                            }
                            departmentInfoDao.update(dp);

                            Intent intent = new Intent(DepartmentSelectActivity.this, RedactDepartmentActivity.class);
                            intent.putExtra("id",id );//dcid
                            intent.putExtra("index", 0);
                            startActivity(intent);
                            finish();
                        }
                        if(index==1){ //创建部门选择
                            DepartmentInfo dp =departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Name.eq(dname)).unique();
                            DepartmentInfo temp  = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(tname.getText().toString())).unique();

                            if(temp!=null) {
                                Log.e(TAG,temp.toString()+dp.toString());
                                dp.setDpid(temp.getDcid());
                                departmentInfoDao.update(dp);
                            }


                            Intent intent = new Intent(DepartmentSelectActivity.this, CreateDepartmentActivity.class);
                            intent.putExtra("index", 1);
                            startActivity(intent);
                            finish();
                        }
                    }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (departmentInfo == null) {//如果部门列表为0，加载空布局
            View hotelEvaluateView = View.inflate(this, R.layout.activity_null_department, null);
            addDPlistView.addView(hotelEvaluateView);
            //sortHotelViewItem();
        } else {//如果有部门则按数组大小加载布局
            for(int i = 0;i <departmentInfo.size(); i ++){
                View hotelEvaluateView = View.inflate(this, R.layout.activity_department_selectitem, null);
                addDPlistView.addView(hotelEvaluateView);
                InitDataViewItem();

            }
            sortHotelViewItem();

        }
    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {
        int i;
        for (i = 0; i < addDPlistView.getChildCount(); i++) {
            View childAt = addDPlistView.getChildAt(i);
            tvname = (TextView)childAt.findViewById(R.id.dname);

            tvname.setText(departmentInfo.get(i).getName());
        }
        Log.e(TAG, "部门名称：" + tvname.getText().toString());
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
