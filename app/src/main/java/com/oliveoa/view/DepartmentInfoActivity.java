package com.oliveoa.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DepartmentInfoActivity extends AppCompatActivity {

    private ArrayList<DepartmentInfo> departmentInfo;
    private ArrayList<DutyInfo> dutyInfo;
    private int index;
    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addDutylistView;
    private TextView tname,tnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_info);

        departmentInfo = getIntent().getParcelableArrayListExtra("ParcelableDepartment");
        dutyInfo = getIntent().getParcelableArrayListExtra("ParcelableDuty");
        index = getIntent().getIntExtra("index",index);//部门索引
//        Log.e("dutyInfos",dutyInfo.toString());
//        Log.e("departmentInfos",departmentInfo.toString());
//
//        Log.e("dpindex", String.valueOf(index));

        initView();
        saveDutyinfo();
      }

    //初始化
    public void initView() {
        ImageView back = (ImageView)findViewById(R.id.null_back);
        TextView edit = (TextView)findViewById(R.id.depart_edit);
       // TextView add = (TextView)findViewById(R.id.duty_add);
        TextView dutyadd = (TextView)findViewById(R.id.duty_add);
        addDutylistView = (LinearLayout)findViewById(R.id.duty_list);

      
        //默认添加一个Item
        addViewItem(null);

       //监听事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartmentInfoActivity.this, DepartmentActivity.class);
                intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfo);
                //intent.putParcelableArrayListExtra("ParcelableDuty",dutyInfo);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });

//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                add();
//            }
//        });

        dutyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dutyadd();
            }
        });

        SharedPreferences pref = getSharedPreferences("department",MODE_PRIVATE);
        TextView tid = (TextView) findViewById(R.id.text_num);
        tid.setText(pref.getString("id["+index+"]",""));
        TextView tname = (TextView) findViewById(R.id.text_name);
        tname.setText(pref.getString("name["+index+"]",""));
        TextView ttelephone = (TextView) findViewById(R.id.text_tel);
        ttelephone.setText(pref.getString("telephone["+index+"]",""));
        TextView tfax = (TextView) findViewById(R.id.text_fax);
        tfax.setText(pref.getString("fax["+index+"]",""));
        TextView tdpid = (TextView) findViewById(R.id.text_superior);
        tdpid.setText(pref.getString("dpname[" + index + "]", ""));
    }

    //编辑部门操作
    public void edit() {
        Intent intent = new Intent(DepartmentInfoActivity.this, RedactDepartmentActivity.class);
        intent.putExtra("ParcelableDepartment",departmentInfo);
        intent.putExtra("index",index);
        startActivity(intent);
        finish();

    }

    //添加职务操作
    public void dutyadd() {
        setAddDutyinfo(dutyInfo.size());
        Intent intent = new Intent(DepartmentInfoActivity.this, AddDutyActivity.class);
        intent.putParcelableArrayListExtra("ParcelableDuty",dutyInfo);
        intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfo);
        intent.putExtra("index",index);
        startActivity(intent);
        finish();

    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addDutylistView.getChildCount(); i++) {
            final View childAt = addDutylistView.getChildAt(i);
            //删除操作
            final Button btn_remove = (Button) childAt.findViewById(R.id.btnDelete);
            btn_remove.setTag("remove");//设置删除标记
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    tname = (TextView)childAt.findViewById(R.id.duty_name);
                    int i ; final int f;
                    String dutyname = tname.getText().toString().trim();
                    Log.i("dutyname=",dutyname);
                    for (i=0;i<dutyInfo.size();i++) {
                        if(dutyname.equals(dutyInfo.get(i).getName())){
                            break;
                        }
                    }
                    f=i;
                    final int[] h = {0};
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                            String s = pref.getString("sessionid", "");

                            DutyInfoService dutyInfoService = new DutyInfoService();
                            StatusAndMsgJsonBean statusAndMsgJsonBean = dutyInfoService.deleteduty(s,dutyInfo.get(f).getPcid());
                            Log.d("delete", statusAndMsgJsonBean.getMsg() + "");
                            if (statusAndMsgJsonBean.getStatus() == 0) {
                                dutyInfo.remove(dutyInfo.get(f));
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "删除成功！点击返回键返回部门列表", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                                addDutylistView.removeView(childAt);
                            } else {
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();
                }

            });
            //转到详情页面
            Button btn_info = (Button) childAt.findViewById(R.id.btnEdit);
            btn_info.setTag("btnEdit");
            btn_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tname = (TextView)childAt.findViewById(R.id.duty_name);
                    int i ;
                    String dutyname = tname.getText().toString().trim();

                    Log.i("dutyname=",dutyname);
                    for (i=0;i<dutyInfo.size();i++) {
                        if(dutyname.equals(dutyInfo.get(i).getName())){
                            break;
                        }

                    }
                    Intent intent = new Intent(DepartmentInfoActivity.this, DutyInfoActivity.class);
                    intent.putParcelableArrayListExtra("ParcelableDuty",dutyInfo);
                    intent.putParcelableArrayListExtra("ParcelableDepartment",departmentInfo);
                    intent.putExtra("duty_index",i);
                    intent.putExtra("department_index",index);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    //添加ViewItem
    private void addViewItem(View view) {
        if (dutyInfo != null) {///如果有部门则按数组大小加载布局
            for(int i = 0;i <dutyInfo.size(); i ++){
                View hotelEvaluateView = View.inflate(this, R.layout.activity_department_info_dutyitem, null);
                addDutylistView.addView(hotelEvaluateView);
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
        for (i = 0; i < addDutylistView.getChildCount(); i++) {
            View childAt = addDutylistView.getChildAt(i);
            tname= (TextView)childAt.findViewById(R.id.duty_name);
            tnum = (TextView)childAt.findViewById(R.id.duty_num);

            tname.setText(dutyInfo.get(i).getName());
            tnum.setText(String.valueOf(dutyInfo.get(i).getLimit()));
        }
        Log.e(TAG, "职务名称：" + tname.getText().toString() + "-----人数限制："
                + tnum.getText().toString());
    }
    /**
     *  新建职务数组空值存储到SharedPreferences文件中
     *
     */
    public void setAddDutyinfo(int v){
        SharedPreferences.Editor editor = getSharedPreferences("duty",MODE_PRIVATE).edit();
        editor.putString("dcid["+v+"]",departmentInfo.get(index).getDcid());
        editor.putString("dname["+v+"]",departmentInfo.get(index).getName());
        editor.putString("ppid["+v+"]","");
        editor.putString("pname["+v+"]","");
        editor.putString("name["+v+"]","");
        editor.putString("limit["+v+"]","");
        editor.apply();

    }


    /**
     *  职务所有数据存储到SharedPreferences文件中
     *
     */
    public void saveDutyinfo(){
        SharedPreferences.Editor editor = getSharedPreferences("duty",MODE_PRIVATE).edit();

        for (int i = 0;i<dutyInfo.size();i++){
            editor.putString("pcid["+i+"]",dutyInfo.get(i).getPcid());
            editor.putString("ppid["+i+"]",dutyInfo.get(i).getPpid());
            editor.putString("dcid["+i+"]",dutyInfo.get(i).getDcid());
            editor.putString("name["+i+"]",dutyInfo.get(i).getName());
            editor.putString("limit["+i+"]", String.valueOf(dutyInfo.get(i).getLimit()));
            editor.apply();
        }
        Log.e(TAG, "" + dutyInfo.toString());
        //存储所属部门以及管辖上级职务名称
        for (int i = 0;i<dutyInfo.size();i++){
           // System.out.println("Ppid["+i+"]"+dutyInfo.get(i).getPpid());
            for (int j = 0 ;j<dutyInfo.size();j++){
              //  System.out.println("Dcid["+j+"]"+dutyInfo.get(j).getDcid());
                if (!(dutyInfo.get(i).getPpid()==null)){
                    if (dutyInfo.get(i).getPpid().equals(dutyInfo.get(j).getPcid())) {
                        editor.putString("pname[" + i + "]", dutyInfo.get(j).getName());
                        editor.apply();
                      //  System.out.println("pname[" + i + "]" + dutyInfo.get(j).getName());
                    }
                } else {
                    editor.putString("pname["+i+"]","无");
                    editor.apply();
                }

               for (int n =0;n<dutyInfo.size();n++){
                    for (int m=0;m<departmentInfo.size();m++){
                        if (dutyInfo.get(n).getDcid().equals(departmentInfo.get(m).getDcid())){
                            editor.putString("dname["+n+"]",departmentInfo.get(m).getName());
                            editor.apply();
                        }
                    }
               }
            }
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
