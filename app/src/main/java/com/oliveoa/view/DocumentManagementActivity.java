package com.oliveoa.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.oliveoa.util.EntityManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DocumentManagementActivity extends AppCompatActivity {
    private RecyclerView mContentRv;
    private ImageView back;
    private TextView ttitle,tcontext,tvtip;
    private List<OfficialDocumentInfoJsonBean> list;
    private String TAG = this.getClass().getSimpleName();

    private RelativeLayout pacmanIndicator_layout;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private ScrollView scrollView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list = (List<OfficialDocumentInfoJsonBean>) msg.obj;
            switch (msg.what){
                case 1:
                    //在这里可以进行UI操作
                    if(list!=null&list.size()!=0){
                        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
                        mContentRv.setLayoutManager(new LinearLayoutManager(DocumentManagementActivity.this));
                        mContentRv.setAdapter(new DocumentManagementActivity.ContentAdapter(list));
                        avLoadingIndicatorView.hide();
                        pacmanIndicator_layout.setVisibility(View.GONE);
                    }else{
                        tvtip.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_management);

        initViews();

    }

    public void initViews() {
        ttitle = (TextView) findViewById(R.id.title);
        tcontext = (TextView) findViewById(R.id.content);
        tvtip = (TextView)findViewById(R.id.tvtip);
        scrollView = (ScrollView)findViewById(R.id.listview);

        pacmanIndicator_layout = (RelativeLayout) findViewById(R.id.pacmanIndicator_layout);
        pacmanIndicator_layout.setVisibility(View.VISIBLE);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avLoadingIndicatorView.show();
      /*  Timer timer=new Timer();
        TimerTask task=new TimerTask()
        {
            @Override
            public void run(){
               Message msg =new Message();
               msg.what = 2;
               handler.sendMessage(msg);
            }
        };
        timer.schedule(task,5000);//此处的Delay可以是3*1000，代表三秒*/
        initData();


        back =(ImageView)findViewById(R.id.info_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DocumentManagementActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{
        private List<OfficialDocumentInfoJsonBean> documents;
        public ContentAdapter(List<OfficialDocumentInfoJsonBean> documents) {
            this.documents = documents;
        }

        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(DocumentManagementActivity.this).inflate(R.layout.item_document, parent, false));
        }

        @Override
        public void onBindViewHolder(final ContentAdapter.ContentHolder holder, final int position) {
            holder.tvcontent.setText(documents.get(position).getOfficialDocument().getContent());
            holder.tvtitle.setText(documents.get(position).getOfficialDocument().getTitle());

            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"你点击了"+holder.itemContent.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, holder.tvtitle.getText().toString().trim() + "----" + documents.get(position).getOfficialDocument().toString());
                    scrollView.setVisibility(View.GONE);
                    pacmanIndicator_layout = (RelativeLayout) findViewById(R.id.pacmanIndicator_layout);
                    pacmanIndicator_layout.setVisibility(View.VISIBLE);
                    avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
                    avLoadingIndicatorView.setVisibility(View.VISIBLE);
                    avLoadingIndicatorView.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            info(position);
                        }
                    }).start();
                }
            });
        }
        private void info(int position) {
            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
            String s = pref.getString("sessionid", "");


            DepartmentInfoService departmentInfoService = new DepartmentInfoService();
            DepartmentInfoJsonBean departmentInfoJsonBean = departmentInfoService.departmentInfo(s);

            ArrayList<DepartmentInfo> departmentInfos = new ArrayList<>();
            ArrayList<EmployeeInfo> employeeInfos = new ArrayList<>();

            EmployeeInfoService employeeInfoService = new EmployeeInfoService();
            EmployeeInfoDao employeeInfoDao = EntityManager.getInstance().getEmployeeInfoDao();
            employeeInfoDao.deleteAll();
            DepartmentInfoDao departmentInfoDao = EntityManager.getInstance().getDepartmentInfo();
            departmentInfoDao.deleteAll();
            if (departmentInfoJsonBean.getStatus() == 0) {
                departmentInfos = departmentInfoJsonBean.getData();
                employeeInfoDao.deleteAll();
                for (int i = 0; i < departmentInfos.size(); i++) {
                    departmentInfoDao.insert(departmentInfos.get(i));
                    EmployeeInfoJsonBean employeeInfoJsonBean = employeeInfoService.employeeinfo(s, departmentInfos.get(i).getDcid());
                    if (employeeInfoJsonBean.getStatus() == 0) {
                        employeeInfos = employeeInfoJsonBean.getData();
                        for (int j = 0; j < employeeInfos.size(); j++) {
                            employeeInfoDao.insert(employeeInfos.get(j));
                        }
                    } else {
                        Looper.prepare();//解决子线程弹toast问题
                        Toast.makeText(getApplicationContext(), employeeInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();// 进入loop中的循7环，查看消息队列

                    }
                }
            } else {
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(getApplicationContext(), departmentInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循7环，查看消息队列

            }
            //Todo Service
            DocumentService service = new DocumentService();
            //Todo Service.Method
            StatusAndMsgAndDataHttpResponseObject<OfficialDocumentInfoJsonBean> statusAndDataHttpResponseObject = service.getdocumentInfo(s, documents.get(position).getOfficialDocument().getOdid());
            //ToCheck JsonBean.getStatus()
            if (statusAndDataHttpResponseObject.getStatus() == 0) {
                OfficialDocument officialDocument = statusAndDataHttpResponseObject.getData().getOfficialDocument();
                Intent intent = new Intent(DocumentManagementActivity.this, DocumentInfoActivity.class);
                intent.putExtra("info",officialDocument);
                intent.putParcelableArrayListExtra("issue",statusAndDataHttpResponseObject.getData().getOfficialDocumentIssueds());
                intent.putParcelableArrayListExtra("read",statusAndDataHttpResponseObject.getData().getOfficialDocumentCirculreads());
                startActivity(intent);
                finish();

            } else {
                Looper.prepare();//解决子线程弹toast问题
                Toast.makeText(DocumentManagementActivity.this, statusAndDataHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
            }
        }
        @Override
        public int getItemCount() {
            return documents.size();
        }

        class ContentHolder extends RecyclerView.ViewHolder{

            public TextView tvtitle,tvcontent;
            public CardView cardview;

            public ContentHolder(View v) {
                super(v);
                tvtitle = v.findViewById(R.id.title);
                tvcontent = v.findViewById(R.id.content);
                cardview = v.findViewById(R.id.card_view);
            }
        }

    }



    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                String s = pref.getString("sessionid","");
                ArrayList<OfficialDocumentInfoJsonBean> temp = new ArrayList<>();
                //Todo Service
                DocumentService service = new DocumentService();
                //Todo Service.Method
                StatusAndMsgAndDataHttpResponseObject<ArrayList<OfficialDocumentInfoJsonBean>> statusAndMsgAndDataHttpResponseObject = service.getalldocument(s);
                //ToCheck JsonBean.getStatus()
                if (statusAndMsgAndDataHttpResponseObject.getStatus() == 0) {
                    //Log.i("DOCUMENT=",statusAndMsgAndDataHttpResponseObject.getData().toString());
                    temp = statusAndMsgAndDataHttpResponseObject.getData();
                    Log.e(TAG,temp.toString());
                    //新建一个Message对象，存储需要发送的消息
                    Message message=new Message();
                    message.what=1;
                    message.obj=temp;
                    //然后将消息发送出去
                    handler.sendMessage(message);
                } else {
                    Looper.prepare();//解决子线程弹toast问题
                    Toast.makeText(DocumentManagementActivity.this,statusAndMsgAndDataHttpResponseObject.getMsg(), Toast.LENGTH_SHORT).show();
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
