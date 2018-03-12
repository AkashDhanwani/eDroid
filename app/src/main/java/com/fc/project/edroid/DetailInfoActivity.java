package com.fc.project.edroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailInfoActivity extends AppCompatActivity {
TextView tvTitle,tvDesc;
ImageView ivimg;
Button btnBuy,btnCompare;
String[] specs=new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        tvTitle=findViewById(R.id.tvTitle);
        tvDesc=findViewById(R.id.tvDesc);
        ivimg=findViewById(R.id.ivimg);
        btnBuy=findViewById(R.id.btnBuy);
        btnCompare=findViewById(R.id.btncompare);

        final Bundle bundle=getIntent().getExtras();
        tvTitle.setText(bundle.getString("title"));
        tvDesc.setText(bundle.getString("desc"+"\n"));
        specs=bundle.getStringArray("specs");
        for(int i=0;i<specs.length;i++){
            tvDesc.append(specs[i]+"\n");
        }

        Glide.with(this).load(bundle.getString("imgurl")).into(ivimg);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(bundle.getString("produrl"));
                Intent itn = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(itn);
            }
        });
    }
}
