package com.oliveoa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erica.oliveoa_company.R;

/**
 * @Author：Guo 时间:2018/9/28 0028
 * 项目名:OliveOA_Company
 * 包名:com.oliveoa.Adapter
 * 类名:
 * 简述:<功能简述>首页图标文字显示
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;

    public GridViewAdapter(Context context){
        this.context=context;
    }

    //九宫格图片设置
    private Integer[] images={
            R.drawable.liebiao,
            R.drawable.gongwenbao,
            R.drawable.caihong,
            R.drawable.wenjian,
            R.drawable.more,
    };

    //九宫格图片下方文字设置
    private String[] texts={
            "部门管理",
            "员工管理",
            "资产管理",
            "公文查看",
            "更多期待",
    };

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageTextWrapper wrapper;
        if(view==null){
            wrapper = new ImageTextWrapper();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.gridview_item,null);
            view.setTag(wrapper);
            view.setPadding(10,22,10,22);  //每格的间距
        }
        else{
            wrapper = (ImageTextWrapper)view.getTag();
        }

        wrapper.imageView = (ImageView)view.findViewById(R.id.image);
        wrapper.imageView.setBackgroundResource(images[i]);
        wrapper.textView = (TextView)view.findViewById(R.id.text);
        wrapper.textView.setText(texts[i]);

        return view;

    }
}

class ImageTextWrapper {
    ImageView imageView;
    TextView textView;
}
