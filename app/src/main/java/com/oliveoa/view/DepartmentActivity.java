package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.oliveoa.pojo.DepartmentInfo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DepartmentActivity extends AppCompatActivity {

    private ArrayList<DepartmentInfo> departmentInfos;
    private ImageView back,add;
//    private LinearLayout check,depart_list;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDPlistView;
    private TextView tvname,tvid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        departmentInfos = getIntent().getParcelableArrayListExtra("ParcelableDepartment");
        System.out.println(departmentInfos);

        initView();
        saveDepartmentinfo();
    }

    public void initView(){
        back = (ImageView)findViewById(R.id.null_back);
        add = (ImageView)findViewById(R.id.null_add);
        //depart_list =(LinearLayout)findViewById(R.id.depart_list);
        addDPlistView = (LinearLayout)findViewById(R.id.depart_list);

        //默认添加一个Item
        addViewItem(null);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentActivity.this, CreateDepartmentActivity.class);
                startActivity(intent);
                finish();
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
            //删除操作
            final Button btn_remove = (Button) childAt.findViewById(R.id.btnDelete);
            btn_remove.setTag("remove");//设置删除标记
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    addDPlistView.removeView(childAt);
                }

            });
            //转到详情页面
            Button btn_info = (Button) childAt.findViewById(R.id.btnInfo);
            btn_info.setTag("edit");
            btn_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvname = (TextView)childAt.findViewById(R.id.dname);
                    int i ;
                    String dname = tvname.getText().toString().trim();
                    Log.i("dname=",dname);
                    for (i=0;i<departmentInfos.size();i++) {
                        if(dname.equals(departmentInfos.get(i).getName())){
                            break;
                        }

                    }
                    Intent intent = new Intent(DepartmentActivity.this, DepartmentInfoActivity.class);
                    intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfos);
                    intent.putExtra("index",i);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (departmentInfos == null) {//如果部门列表为0，加载空布局
            View hotelEvaluateView = View.inflate(this, R.layout.activity_null_department, null);
            addDPlistView.addView(hotelEvaluateView);
            //sortHotelViewItem();
        } else {//如果有部门则按数组大小加载布局
            for(int i = 0;i <departmentInfos.size(); i ++){
                View hotelEvaluateView = View.inflate(this, R.layout.activity_department_listitem, null);
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
            tvid = (TextView)childAt.findViewById(R.id.did);

            tvname.setText(departmentInfos.get(i).getName());
            tvid.setText("编号："+departmentInfos.get(i).getId());
        }
        Log.e(TAG, "部门名称：" + tvname.getText().toString() + "-----部门编号："
                + tvid.getText().toString());
    }

    /**
     *  数据存储到SharedPreferences文件中
     *
     */
    public void saveDepartmentinfo(){
        SharedPreferences.Editor editor = getSharedPreferences("department",MODE_PRIVATE).edit();
        for (int i = 0;i<departmentInfos.size();i++){
        editor.putString("dcid["+i+"]",departmentInfos.get(i).getDcid());
        editor.putString("dpid["+i+"]",departmentInfos.get(i).getDpid());
        editor.putString("id["+i+"]",departmentInfos.get(i).getId());
        editor.putString("name["+i+"]",departmentInfos.get(i).getName());
        editor.putString("telephone["+i+"]",departmentInfos.get(i).getTelephone());
        editor.putString("fax["+i+"]",departmentInfos.get(i).getFax());
        editor.apply();
        }
        Log.e(TAG, "" + departmentInfos.toString());
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
