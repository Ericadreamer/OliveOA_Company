package com.oliveoa.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.EntityIterator;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.common.Const;
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
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
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
    private Context mContext;

    DownloadService.DownloadBinder downloadBinder;

    //private String url ="http://1.199.93.153/imtt.dd.qq.com/16891/5FE88135737E977CCCE1A4DAC9FAFFCB.apk";

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
   // private RelativeLayout pacmanIndicator_layout;
   // private AVLoadingIndicatorView avLoadingIndicatorView;

    private String fileName;
    private String path;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                fileName = (String)msg.obj;
                Log.i(TAG,fileName+"==="+path);
                downloadBinder.startDownload(
                        Environment.getExternalStorageDirectory() + "/OliveOA_Company/OfficialsDocument/",
                        fileName,
                        path,
                        (int) System.currentTimeMillis());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_info);

        mContext = this;
        officialDocument = getIntent().getParcelableExtra("info");
        list = getIntent().getParcelableArrayListExtra("issue");
        officialDocumentCirculreads = getIntent().getParcelableArrayListExtra("read");
        Log.i(TAG,officialDocument.toString());
        Log.i(TAG,list.toString());
        Log.i(TAG,officialDocumentCirculreads.toString());

        path = Const.DOCUMENTFLOW_DOWNLOAD + officialDocument.getOdid();
        //path = "http://1.199.93.153/imtt.dd.qq.com/16891/5FE88135737E977CCCE1A4DAC9FAFFCB.apk";
        Intent intent = new Intent(mContext, DownloadService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        initView();

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
      /*  pacmanIndicator_layout = (RelativeLayout) findViewById(R.id.pacmanIndicator_layout);
        pacmanIndicator_layout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avLoadingIndicatorView.show();*/
        initData();
       // avLoadingIndicatorView.hide();


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

    private void download() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL murl = null;
                String fileName ="";
                try {
                    //获取文件名
                    murl = new URL(path);
                    URLConnection con = murl.openConnection();
                    fileName = con.getHeaderField("Content-Disposition");
                    fileName = new String(fileName.getBytes("ISO-8859-1"), "GBK");
                    fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("filename=") + 9), "UTF-8");
                    Log.i(getClass().getSimpleName(), "文件名为：" + fileName);
                    Log.i(getPackageName(),"size="+con.getHeaderField("Content-Length"));
                    Log.i(getPackageName(),"size="+con.getContentLength());

                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    msg.obj = fileName;
                    handler.sendMessage(msg);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
            intent.putExtra("info",officialDocument);
            intent.putParcelableArrayListExtra("issue",list);
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
