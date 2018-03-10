package com.fc.project.edroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        myHolder.FlipkartproDescc.setText(products.getDesc());
        myHolder.FlipkartproPrice.setText("\u20B9"+products.getPrice());
        myHolder.FlipkartproSellingPrice.setText("\u20B9"+products.getFlipkartSellingPrice());
       // myHolder.FlipkartproInStock.setText(products.getInStock());
        Glide.with(context).load(products.getImgUrl()).into(myHolder.FlipkartprooImg);
        myHolder.produrl=products.getProdUrl();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {
        String produrl;
        ImageView imageView,FlipkartprooImg;
        TextView proDesc,proTitle,FlipkartproTitlee,FlipkartproDescc,FlipkartproPrice,FlipkartproSellingPrice,FlipkartproInStock;
        public MyHolder(View itemView) {
            super(itemView);
//            proTitle=itemView.findViewById(R.id.title);
//            proDesc=itemView.findViewById(R.id.description);
//            imageView=itemView.findViewById(R.id.proImg);
            FlipkartprooImg=itemView.findViewById(R.id.FlipkartprooImg);
            FlipkartproTitlee=itemView.findViewById(R.id.FlipkartproTitlee);
            FlipkartproDescc=itemView.findViewById(R.id.FlipkartproDescc);
            FlipkartproPrice=itemView.findViewById(R.id.FlipkartproPrice);
            FlipkartproSellingPrice=itemView.findViewById(R.id.FlipkartproSellingPrice);
            FlipkartproPrice.setPaintFlags(FlipkartproPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
         //   FlipkartproInStock=itemView.findViewById(R.id.FlipkartproInStock);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(produrl); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });



        }
    }
}
