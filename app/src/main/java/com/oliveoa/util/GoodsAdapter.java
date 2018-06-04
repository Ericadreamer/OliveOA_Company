package com.oliveoa.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.pojo.Goods;
import com.oliveoa.view.GoodsActivity;
import com.oliveoa.view.MainActivity;

import java.util.List;


/**
 * 资产管理显示物品列表RecyclerView适配器
 */
public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private Context mContext;
    private List<Goods> mGoodsList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView goodsName,goodsDescription;
        LinearLayout goodsShow;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            goodsName = (TextView) view.findViewById(R.id.goods_name);
            goodsDescription = (TextView) view.findViewById(R.id.goods_description);
            goodsShow = (LinearLayout) view.findViewById(R.id.goods_item);
        }
    }

    public GoodsAdapter(List<Goods> goodsList) {
        mGoodsList = goodsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods,parent, false);

        //点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.goodsShow.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Goods goods = mGoodsList.get(position);
                //Toast.makeText(GoodsAdapter.this, "点击跳转编辑页面", Toast.LENGTH_SHORT).show();
            }
        }));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Goods goods = mGoodsList.get(position);
        holder.goodsName.setText(goods.getgName());
        holder.goodsDescription.setText(goods.getgDescription());
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }

}
