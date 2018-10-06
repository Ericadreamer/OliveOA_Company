package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.GoodInfoService;
import com.oliveoa.greendao.PropertiesInfoDao;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.GoodInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.PropertiesInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.LinesEditView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class AddGoodsActivity extends AppCompatActivity {

    private ImageView back,save,next;
    private EditText tname;
    private EditText tquantity;
    private TextView tpname;
    private LinesEditView linesEditView;
    private View textAreaView;
    private EditText einput;
    private String TAG = this.getClass().getSimpleName();
    private PropertiesInfo propertiesInfo;
    private PropertiesInfoDao propertiesInfoDao;
    private String dname;
    private String pname;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        index = getIntent().getIntExtra("index",index);//详情为0，选择为1
        dname = getIntent().getStringExtra("dname");
        pname = getIntent().getStringExtra("pname");

        Log.d(TAG,"index="+index+"dname="+dname+"pname="+pname);
       initView();
    }

    public void initView() {

        linesEditView = new LinesEditView(AddGoodsActivity.this);
        textAreaView =(LinesEditView)findViewById(R.id.description);
        einput = textAreaView.findViewById(R.id.id_et_input);
        if(  einput==null){
            System.out.println("  einput==null");
        }
        back = (ImageView)findViewById(R.id.back);
        save = (ImageView)findViewById(R.id.save);
        next = (ImageView)findViewById(R.id.next);

        tname = (EditText)findViewById(R.id.goods_name);
        if(tname==null){
            System.out.println("tname==null");
        }
        tquantity = (EditText)findViewById(R.id.quantity);
        if( tquantity==null){
            System.out.println(" tquantity==null");
        }
        tpname = (TextView)findViewById(R.id.show);

        initData();

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                back();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {   //点击保存键，提示保存是否成功
            @Override
            public void onClick(View view) {
                save();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDpDtPicker(view);
                Log.e(TAG,"click1111");
            }
        });

        tpname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDpDtPicker(view);
                Log.e(TAG,"click");
            }
        });


    }

    private void back() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

        Handler mHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");

                GoodInfoService goodInfoService = new GoodInfoService();
                GoodInfoJsonBean goodInfoJsonBean = goodInfoService.properties(s);
                if(goodInfoJsonBean.getStatus()==0){
                    ArrayList<PropertiesInfo> propertiesInfos = goodInfoJsonBean.getData();

                    Intent intent = new Intent(AddGoodsActivity.this,GoodsActivity.class);
                    intent.putExtra("allpp",propertiesInfos);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),goodInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                }


                Log.d(TAG,"uiThread2------"+Thread.currentThread());//子线程
            }
        };

        Log.d(TAG,"uiThread1------"+Thread.currentThread());//主线程
        mHandler.sendEmptyMessage(1);
    }

    private void initData() {
        propertiesInfoDao = EntityManager.getInstance().getPropertiesDao();
        propertiesInfo = propertiesInfoDao.queryBuilder().unique();
        Log.e(TAG,"index=1-------goodinfo"+propertiesInfo.toString());
        if(index==1){
            tname.setText(propertiesInfo.getName());
            tquantity.setText(propertiesInfo.getTotal());
            tpname.setText(dname+":"+pname);
//            linesEditView.setContentText(propertiesInfo.getDescribe());
            einput.setText(propertiesInfo.getDescribe());
        }
    }

    public void save() {
        if(TextUtils.isEmpty(tname.getText().toString().trim())&&TextUtils.isEmpty(einput.getText().toString().trim())
                ||TextUtils.isEmpty(tquantity.getText().toString().trim())||tpname.getText().toString().trim().equals(":")||tpname.getText().toString().trim().equals("无:无")){
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        }else {
            propertiesInfo.setName(tname.getText().toString().trim());
            propertiesInfo.setDescribe(einput.getText().toString().trim());
            propertiesInfo.setTotal(tquantity.getText().toString().trim());

            if (TextUtils.isEmpty(propertiesInfo.getPcid())) {
                Toast.makeText(getApplicationContext(), "请选择管理岗位！", Toast.LENGTH_SHORT).show();
            } else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //读取SharePreferences的cookies
                        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                        String s = pref.getString("sessionid", "");

                        GoodInfoService goodInfoService = new GoodInfoService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean = goodInfoService.addproperty(s,propertiesInfo);
                        if (statusAndMsgJsonBean.getStatus() == 0) {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "添加成功！点击返回键返回主页", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        }
    }

    //部门及职务选择
    public void onDpDtPicker(View view){

        String contenttext = einput.getText().toString().trim();
        Log.e(TAG,"lineEditView=="+contenttext);
        /**
         *   存储表中所有数据
         */
        PropertiesInfoDao propertiesInfoDao = EntityManager.getInstance().getPropertiesDao();

        PropertiesInfo pp = new PropertiesInfo();

        pp.setName(tname.getText().toString().trim());
        pp.setDescribe(contenttext);
        pp.setTotal(tquantity.getText().toString().trim());
       /* pp.setRemaining(propertiesInfo.getRemaining());
        pp.setPcid(propertiesInfo.getPcid());
        pp.setGid(propertiesInfo.getGid());*/
        propertiesInfoDao.deleteAll();
        propertiesInfoDao.insert(pp);
        Log.e(TAG,"pp==="+pp.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");
                DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);
                if (departmentInfoJsonBean.getStatus() == 0) {
                    ArrayList<DepartmentInfo> departmentInfos= departmentInfoJsonBean.getData();
                    Intent intent = new Intent(AddGoodsActivity.this, DepartmentSelectActivity.class);
                    intent.putExtra("index", 5);
                    intent.putParcelableArrayListExtra("alldp", departmentInfos);
                    startActivity(intent);
                    finish();
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(getApplicationContext(),departmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }
            }
        }).start();
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
