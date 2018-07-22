package com.oliveoa.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.erica.oliveoa_company.R;
import com.oliveoa.pojo.Goods;

public class GoodsCardActivity extends AppCompatActivity {

    private TextView goodsName,goodsDescription,goodsRecord;
    private ImageButton btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_goods);

        goodsName = (TextView) findViewById(R.id.goods_name);
        goodsDescription = (TextView) findViewById(R.id.goods_description);
        goodsRecord = (TextView) findViewById(R.id.goods_record);
        btn_delete = (ImageButton) findViewById(R.id.goods_delete);

        Intent intent=getIntent();

        Goods item= (Goods) intent.getSerializableExtra("Goods");
        goodsName.setText(item.getgName());
        goodsDescription.setText(item.getgDescription());
        goodsRecord.setText(item.getgRecord());


    }
}
