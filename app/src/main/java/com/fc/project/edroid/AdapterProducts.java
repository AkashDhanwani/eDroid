package com.fc.project.edroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by LakhwaniPc on 18-02-2018.
 */

public class AdapterProducts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


   private Context context;
   private LayoutInflater inflater;
   List<Products> data= Collections.emptyList();
   Products current;
   int currentpos=0;
    int flag=1;

    public AdapterProducts(Context context, List<Products>data) {
        this.context = context;
        this.inflater =LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view=inflater.inflate(R.layout.product_file,parent,false);
        View view=inflater.inflate(R.layout.mainpage_flipkart_file,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        MyHolder myHolder=(MyHolder)holder;
      //  current=data.get(position);
        Products products=data.get(position);
        myHolder.FlipkartproTitlee.setText(products.getTitle());
        myHolder.FlipkartproPrice.setText("\u20B9"+products.getPrice());
        myHolder.FlipkartproSellingPrice.setText("\u20B9"+products.getFlipkartSellingPrice());
        Glide.with(context).load(products.getImgUrl()).into(myHolder.FlipkartprooImg);

        myHolder.title=products.getTitle();
        myHolder.imgurl=products.getImgUrl();
        myHolder.produrl=products.getProdUrl();
        myHolder.desc=products.getDesc();
        myHolder.specs=products.getSpecs();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {
        String produrl,imgurl,title,desc;
        String[] specs=new String[5];
        ImageView imageView,FlipkartprooImg;
        Button btnBookMark;
        TextView proDesc,proTitle,FlipkartproTitlee,FlipkartproDescc,FlipkartproPrice,FlipkartproSellingPrice,FlipkartproInStock;
        public MyHolder(View itemView) {
            super(itemView);
//            proTitle=itemView.findViewById(R.id.title);
//            proDesc=itemView.findViewById(R.id.description);
//            imageView=itemView.findViewById(R.id.proImg);
            FlipkartprooImg=itemView.findViewById(R.id.FkImg);
            FlipkartproTitlee=itemView.findViewById(R.id.FkTitle);
   //         FlipkartproDescc=itemView.findViewById(R.id.FlipkartproDescc);
            FlipkartproPrice=itemView.findViewById(R.id.FlipkartproPrice);
            FlipkartproSellingPrice=itemView.findViewById(R.id.FlipkartproSellingPrice);
            FlipkartproPrice.setPaintFlags(FlipkartproPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);     //?
            btnBookMark=itemView.findViewById(R.id.btnBookMark);
         //   FlipkartproInStock=itemView.findViewById(R.id.FlipkartproInStock);
            btnBookMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(flag==1)
                    {
                        //button.setBackgroundColor(Color.CYAN);
                        btnBookMark.setBackgroundResource(R.drawable.ic_star_black_24dp);
                        Toast.makeText(context.getApplicationContext(), "set bookmark", Toast.LENGTH_SHORT).show();
                        flag=0;
                    }
                    else
                    {
                        btnBookMark.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                        Toast.makeText(context.getApplicationContext(), "cancel bookmark", Toast.LENGTH_SHORT).show();
                        flag=1;
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Uri uri = Uri.parse(produrl); // missing 'http://' will cause crashed
                    Bundle bundle=new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("produrl",produrl);
                    bundle.putString("imgurl",imgurl);
                    bundle.putString("desc",desc);
                    for(int i=0;i<5;i++){
                    bundle.putStringArray("specs",specs); }
                    Intent intent = new Intent(context, DetailInfoActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });



        }
    }
}
