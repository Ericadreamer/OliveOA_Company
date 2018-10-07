package com.oliveoa.view;

import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.EmployeeInfoDao;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentCirculread;
import com.oliveoa.pojo.OfficialDocumentIssued;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ReadInfoActivity extends AppCompatActivity {
    private TextView treadPerson, treadStatus, treadReport;
    private ImageView back;

    private OfficialDocument officialDocument;
    private ArrayList<OfficialDocumentIssued> list;
    private ArrayList<OfficialDocumentCirculread> officialDocumentCirculreads;

    private RecyclerView mContentRv;
    private EmployeeInfoDao employeeInfoDao;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_info);

        officialDocument = getIntent().getParcelableExtra("info");
        list = getIntent().getParcelableArrayListExtra("issue");
        officialDocumentCirculreads = getIntent().getParcelableArrayListExtra("read");
        Log.i(TAG,officialDocument.toString());
        Log.i(TAG,list.toString());
        Log.i(TAG,officialDocumentCirculreads.toString());



        initView();
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.info_back);

       /* treadPerson = (TextView) findViewById(R.id.read_person);
        treadStatus = (TextView) findViewById(R.id.read_status);
        treadReport = (TextView) findViewById(R.id.read_advise);*/
        initData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadInfoActivity.this,DocumentInfoActivity.class);
                intent.putExtra("info",officialDocument);
                intent.putParcelableArrayListExtra("issue",list);
                intent.putParcelableArrayListExtra("read",officialDocumentCirculreads);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ContentInfoAdapter extends RecyclerView.Adapter<ReadInfoActivity.ContentInfoAdapter.ContentHolder>{

        private ArrayList<OfficialDocumentCirculread> documents;
        private EmployeeInfo employeeInfo;

        public ContentInfoAdapter(ArrayList<OfficialDocumentCirculread> documents) {
            this.documents = documents;
        }
        @Override
        public ReadInfoActivity.ContentInfoAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ReadInfoActivity.ContentInfoAdapter.ContentHolder(LayoutInflater.from(ReadInfoActivity.this).inflate(R.layout.item_readinfo, parent, false));
        }

        @Override
        public void onBindViewHolder(final ReadInfoActivity.ContentInfoAdapter.ContentHolder holder, final int position) {
            employeeInfo = employeeInfoDao.queryBuilder().where(EmployeeInfoDao.Properties.Eid.eq(documents.get(position).getEid())).unique();
            if(employeeInfo!=null) {
                holder.tPerson.setText(employeeInfo.getName());
            }
            switch (documents.get(position).getIsread()){
                case -2:
                    holder.tStatus.setText("待办理");
                    holder.tStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                    break;
                case -1:
                    holder.tStatus.setText("不同意");
                    holder.tStatus.setTextColor(getResources().getColor(R.color.errorParimary));
                    break;
                case 1:
                    holder.tStatus.setText("同意");
                    holder.tStatus.setTextColor(getResources().getColor(R.color.passParimary));
                    break;
                case 0:
                    holder.tStatus.setText("正在办理");
                    holder.tStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                    break;
                default:
                    break;

            }
            holder.treport.setText(documents.get(position).getReport());
            holder.listitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ReadInfoActivity.this,"你点击了"+holder.tPerson.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, holder.tPerson.getText().toString().trim() + "----" + documents.get(position).toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            info(position);
                        }
                    }).start();
                }
            });

        }
        public void info(int position) {

        }
        @Override
        public int getItemCount() {
            return documents.size();
        }

        class ContentHolder extends RecyclerView.ViewHolder{

            public TextView tPerson,tStatus,treport;
            public LinearLayout listitem;

            public ContentHolder(View v) {
                super(v);
               tPerson = v.findViewById(R.id.read_person);
               tStatus = v.findViewById(R.id.read_status);
               treport = v.findViewById(R.id.read_advise);
               listitem = v.findViewById(R.id.item_document);
            }
        }

    }



    public void initData() {
        employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();

        if(officialDocumentCirculreads!=null&&officialDocumentCirculreads.size()!=0) {
            mContentRv = (RecyclerView) findViewById(R.id.rv_content);
            mContentRv.setLayoutManager(new LinearLayoutManager(ReadInfoActivity.this));
            mContentRv.setAdapter(new ReadInfoActivity.ContentInfoAdapter(officialDocumentCirculreads));
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
