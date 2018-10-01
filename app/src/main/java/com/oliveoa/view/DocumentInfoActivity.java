package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;

import java.util.Timer;
import java.util.TimerTask;

public class DocumentInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView tdraftPerson,ttitle, tcontent, tnuclearPerson, tnuclearStatus, tnuclearAdvise,
            tissuePerson, tissueStatus, tissueAdvise;
    private RecyclerView mContentRv;
    private Button btn_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_info);

        initView();
        initData();

    }

    public void initView() {
        back = (ImageView) findViewById(R.id.info_back);
        tdraftPerson = (TextView) findViewById(R.id.draft_person);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        tnuclearPerson = (TextView) findViewById(R.id.nuclear_person);
        tnuclearStatus = (TextView) findViewById(R.id.nuclear_status);
        tnuclearAdvise = (TextView) findViewById(R.id.nuclear_advise);
        tissuePerson = (TextView) findViewById(R.id.issue_person);
        tissueStatus = (TextView) findViewById(R.id.issue_status);
        tissueAdvise = (TextView) findViewById(R.id.issue_advise);

        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(new ContentInfoAdapter());

        btn_download = (Button) findViewById(R.id.download);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DocumentInfoActivity.this,DocumentManagementActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {  //点击返回键，返回主页
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DocumentInfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否下载附件");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        download();
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

    public void download() {

    }

    private class ContentInfoAdapter extends RecyclerView.Adapter<ContentInfoAdapter.ContentHolder>{

        @Override
        public ContentInfoAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(DocumentInfoActivity.this).inflate(R.layout.item_receive_department, parent, false));
        }

        @Override
        public void onBindViewHolder(ContentInfoAdapter.ContentHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 100;
        }

        class ContentHolder extends RecyclerView.ViewHolder{

            public TextView treceivePerson,treceiveStatus;
            public LinearLayout receiveDepartment;

            public ContentHolder(View v) {
                super(v);
                treceivePerson = v.findViewById(R.id.receive_person);
                treceiveStatus = v.findViewById(R.id.receive_status);
                receiveDepartment = v.findViewById(R.id.receive_department);
            }
        }

    }

    public void initData() {

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
