package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DutySelectActivity extends AppCompatActivity {


    private List<DutyInfo> dutyInfos;
    private ImageView back;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDTlistView;
    private String dtname;
    private TextView tvname;
    private DutyInfoDao dutyInfoDao;
    private int index;
    private DutyInfo temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duty_select);

        dutyInfos= getIntent().getParcelableArrayListExtra("alldt");
        index = getIntent().getIntExtra("index",index);//判断是编辑页面0还是添加页面1
        Log.d(TAG,"index="+index+"dutyInfos="+dutyInfos.toString());
        initData();
    }

    public void initData(){
        dutyInfoDao = EntityManager.getInstance().dutyInfoDao;
        temp = dutyInfoDao.queryBuilder().unique();
        Log.d(TAG,"temp="+temp.toString());
        initview();
    }

    public void initview(){
        back =(ImageView)findViewById(R.id.null_back);
        addDTlistView = (LinearLayout)findViewById(R.id.duty_list);

        // 默认添加一个Item
        addViewItem(null);

        //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DutySelectActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出选择,直接返回职务编辑页面？");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(index==0) {
                            Intent intent = new Intent(DutySelectActivity.this,EditDutyInfoActivity.class);
                            intent.putExtra("dtname","无" );//上级部门名
                            intent.putExtra("index", 0);
                            intent.putExtra("dt",temp);
                            startActivity(intent);
                            finish();
                        }
                        if(index==1){
                            Intent intent = new Intent(DutySelectActivity.this, AddDutyActivity.class);
                            intent.putExtra("index", 1);
                            intent.putExtra("dtname","无");
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
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
        for (int i = 0; i < addDTlistView.getChildCount(); i++) {
            final View childAt = addDTlistView.getChildAt(i);
            final LinearLayout item = (LinearLayout)childAt.findViewById(R.id.select_item);

            final TextView tname = (TextView)childAt.findViewById(R.id.dname);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(index==0) {  //编辑职务选择
                        if(temp!=null) {
                            Log.e(TAG,temp.toString());
                            if(tname.getText().toString().equals(temp.getName())) {
                                Toast.makeText(getApplicationContext(), "请选择除"+tname.getText().toString()+"以外的其他职务", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            for (int i = 0; i < dutyInfos.size(); i++) {
                                Log.e(TAG, dutyInfos.get(i).getName() + tname.getText().toString());
                                if (tname.getText().toString().equals(dutyInfos.get(i).getName())) {
                                    temp.setPpid(dutyInfos.get(i).getPcid());
                                    Log.e(TAG, dutyInfos.get(i).getPcid());
                                    break;
                                }
                            }
                            dutyInfoDao.deleteAll();
                            dutyInfoDao.insert(temp);
                            Log.e(TAG, dutyInfoDao.queryBuilder().unique().toString());
                        }
                        Intent intent = new Intent(DutySelectActivity.this, EditDutyInfoActivity.class);
                        intent.putExtra("index", 1);
                        intent.putExtra("dp", temp);
                        intent.putExtra("dpname", tname.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                    if(index==1) { //创建部门选择
                        if (temp != null) {
                            Log.e(TAG, temp.toString());

                            for (int i = 0; i < dutyInfos.size(); i++) {
                                Log.e(TAG, dutyInfos.get(i).getName() +","+ tname.getText().toString());
                                if (tname.getText().toString().equals(dutyInfos.get(i).getName())) {
                                    temp.setPpid(dutyInfos.get(i).getPcid());
                                    Log.e(TAG, dutyInfos.get(i).getPcid());
                                    break;
                                }
                            }
                            dutyInfoDao.deleteAll();
                            dutyInfoDao.insert(temp);
                            Log.e(TAG, dutyInfoDao.queryBuilder().unique().toString());
                        }
                        Intent intent = new Intent(DutySelectActivity.this, AddDutyActivity.class);
                        intent.putExtra("index", 1);
                        intent.putExtra("dtname", tname.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
       }

    //添加ViewItem
    private void addViewItem(View view) {
        if (dutyInfos!=null) {//如果有职务则按数组大小加载布局
            for(int i = 0;i <dutyInfos.size(); i ++){
                View hotelEvaluateView = View.inflate(this, R.layout.activity_duty_selectitem, null);
                addDTlistView.addView(hotelEvaluateView);
                InitDataViewItem();

            }
            sortHotelViewItem();
        }else {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Item加载数据
     */
    private void InitDataViewItem() {
        int i;
        for (i = 0; i < addDTlistView.getChildCount(); i++) {
            View childAt = addDTlistView.getChildAt(i);
            tvname = (TextView)childAt.findViewById(R.id.dname);

            tvname.setText(dutyInfos.get(i).getName());
        }
        Log.e(TAG, "职务名称：" + tvname.getText().toString());
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
