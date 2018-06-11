package com.oliveoa.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.EmployeeInfoService;
import com.oliveoa.daoimpl.DepartmentDAOImpl;
import com.oliveoa.daoimpl.DutyDAOImpl;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.DutyInfo;
import com.oliveoa.pojo.EmployeeInfo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

import static com.oliveoa.util.Validator.isEmail;
import static com.oliveoa.util.Validator.isMobile;

public class AddEmployeeinfoActivity extends AppCompatActivity {

    private ArrayList<EmployeeInfo> employeeInfos;
    private ArrayList<DepartmentInfo> departmentInfos;
    private ArrayList<DutyInfo> dutyInfos;

    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private EmployeeInfo employeeInfo;
    private String tmpdname;
    private String tmppname;

//    DepartmentDAOImpl departmentDAO;
//    DutyDAOImpl dutyDAO;
//    EmployeeDAOImpl employeeDAO;

    private TextView dpcid, sex, birth;
    private EditText ename, eid, etel, eemail, eaddress;
    private ImageView back, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employeeinfo);

//        employeeInfo = getIntent().getParcelableExtra("employee");
//        Log.d("ParcelableEmployee", employeeInfo.toString());

        getOptionData();
        initview();
    }

    public void initview() {
        back = (ImageView) findViewById(R.id.info_back);
        save = (ImageView) findViewById(R.id.info_save);

        dpcid = (TextView) findViewById(R.id.content_dpcid);
        sex = (TextView) findViewById(R.id.content_sex);
        birth = (TextView) findViewById(R.id.content_birth);

        ename = (EditText) findViewById(R.id.content_name);
        eid = (EditText) findViewById(R.id.content_id);
        etel = (EditText) findViewById(R.id.content_tel);
        eemail = (EditText) findViewById(R.id.content_email);
        eaddress = (EditText) findViewById(R.id.content_address);

        initData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddEmployeeinfoActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否确定退出创建,直接返回部门列表页面？");
                dialog.setCancelable(false);
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(AddEmployeeinfoActivity.this, EmployeelistActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                //Toast.makeText(mContext, "你点击了返回", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });


    }

    public void initData() {
    }

    public void save() {

        employeeInfo.setName(ename.getText().toString().trim());
        employeeInfo.setId(eid.getText().toString().trim());
        employeeInfo.setSex(sex.getText().toString().trim());
        employeeInfo.setTel(etel.getText().toString().trim());
        employeeInfo.setBirth(birth.getText().toString().trim());
        employeeInfo.setEmail(eemail.getText().toString().trim());
        employeeInfo.setAddress(eaddress.getText().toString().trim());

        for (int i = 0; i < departmentInfos.size(); i++) {
            if (tmpdname.equals(departmentInfos.get(i).getName())) {
                employeeInfo.setDcid(departmentInfos.get(i).getDcid());
            }
        }

        DutyDAOImpl dutyDAO = new DutyDAOImpl(AddEmployeeinfoActivity.this);

        for (int i = 0; i < departmentInfos.size(); i++) {
            dutyInfos = dutyDAO.getDutys(departmentInfos.get(i).getDcid());
            for (int j = 0; j < dutyInfos.size(); j++) {
                if (tmppname.equals(dutyInfos.get(j).getName())) {
                    employeeInfo.setPcid(dutyInfos.get(j).getName());
                }
            }
        }

        if (TextUtils.isEmpty(employeeInfo.getName()) || TextUtils.isEmpty(employeeInfo.getEmail()) ||
                TextUtils.isEmpty(employeeInfo.getBirth()) || TextUtils.isEmpty(employeeInfo.getAddress()) ||
                TextUtils.isEmpty(employeeInfo.getId()) || TextUtils.isEmpty(employeeInfo.getTel()) ||
                TextUtils.isEmpty(employeeInfo.getSex()) || TextUtils.isEmpty(employeeInfo.getPcid()) ||
                TextUtils.isEmpty(employeeInfo.getDcid())) {
            Toast.makeText(getApplicationContext(), "信息不得为空！", Toast.LENGTH_SHORT).show();
        } else if (!isMobile(employeeInfo.getTel())) {
            Toast.makeText(getApplicationContext(), "联系方式格式输入错误！请以手机格式重新输入", Toast.LENGTH_SHORT).show();
        } else if (!isEmail(employeeInfo.getEmail())) {
            Toast.makeText(getApplicationContext(), "邮箱格式输入错误！请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //读取SharePreferences的cookies
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    String s = pref.getString("sessionid", "");

                    EmployeeInfoService employeeInfoService = new EmployeeInfoService();
                    StatusAndMsgJsonBean statusAndMsgJsonBean = employeeInfoService.addemployee(s, employeeInfo);
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

    //年月选择器
    public void onYearMonthPicker(View view) {
        DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
        //picker.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        picker.setTitleTextSize(16);
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("年月选择");
        picker.setWidth((int) (picker.getScreenWidthPixels() * 1));
        picker.setRangeStart(1900, 01, 01);
        picker.setRangeEnd(2020, 11, 11);
        picker.setSelectedItem(1996, 12);
        picker.setTopLineColor(Color.WHITE);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                birth.setText(year + "-" + month);
                //showToast(year + "-" + month);
            }
        });
        picker.show();
    }

    public void onYearMonthDayPicker(View view) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2111, 1, 11);
        picker.setRangeStart(2016, 8, 29);
        picker.setSelectedItem(2050, 10, 14);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                birth.setText(year + "-" + month + "-" + day);
                // showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    //年月日时分选择器
    public void onYearMonthDayTimePicker(View view) {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setDateRangeStart(1900, 1, 1);
        picker.setDateRangeEnd(2025, 11, 11);
        picker.setTimeRangeStart(9, 0);
        picker.setTimeRangeEnd(20, 30);
        // picker.setTopLineColor(0x99FF0000);
        picker.setLabelTextColor(0xFFFF0000);
        picker.setDividerColor(0xFFFF0000);
        picker.setTopLineColor(Color.WHITE);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("年月日选择");
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                //birth.setText(year+"-"+month+"-"+day + " " + hour + ":" + minute);
                //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    //单项选择器
    public void onOptionPicker(View view) {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "男", "女"
        });
        picker.setTitleTextColor(Color.BLACK);
        picker.setTitleText("性别选择");
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.WHITE, 40);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setTextSize(18);
        picker.setTitleTextSize(16);
        picker.setTopLineColor(Color.WHITE);
        picker.setSubmitTextSize(16);
        picker.setCancelTextSize(16);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                sex.setText(item);
                //showToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    //重写showToast
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    //多级联动
    public void onLinkagePicker(View view) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(AddEmployeeinfoActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)
                        + ":" + options2Items.get(options1).get(option2);
                /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/
                ;
                dpcid.setText(tx);
            }
        })
                .setTitleColor(Color.BLACK)
                .setTitleText("部门职务选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.rgb(0, 178, 238))//设置分割线的颜色
                .setDividerType(com.contrarywind.view.WheelView.DividerType.WRAP)
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleSize(16)
                .setCancelColor(Color.rgb(0, 178, 238))
                .setSubmitColor(Color.rgb(0, 178, 238))
                .setSubCalSize(16)
                .setTextColorCenter(Color.rgb(0, 178, 238))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        //String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                        // Toast.makeText(EditEmployeeinfoActivity.this, str, Toast.LENGTH_SHORT).show();
                        dpcid.setText(options1Items.get(options1) + ":" + options2Items.get(options2));
                        tmpdname = options1Items.get(options1);
                        tmppname = options2Items.get(options1).get(options2);
                    }
                })
                .build();

        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    //联动数据获取
    private void getOptionData() {
        DepartmentDAOImpl departmentDAO = new DepartmentDAOImpl(AddEmployeeinfoActivity.this);
        DutyDAOImpl dutyDAO = new DutyDAOImpl(AddEmployeeinfoActivity.this);

        departmentInfos = departmentDAO.getDepartments();
        //选项1
        for (int i = 0; i < departmentInfos.size(); i++) {
            options1Items.add(departmentInfos.get(i).getName());
        }

        //选项2
        for (int i = 0; i < departmentInfos.size(); i++) {
            dutyInfos = dutyDAO.getDutys(departmentInfos.get(i).getDcid());
            //Log.i("EditEmployeeinfo.DTInfo",dutyInfos.toString());
            ArrayList<String> options2Item = new ArrayList<>();
            for (int j = 1; j < dutyInfos.size(); j++) {
                options2Item.add(dutyInfos.get(j).getName());
            }
            options2Items.add(options2Item);
        }

        /*--------数据源添加完毕---------*/
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
        Timer tExit;
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