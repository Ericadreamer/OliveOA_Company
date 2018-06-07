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
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.jsonbean.UpdateDepartmentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.oliveoa.util.Validator.isFixPhone;
import static com.oliveoa.util.Validator.isMobile;
import static com.oliveoa.util.Validator.isNumstr;

public class EditDutyInfoActivity extends AppCompatActivity {

    private EditText tname,tlimit;
    private ArrayList<DutyInfo> dutyInfo;
    private ArrayList<DepartmentInfo> departmentInfo;
    private String TAG = this.getClass().getSimpleName();
    private int dpindex,dtindex;
    private TextView tppid;
    private ImageView dutynext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_duty_info);

        departmentInfo = getIntent().getParcelableArrayListExtra("ParcelableDepartment");
        dutyInfo = getIntent().getParcelableArrayListExtra("ParcelableDuty");
        dpindex = getIntent().getIntExtra("dpindex",dpindex);
        dtindex = getIntent().getIntExtra("dtindex",dtindex);
        Log.e(TAG,"部门信息"+departmentInfo+"职务信息"+dutyInfo+"部门索引"+dpindex);
        initView();
    }

    //初始化
    public void initView() {
        SharedPreferences pref = getSharedPreferences("duty", MODE_PRIVATE);

        tname = (EditText) findViewById(R.id.edit_duty_name);
        tname.setText(pref.getString("name[" + dtindex + "]", ""));
        tlimit = (EditText) findViewById(R.id.edit_num);
        tlimit.setText(pref.getString("limit[" + dtindex + "]", ""));


        ImageView back = (ImageView)findViewById(R.id.null_back);
        ImageView save = (ImageView)findViewById(R.id.info_save);

        dutynext = (ImageView)findViewById(R.id.duty_select);
        tppid =(TextView)findViewById(R.id.tv_superior);
        tppid.setText(pref.getString("pname["+dtindex+"]",""));

        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditDutyInfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出编辑,直接返回部门信息页面？");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(EditDutyInfoActivity.this,DepartmentInfoActivity.class);
                        intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfo);
                        intent.putExtra("index",dpindex);
                        intent.putParcelableArrayListExtra("ParcelableDuty",dutyInfo);
                        Log.e(TAG,"部门信息"+departmentInfo+"职务信息"+dutyInfo+"部门索引"+dpindex);
                        //intent.putExtra("index",index);
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
        dutynext.setOnClickListener(new View.OnClickListener() {
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
        saveDutyinfo();
        Intent intent = new Intent(EditDutyInfoActivity.this, DutySelectActivity.class);
        intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfo);
        intent.putParcelableArrayListExtra("ParcelableDuty",dutyInfo);
        intent.putExtra("dtindex", dtindex);
        intent.putExtra("dpindex", dpindex);
        startActivity(intent);
        finish();
    }

    //保存编辑
    public void save() {
        if (isNumstr(tlimit.getText().toString().trim())) {
            SharedPreferences pref = getSharedPreferences("duty", MODE_PRIVATE);
            dutyInfo.get(dtindex).setName(tname.getText().toString().trim());
            dutyInfo.get(dtindex).setLimit(Integer.parseInt(tlimit.getText().toString().trim()));
            dutyInfo.get(dtindex).setPpid(pref.getString("ppid[" + dtindex + "]", ""));
            dutyInfo.get(dtindex).setPcid(pref.getString("pcid[" + dtindex + "]", ""));

            Log.e("dutyInfo111", dutyInfo.get(dtindex).toString());

            if (TextUtils.isEmpty(dutyInfo.get(dtindex).getName())) {
                Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
            } else if (dutyInfo.get(dtindex).getLimit() <= 0) {
                Toast.makeText(getApplicationContext(), "职务限制人数不得小于1人，请输入人数", Toast.LENGTH_SHORT).show();
            } else if (checkdutyname(dutyInfo.get(dtindex).getName())) {
                Toast.makeText(getApplicationContext(), "该职务名称已存在，请重新输入", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        DutyInfoService dutyInfoService = new DutyInfoService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = dutyInfoService.updatedutyinfo(s, dutyInfo.get(dtindex));
                        Log.d("update", statusAndMsgJsonBean.getMsg() + "");

                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "保存成功！点击返回键返回职务列表", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }

                    }
                }).start();
            }
        }else{
            Toast.makeText(getApplicationContext(), "请输入阿拉伯数字形式的限制人数", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     *  数据存储到SharedPreferences文件中
     *
     */
    public void saveDutyinfo(){
        SharedPreferences.Editor editor = getSharedPreferences("duty",MODE_PRIVATE).edit();
            editor.putString("name["+dtindex+"]",tname.getText().toString().trim());
            editor.putString("limit["+dtindex+"]",tlimit.getText().toString().trim());
            editor.apply();
           // Log.e(TAG, "updatinginfo" + dutyinfo.toString());
    }
    //判断重名
    public boolean checkdutyname(String name) {
        System.out.println(dutyInfo.size());
        for (int i=0;i<dutyInfo.size();i++) {
            if(i!=dtindex)
            if(name.equals(dutyInfo.get(i).getName())) {
                Log.e(TAG, "inputname"+name+"duty[]name"+dutyInfo.get(i).getName());
                return true;
            }
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
