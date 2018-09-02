package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.GoodInfoService;
import com.oliveoa.greendao.PropertiesInfoDao;
import com.oliveoa.jsonbean.GoodInfoJsonBean;
import com.oliveoa.pojo.Goods;
import com.oliveoa.pojo.PropertiesInfo;
import com.oliveoa.util.EntityManager;
import com.oliveoa.util.GoodsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GoodsActivity extends AppCompatActivity {

    private ImageView back,add;
    private List<Goods> goodsList;
    private GoodsAdapter adapter;
    private RecyclerView recyclerView;

    private View view;

    private String TAG = this.getClass().getSimpleName();

    private ArrayList<PropertiesInfo> propertiesInfos;


    //private Goods[] goods = {new Goods("打印机","打印公司文件")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        propertiesInfos = getIntent().getParcelableArrayListExtra("allpp");
        Log.i(TAG,"alldp="+propertiesInfos);
        LayoutInflater inflate = LayoutInflater.from(this);
        if(propertiesInfos.size()==0) {
            view = inflate.inflate(R.layout.activity_null_goods,null);
            setContentView(view);
            initView(view);
        }else{
            view = inflate.inflate(R.layout.activity_goods,null);
            setContentView(view);
            initView(view);
            initGoods();
            adapter=new GoodsAdapter(GoodsActivity.this,goodsList);
            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            //GridLayoutManager layoutManager = new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            //adapter = new GoodsAdapter(goodsList);
            recyclerView.setAdapter(adapter);
        }

    }

    private void initData(){

    }

    private void initGoods() {
        goodsList = new ArrayList<Goods>();
        for(int i =0;i<propertiesInfos.size();i++) {
            //goodsList.add(new Goods("佳能单反相机", "广告外拍", "那只喵借用未还"));
            goodsList.add(new Goods(propertiesInfos.get(i).getName(), propertiesInfos.get(i).getDescribe(),propertiesInfos.get(i).getGid()));
        }
    }

    public void initView(View view) {
        back =(ImageView)view.findViewById(R.id.back);
        add = (ImageView)view.findViewById(R.id.add);

        //点击事件
        back.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoodsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  add();
            }
        });
    }

    private void add() {
        PropertiesInfoDao propertiesInfoDao = EntityManager.getInstance().getPropertiesDao();
        PropertiesInfo pp = new PropertiesInfo();
        propertiesInfoDao.deleteAll();
        propertiesInfoDao.insert(pp);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GoodsActivity.this, AddGoodsActivity.class);
                intent.putExtra("index",0);
                //intent.putParcelableArrayListExtra("alldp", departmentInfos);
                intent.putExtra("dname", "");
                intent.putExtra("pname", "");
                startActivity(intent);
                finish();
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
