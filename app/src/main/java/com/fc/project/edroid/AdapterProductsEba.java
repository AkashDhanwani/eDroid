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

public class AdapterProductsEba extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    List<ProductsEba> data = Collections.emptyList();

    public AdapterProductsEba(Context context, List<ProductsEba> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view=inflater.inflate(R.layout.product_file,parent,false);
        View view = inflater.inflate(R.layout.mainpage_ebay_file, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        AdapterProductsEba.MyHolder myHolder=(AdapterProductsEba.MyHolder)holder;
        ProductsEba products=data.get(position);
        myHolder.EbayproTitlee.setText(products.getTitle());
        //myHolder.AmazonproDescc.setText(products.getDesc());
        myHolder.EbayproPrice.setText("\u20B9"+products.getPrice());
        //myHolder.AmazonproSellingPrice.setText("\u20B9"+products.getFlipkartSellingPrice());
        // myHolder.FlipkartproInStock.setText(products.getInStock());
        Glide.with(context).load(products.getImgUrl()).into(myHolder.EbayprooImg);
        myHolder.produrl=products.getProdUrl();
        myHolder.title=products.getTitle();
        myHolder.imgurl=products.getImgUrl();



    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {

        ImageView EbayprooImg;
        String title,imgurl,produrl;
        TextView EbayproTitlee,EbayproPrice;
        public MyHolder(View itemView) {
            super(itemView);
            EbayprooImg=itemView.findViewById(R.id.EbayprooImg);
            EbayproTitlee=itemView.findViewById(R.id.EbayproTitlee);
            EbayproPrice=itemView.findViewById(R.id.EbayproPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Uri uri = Uri.parse(produrl); // missing 'http://' will cause crashed
                    Bundle bundle=new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("produrl",produrl);
                    bundle.putString("imgurl",imgurl);
                //    bundle.putString("desc",desc);
                    Intent intent = new Intent(context, DetailInfoActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });



        }
    }





}
