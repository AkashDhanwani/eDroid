package com.fc.project.edroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class AdapterProductsAma extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    List<ProductsAma> data= Collections.emptyList();

    public AdapterProductsAma(Context context, List<ProductsAma>data) {
        this.context = context;
        this.inflater =LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view=inflater.inflate(R.layout.product_file,parent,false);
        View view=inflater.inflate(R.layout.mainpage_amazon_file,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        MyHolder myHolder=(MyHolder)holder;
        //  current=data.get(position);
        ProductsAma products=data.get(position);
        myHolder.AmazonproTitlee.setText(products.getTitle());
        //myHolder.AmazonproDescc.setText(products.getDesc());
        myHolder.AmazonproPrice.setText("\u20B9"+products.getPrice());
        //myHolder.AmazonproSellingPrice.setText("\u20B9"+products.getFlipkartSellingPrice());
        // myHolder.FlipkartproInStock.setText(products.getInStock());
        Glide.with(context).load(products.getImgUrl()).into(myHolder.AmazonprooImg);
        myHolder.title=products.getTitle();
        myHolder.imgurl=products.getImgUrl();
        myHolder.produrl=products.getProdUrl();
        myHolder.specs=products.getSpecs();


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView,AmazonprooImg;
        String[] specs=new String[5];
        String title,imgurl,desc,produrl;
        TextView proDesc,proTitle,AmazonproTitlee,AmazonproDescc,AmazonproPrice,AmazonproSellingPrice,FlipkartproInStock;
        public MyHolder(View itemView) {
            super(itemView);
//            proTitle=itemView.findViewById(R.id.title);
//            proDesc=itemView.findViewById(R.id.description);
//            imageView=itemView.findViewById(R.id.proImg);
            AmazonprooImg=itemView.findViewById(R.id.AmazonprooImg);
            AmazonproTitlee=itemView.findViewById(R.id.AmazonproTitlee);
          //  AmazonproDescc=itemView.findViewById(R.id.AmazonproDescc);
            AmazonproPrice=itemView.findViewById(R.id.AmazonproPrice);
           // AmazonproSellingPrice=itemView.findViewById(R.id.AmazonproSellingPrice);
          //  AmazonproPrice.setPaintFlags(AmazonproPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            //   FlipkartproInStock=itemView.findViewById(R.id.FlipkartproInStock);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Uri uri = Uri.parse(produrl); // missing 'http://' will cause crashed
                    Bundle bundle=new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("produrl",produrl);
                    bundle.putString("imgurl",imgurl);
                    for(int i=0;i<5;i++){
                        bundle.putStringArray("specs",specs); }

                    //    bundle.putString("desc",desc);
                    Intent intent = new Intent(context, DetailInfoActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }
    }
}
