package com.fc.project.edroid;

import android.content.Context;
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
        View view=inflater.inflate(R.layout.product_file,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        MyHolder myHolder=(MyHolder)holder;
      //  current=data.get(position);
        Products products=data.get(position);
        myHolder.proTitle.setText(products.getTitle());
        myHolder.proDesc.setText(products.getDesc());
        Glide.with(context).load(products.getImgUrl()).into(myHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView proDesc,proTitle;
        public MyHolder(View itemView) {
            super(itemView);
            proTitle=itemView.findViewById(R.id.title);
            proDesc=itemView.findViewById(R.id.description);
            imageView=itemView.findViewById(R.id.proImg);


        }
    }
}
