package com.oliveoa.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.controller.DepartmentInfoService;
import com.oliveoa.controller.DutyInfoService;
import com.oliveoa.controller.GoodInfoService;
import com.oliveoa.jsonbean.GoodInfoJsonBean;
import com.oliveoa.jsonbean.OneDepartmentInfoJsonBean;
import com.oliveoa.jsonbean.OneDutyInfoJsonBean;
import com.oliveoa.jsonbean.OneGoodInfoJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.pojo.DepartmentInfo;
import com.oliveoa.pojo.Goods;
import com.oliveoa.pojo.PropertiesInfo;
import com.oliveoa.view.AddGoodsActivity;
import com.oliveoa.view.EditGoodsActivity;
import com.oliveoa.view.GoodsActivity;
import com.oliveoa.view.GoodsInfoActivity;
import com.oliveoa.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * 资产管理显示物品列表RecyclerView适配器
 *
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private Context mContext;
    private List<Goods> mGoodsList;

    public GoodsAdapter(Context mContext, List<Goods> mGoodsList) {
        this.mContext = mContext;
        this.mGoodsList = mGoodsList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView goodsName,goodsDescription,goodsRecord;
        ImageButton goodsDelete;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            goodsName = (TextView) view.findViewById(R.id.goods_name);
            goodsDescription = (TextView) view.findViewById(R.id.goods_description);
            //goodsRecord = (TextView) view.findViewById(R.id.goods_record);
            //goodsShow = (ImageView) view.findViewById(R.id.to_info);
            //goodsEdit = (ImageButton) view.findViewById(R.id.goods_edit);
            goodsDelete = (ImageButton) view.findViewById(R.id.goods_delete);
        }
    }

    /*public GoodsAdapter(List<Goods> goodsList) {
        mGoodsList = goodsList;
    }*/

    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_goods,viewGroup,false);
        ViewHolder nvh=new ViewHolder(v);
        return nvh;
    }

   /* @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods,parent, false);
        return new ViewHolder(view);
    }*/

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        /*Goods goods = mGoodsList.get(position);
        holder.goodsName.setText(goods.getgName());
        holder.goodsDescription.setText(goods.getgDescription());*/
        final int j=i;

        holder.goodsName.setText(mGoodsList.get(i).getgName());
        holder.goodsDescription.setText(mGoodsList.get(i).getgDescription());
       // holder.goodsRecord.setText(mGoodsList.get(i).getgRecord());

        //为btn_edit btn_delete  cardView设置点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 *  需要将部门和职务名显示，但是职务名只有pcid
                 */
                HandlerThread handlerThread = new HandlerThread("HandlerThread");
                handlerThread.start();

                Handler mHandler = new Handler(handlerThread.getLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                        String s = pref.getString("sessionid","");

                        GoodInfoService goodInfoService = new GoodInfoService();
                        OneGoodInfoJsonBean oneGoodInfoJsonBean = goodInfoService.getpropertiesbyid(s,mGoodsList.get(j).getGgid());
                        if(oneGoodInfoJsonBean.getStatus()==0){
                            PropertiesInfo propertiesInfo =oneGoodInfoJsonBean.getData();
                            DutyInfoService dutyInfoService = new DutyInfoService();
                            OneDutyInfoJsonBean oneDutyInfoJsonBean = dutyInfoService.getoneduty(s,propertiesInfo.getPcid());
                            if(oneDutyInfoJsonBean.getStatus()==0){
                                DepartmentInfoService departmentInfoService = new DepartmentInfoService();
                                OneDepartmentInfoJsonBean oneDepartmentInfoJsonBean =departmentInfoService.getdepartmentinfo(s,oneDutyInfoJsonBean.getData().getDcid());
                                if(oneDepartmentInfoJsonBean.getStatus()==0){
                                    Intent intent=new Intent(mContext,GoodsInfoActivity.class);
                                    intent.putExtra("pp",propertiesInfo);
                                    intent.putExtra("pname",oneDutyInfoJsonBean.getData().getName());
                                    intent.putExtra("dname",oneDepartmentInfoJsonBean.getData().getName());
                                    mContext.startActivity(intent);
                                }else{
                                    Toast.makeText(mContext,"获取该物品的管理部门失败", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(mContext,"获取该物品的管理岗位失败", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(mContext,oneGoodInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }
                };
                mHandler.sendEmptyMessage(1);

            }
        });


        holder.goodsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerThread handlerThread = new HandlerThread("HandlerThread");
                handlerThread.start();

                Handler mHandler = new Handler(handlerThread.getLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        SharedPreferences pref = mContext.getSharedPreferences("data",MODE_PRIVATE);
                        String s = pref.getString("sessionid","");
                        GoodInfoService goodInfoService = new GoodInfoService();
                        StatusAndMsgJsonBean statusAndMsgJsonBean= goodInfoService.deleteproperty(s,mGoodsList.get(j).getGgid());
                        if(statusAndMsgJsonBean.getStatus()==0){
                            Toast.makeText(mContext, statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();

                            GoodInfoJsonBean goodInfoJsonBean = goodInfoService.properties(s);
                            if(goodInfoJsonBean.getStatus()==0){
                                Intent intent = new Intent(mContext,GoodsActivity.class);
                                intent.putExtra("allpp",goodInfoJsonBean.getData());
                                mContext.startActivity(intent);
                            }else{
                                Toast.makeText(mContext, goodInfoJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(mContext, statusAndMsgJsonBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    }
                };
                mHandler.sendEmptyMessage(1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }

}
