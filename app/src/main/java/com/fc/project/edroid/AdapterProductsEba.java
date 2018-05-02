package com.fc.project.edroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AdapterProductsEba extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    List<ProductsEba> data = Collections.emptyList();
    int flag = 1;
    final MyDatabaseHelper dbh;

    public AdapterProductsEba(Context context, List<ProductsEba> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        dbh = MyDatabaseHelper.getInstance(context);
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


        AdapterProductsEba.MyHolder myHolder = (AdapterProductsEba.MyHolder) holder;
        ProductsEba products = data.get(position);
        myHolder.ebPrice = products.getPrice();
        myHolder.EbayproTitlee.setText(products.getTitle());
        myHolder.title = products.getTitle();
        //myHolder.AmazonproDescc.setText(products.getDesc());
        myHolder.EbayproPrice.setText("\u20B9" + products.getPrice());
        //myHolder.AmazonproSellingPrice.setText("\u20B9"+products.getFlipkartSellingPrice());
        // myHolder.FlipkartproInStock.setText(products.getInStock());
        Glide.with(context).load(products.getImgUrl()).into(myHolder.EbayprooImg);
        myHolder.produrl = products.getProdUrl();


        ArrayList<String> marksbook = new ArrayList<String>(dbh.getAllbookmark());
        Iterator<String> itc = marksbook.iterator();
        while (itc.hasNext()) {
            String abc = itc.next();
            abc = abc.substring(0, abc.length() - 1);
            if (abc.equals(myHolder.title)) {
                flag = 0;
                myHolder.btn.setBackgroundResource(R.drawable.ic_star_black_24dp);

                break;
            }
        }


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView EbayprooImg;
        String produrl;
        String title, ebPrice;
        TextView EbayproTitlee, EbayproPrice;
        Button btn;

        public MyHolder(View itemView) {
            super(itemView);
            EbayprooImg = itemView.findViewById(R.id.EbayprooImg);
            EbayproTitlee = itemView.findViewById(R.id.EbayproTitlee);
            EbayproPrice = itemView.findViewById(R.id.EbayproPrice);
            btn = itemView.findViewById(R.id.btnEbayBookMark);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    String dat = formatter.format(date);
                    if (flag == 1) {
                        //button.setBackgroundColor(Color.CYAN);
                        btn.setBackgroundResource(R.drawable.ic_star_black_24dp);
                        Toast.makeText(context.getApplicationContext(), "set bookmark", Toast.LENGTH_SHORT).show();
                        dbh.addBookmark(title, dat, "eBay", ebPrice);
                        flag = 0;
                    } else {
                        btn.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                        dbh.delBookmark(title);
                        flag = 1;
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(produrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });


        }
    }
}
