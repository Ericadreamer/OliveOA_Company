package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.jsonbean.DutyInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.util.EntityManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AddDutyActivity extends AppCompatActivity {

    private List<DutyInfo> dutyInfo;

    private ImageView back,save,dtselect;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private EditText etname,etnum;
    private TextView tppid;
    private String dpname;
    private int index;
    private DutyInfo dt;
    private DutyInfoDao dutyInfoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_duty);
        index = getIntent().getIntExtra("index",index);//详情0，职务选择返回1
        initData();
    }
    private void initData(){
        dutyInfoDao = EntityManager.getInstance().dutyInfoDao;
        dutyInfo = dutyInfoDao.queryBuilder().orderAsc(DutyInfoDao.Properties.Orderby).list();
        //如果是从职务选择页面返回
        if(index==1){
            Log.e("AddDutySize", String.valueOf(dutyInfo.size()));
            dt = dutyInfo.get(dutyInfo.size()-1);
            DutyInfo temp = dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Pcid.eq(dt.getPpid())).unique();
            if(temp==null){
                dpname = "无";
            }else{
                dpname = temp.getName();
            }

        }
        initView();
    }

    private void initView() {
        back = (ImageView)findViewById(R.id.null_back);
        save = (ImageView)findViewById(R.id.info_save);
        dtselect = (ImageView)findViewById(R.id.duty_select);

        etname = (EditText)findViewById(R.id.edit_duty_name);
        etnum =(EditText)findViewById(R.id.edit_limit);
        tppid = (TextView) findViewById(R.id.edit_superior);

        if(index==1) {
            etname.setText(dt.getName());
            etnum.setText(dt.getLimit());
            tppid.setText(dpname);
        }


        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddDutyActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出创建,直接返回职务列表页面？");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AddDutyActivity.this, DepartmentInfoActivity.class);
                        startActivity(intent);
                        finish();
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
        save.setOnClickListener(new View.OnClickListener() {   //点击保存键，提示保存是否成功
            @Override
            public void onClick(View view) {
                save();
            }
        });

        tppid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dutyInfo.size()>0)
                dutySelect();
                else{
                    Toast.makeText(getApplicationContext(), "当前无更多职务，无法选择，请创建新职务！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dtselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dutyInfo.size()>0)
                dutySelect();
                else{
                    Toast.makeText(getApplicationContext(), "当前无更多职务，无法选择，请创建新职务！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void dutySelect(){
        Intent intent = new Intent(AddDutyActivity.this, DutySelectActivity.class);
        intent.putExtra("dutyname", dt.getName());
        intent.putExtra("index",1 );
        startActivity(intent);
        finish();
    }

    //保存编辑
    public void save() {
        SharedPreferences pref = getSharedPreferences("duty", MODE_PRIVATE);

        dt.setName(etname.getText().toString().trim());

        if(etnum.getText().toString().trim().equals("")){
           dt.setLimit(0);
        }else{
            dt.setLimit(Integer.parseInt(etnum.getText().toString().trim()));
        }


        if (TextUtils.isEmpty(dt.getName())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else if(dt.getLimit()<=0){
            Toast.makeText(getApplicationContext(), "职务限制人数不得小于1人，请输入人数", Toast.LENGTH_SHORT).show();
        }else if(checkdutyname(dt.getName())){
            Toast.makeText(getApplicationContext(), "该职务名称已存在，请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    DutyInfoService dutyInfoService = new DutyInfoService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = dutyInfoService.addduty(s, dt);
                        Log.d("add", statusAndMsgJsonBean.getMsg() + "");
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            DutyInfoJsonBean dutyInfoJsonBean = dutyInfoService.dutyInfo(s, dt.getDcid());
                            DutyInfoDao dutyInfoDao = EntityManager.getInstance().dutyInfoDao;
                            List<DutyInfo> duty;
                            if(dutyInfoJsonBean.getStatus()==0) {
                                duty = dutyInfoJsonBean.getData();
                                Log.e("duty_add", duty.toString());

                                dutyInfoDao.deleteAll();
                                for (int i =0;i<duty.size();i++){
                                    dutyInfoDao.insert(duty.get(i));
                                }

                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "创建成功！点击返回键返回部门列表", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            } else{
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "获取职务信息失败！", Toast.LENGTH_SHORT).show();
                                Looper.prepare();
                                }
                        }
                }
            }).start();
        }
    }
    //判断重名
   public boolean checkdutyname(String name) {
            DutyInfo dutyInfo =dutyInfoDao.queryBuilder().where(DutyInfoDao.Properties.Name.eq(name)).unique();
            if(dutyInfo!=null) {
                Log.e(TAG, "inputname"+name+"duty[]name"+dutyInfo.getName());
                return true;
            }

       return false;
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
