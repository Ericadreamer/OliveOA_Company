package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.EntityIterator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.oliveoa.common.StatusAndMsgAndDataHttpResponseObject;
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.DocumentService;
import com.oliveoa.controller.EmployeeInfoService;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.EmployeeInfoDao;
import com.oliveoa.jsonbean.DepartmentInfoJsonBean;
import com.oliveoa.jsonbean.EmployeeInfoJsonBean;
import com.oliveoa.jsonbean.OfficialDocumentInfoJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.EmployeeInfo;
import com.oliveoa.pojo.OfficialDocument;
import com.oliveoa.pojo.OfficialDocumentCirculread;
import com.oliveoa.pojo.OfficialDocumentIssued;
import com.oliveoa.util.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DocumentInfoActivity extends AppCompatActivity {
    private ImageView back;
    private TextView tdraftPerson,ttitle, tcontent, tnuclearPerson, tnuclearStatus, tnuclearAdvise,
            tissuePerson, tissueStatus, tissueAdvise;
    private RecyclerView mContentRv;
    private Button btn_download;

    private OfficialDocument officialDocument;
    private ArrayList<OfficialDocumentIssued> list;
    private ArrayList<OfficialDocumentCirculread> officialDocumentCirculreads;

    private DepartmentInfoDao departmentInfoDao;
    private EmployeeInfoDao employeeInfoDao;

    private String TAG = this.getClass().getSimpleName();
   /* private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list = (ArrayList<OfficialDocumentIssued>) msg.obj;
            switch (msg.what){
                case 1:
                    //在这里可以进行UI操作
                    if(list!=null&list.size()!=0){
                        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
                        mContentRv.setLayoutManager(new LinearLayoutManager(DocumentInfoActivity.this));
                        mContentRv.setAdapter(new DocumentInfoActivity.ContentInfoAdapter(list));
                    }
                    break;
                default:
                    break;
            }
        }

    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_info);

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
        tdraftPerson = (TextView) findViewById(R.id.draft_person);
        ttitle = (TextView) findViewById(R.id.title);
        tcontent = (TextView) findViewById(R.id.content);
        tnuclearPerson = (TextView) findViewById(R.id.nuclear_person);
        tnuclearStatus = (TextView) findViewById(R.id.nuclear_status);
        tnuclearAdvise = (TextView) findViewById(R.id.nuclear_advise);
        tissuePerson = (TextView) findViewById(R.id.issue_person);
        tissueStatus = (TextView) findViewById(R.id.issue_status);
        tissueAdvise = (TextView) findViewById(R.id.issue_advise);

        btn_download = (Button) findViewById(R.id.download);

        initData();


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

        private ArrayList<OfficialDocumentIssued> documentIssueds;
        private DepartmentInfo departmentInfo;

        public ContentInfoAdapter(ArrayList<OfficialDocumentIssued> documentIssueds) {
            this.documentIssueds = documentIssueds;
        }
        @Override
        public ContentInfoAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(DocumentInfoActivity.this).inflate(R.layout.item_receive_department, parent, false));
        }

        @Override
        public void onBindViewHolder(final ContentInfoAdapter.ContentHolder holder, final int position) {
            departmentInfo = departmentInfoDao.queryBuilder().where(DepartmentInfoDao.Properties.Dcid.eq(documentIssueds.get(position).getDcid())).unique();
            if(departmentInfo!=null) {
                holder.treceivePerson.setText(departmentInfo.getName());
            }
            switch (documentIssueds.get(position).getIsreceive()){
                case -2:
                    holder.treceiveStatus.setText("待签收");
                    holder.treceiveStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                    break;
                case -1:
                    holder.treceiveStatus.setText("不同意");
                    holder.treceiveStatus.setTextColor(getResources().getColor(R.color.errorParimary));
                    break;
                case 1:
                    holder.treceiveStatus.setText("同意");
                    holder.treceiveStatus.setTextColor(getResources().getColor(R.color.passParimary));
                    break;
                case 0:
                    holder.treceiveStatus.setText("正在签收");
                    holder.treceiveStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                    break;
                default:
                    break;

            }
            holder.receiveDepartment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"你点击了"+holder.itemContent.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, holder.treceivePerson.getText().toString().trim() + "----" + documentIssueds.get(position).toString());
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
            Intent intent = new Intent(DocumentInfoActivity.this, ReadInfoActivity.class);
            intent.putParcelableArrayListExtra("read",officialDocumentCirculreads);
            startActivity(intent);
            finish();
        }
        @Override
        public int getItemCount() {
            return documentIssueds.size();
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
        departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
        employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();

        DepartmentInfo departmentInfo = new DepartmentInfo();
        EmployeeInfo employeeInfo = new EmployeeInfo();

        employeeInfo = employeeInfoDao.queryBuilder().where(EmployeeInfoDao.Properties.Eid.eq(officialDocument.getDraftEid())).unique();
        if(employeeInfo!=null) {
            tdraftPerson.setText(employeeInfo.getName());
        }else{
            tdraftPerson.setText("");
        }

        employeeInfo = employeeInfoDao.queryBuilder().where(EmployeeInfoDao.Properties.Eid.eq(officialDocument.getNuclearDraftEid())).unique();
        if(employeeInfo!=null) {
            tnuclearPerson.setText(employeeInfo.getName());
        }else{
            tnuclearPerson.setText("");
        }

        employeeInfo = employeeInfoDao.queryBuilder().where(EmployeeInfoDao.Properties.Eid.eq(officialDocument.getIssuedEid())).unique();
        if(employeeInfo!=null) {
            tissuePerson.setText(employeeInfo.getName());
        }else{
            tissuePerson.setText("");
        }

        ttitle.setText(officialDocument.getTitle());
        tcontent.setText(officialDocument.getContent());
        tnuclearAdvise.setText(officialDocument.getNuclearDraftOpinion());
        tissueAdvise.setText(officialDocument.getIssuedOpinion());

        switch (officialDocument.getNuclearDraftIsapproved()){
            case -2:
                tnuclearStatus.setText("待核稿");
                tnuclearStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            case -1:
                tnuclearStatus.setText("不同意");
                tnuclearStatus.setTextColor(getResources().getColor(R.color.errorParimary));
                break;
            case 1:
                tnuclearStatus.setText("同意");
                tnuclearStatus.setTextColor(getResources().getColor(R.color.passParimary));
                break;
            case 0:
                tnuclearStatus.setText("正在核稿");
                tnuclearStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            default:
                break;

        }

        switch (officialDocument.getIssuedIsapproved()){
            case -2:
                tissueStatus.setText("待签发");
                tissueStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            case -1:
                tissueStatus.setText("不同意");
                tissueStatus.setTextColor(getResources().getColor(R.color.errorParimary));
                break;
            case 1:
                tissueStatus.setText("同意");
                tissueStatus.setTextColor(getResources().getColor(R.color.passParimary));
                break;
            case 0:
                tissueStatus.setText("正在签发");
                tissueStatus.setTextColor(getResources().getColor(R.color.tv_gray_deep));
                break;
            default:
                break;

        }

        if(list!=null&&list.size()!=0) {
            mContentRv = (RecyclerView) findViewById(R.id.rv_content);
            mContentRv.setLayoutManager(new LinearLayoutManager(DocumentInfoActivity.this));
            mContentRv.setAdapter(new DocumentInfoActivity.ContentInfoAdapter(list));
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
