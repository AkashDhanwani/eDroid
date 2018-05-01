package com.fc.project.edroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailInfoActivity extends AppCompatActivity {
TextView tvTitle,tvDesc;
ImageView ivimg;
Button btnBuy,btnShare, btnCompare;
String[] specs=new String[5];
Bundle bundle;
Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.newdetail);

        tvTitle=findViewById(R.id.tvTitle);
        tvDesc=findViewById(R.id.tvDesc);
        ivimg=findViewById(R.id.ivimg);
        btnBuy=findViewById(R.id.btnBuy);
        btnShare=findViewById(R.id.btnShare);
        btnCompare = findViewById(R.id.btncompare12);

        bundle=getIntent().getExtras();
        tvTitle.setText(bundle.getString("title"));
        tvDesc.setText(bundle.getString("desc"+"\n"));
        specs=bundle.getStringArray("specs");

        for(int i=0;i<specs.length;i++){
            if(specs[i]!=null)
            tvDesc.append("* "+specs[i]+"\n\n");
        }

        Glide.with(this).load(bundle.getString("imgurl")).into(ivimg);
        //final Uri link= Uri.parse(bundle.getString("produrl"));
            final String link=bundle.getString("produrl");

            btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uri = Uri.parse(bundle.getString("produrl"));
                Intent itn = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(itn);
            }
        });


            btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,link);
                startActivity(Intent.createChooser(intent,"Share via.."));
            }
        });

        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailInfoActivity.this,Spec_Compare.class);
                startActivity(intent);
            }
        });

    }
}
