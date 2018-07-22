package com.oliveoa.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.oliveoa.pojo.Goods;
import com.oliveoa.view.AddGoodsActivity;
import com.oliveoa.view.EditGoodsActivity;
import com.oliveoa.view.GoodsActivity;
import com.oliveoa.view.GoodsInfoActivity;
import com.oliveoa.view.MainActivity;

import java.util.List;


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
            goodsRecord = (TextView) view.findViewById(R.id.goods_record);
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
        holder.goodsRecord.setText(mGoodsList.get(i).getgRecord());

        //为btn_edit btn_delete  cardView设置点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,GoodsInfoActivity.class);
                intent.putExtra("Goods", String.valueOf(mGoodsList.get(j)));
                mContext.startActivity(intent);
            }
        });


        holder.goodsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }

}
