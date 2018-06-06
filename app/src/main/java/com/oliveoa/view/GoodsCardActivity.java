package com.oliveoa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.pojo.Goods;

public class GoodsCardActivity extends AppCompatActivity {

    private TextView goodsName,goodsDescription;
    //private Button btn_edit,btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_goods);

        goodsName = (TextView) findViewById(R.id.goods_name);
        goodsDescription = (TextView) findViewById(R.id.goods_description);

        Intent intent=getIntent();

        Goods item= (Goods) intent.getSerializableExtra("Goods");
        goodsName.setText(item.getgName());
        goodsDescription.setText(item.getgDescription());

    }
}
