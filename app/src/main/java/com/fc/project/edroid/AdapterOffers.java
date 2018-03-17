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

public class AdapterOffers extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


   private Context context;
   private LayoutInflater inflater;
   List<Productsoffers> data= Collections.emptyList();


    public AdapterOffers(Context context, List<Productsoffers>data) {
        this.context = context;
        this.inflater =LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.offer_file,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        MyHolder myHolder=(MyHolder)holder;
        Productsoffers products=data.get(position);
        myHolder.FkTitle.setText(products.getTitle());
        myHolder.FkDesc.setText(products.getDesc());
        Glide.with(context).load(products.getImgUrl()).into(myHolder.FkImg);
        myHolder.produrl=products.getProdUrl();


            myHolder.endtime = products.getEndtime();

            myHolder.FkEndtime.setText("End Of Offer at: " + myHolder.endtime[0] + " : " + myHolder.endtime[1]);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {
        String produrl,imgurl,title;
        String[] endtime=new String[2];
        ImageView FkImg;
        TextView FkTitle,FkDesc,FkEndtime;
        public MyHolder(View itemView) {
            super(itemView);
//            proTitle=itemView.findViewById(R.id.title);
//            proDesc=itemView.findViewById(R.id.description);
//            imageView=itemView.findViewById(R.id.proImg);
            FkImg=itemView.findViewById(R.id.FkImg);
            FkTitle=itemView.findViewById(R.id.FkTitle);
            FkDesc=itemView.findViewById(R.id.FkDesc);
            FkEndtime=itemView.findViewById(R.id.FkEndtime);

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
