package com.fc.project.edroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

public class AdapterProductsRes extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int flag=1;
    private Context context;
    private LayoutInflater inflater;
    List<ProductsRes> data = Collections.emptyList();

    final MyDatabaseHelper dbh;

    public AdapterProductsRes(Context context, List<ProductsRes> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        dbh=MyDatabaseHelper.getInstance(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view=inflater.inflate(R.layout.product_file,parent,false);
        View view = inflater.inflate(R.layout.mainpage_datayuge_file, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AdapterProductsRes.MyHolder myHolder=(AdapterProductsRes.MyHolder)holder;
        final ProductsRes products=data.get(myHolder.getAdapterPosition());
        myHolder.datayugeproTitlee.setText(products.getTitle());
        myHolder.title=products.getTitle();
       // String[] storename=new String[4];
       // String[] price=new String[4];
        //final String[] produrl=new String[4];
        Glide.with(context).load(products.getImgUrl()).into(myHolder.datayugeprooImg);

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("title",products.getTitle());
                bundle.putString("img",products.getImgUrl());
                bundle.putString("pid",products.getPid());
                Intent intent = new Intent(context, datayuge_detail.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
       /* myHolder.storepriceurl=products.getProds();
        for(int i=0;i<4;i++){
        storename[i]=myHolder.storepriceurl[i][0];
        price[i]=myHolder.storepriceurl[i][1];
        produrl[i]=myHolder.storepriceurl[i][2];
        }
        if(storename[0]==null){ myHolder.button1.setVisibility(View.GONE);
       // removeAt(myHolder.getAdapterPosition(),position);
        }
        else {
            myHolder.button1.setText(storename[0] + ": " + price[0]);
            myHolder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(produrl[0]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });

        }
        if(storename[1]==null) myHolder.button2.setVisibility(View.GONE);

        else {
            myHolder.button2.setText(storename[1] + ": " + price[1]);
            myHolder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(produrl[1]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });

        }
        if(storename[2]==null) myHolder.button3.setVisibility(View.GONE);
        else {
            myHolder.button3.setText(storename[2] + ": " + price[2]);
            myHolder.button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(produrl[2]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });

        }
        if(storename[3]==null){ myHolder.button4.setVisibility(View.GONE);    }
        else {
            myHolder.button4.setText(storename[3] + ": " + price[3]);
            myHolder.button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(produrl[3]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });

        }


        ArrayList<String> marksbook=new ArrayList<String>(dbh.getAllbookmark());
        Iterator<String> itc=marksbook.iterator();
        while(itc.hasNext()){
            String abc=itc.next();
            abc = abc.substring(0, abc.length() - 1);
            if(abc.equals(myHolder.title)){     flag=0;
                myHolder.btn.setBackgroundResource(R.drawable.ic_star_black_24dp);
                break;
            }
        }
*/
    }

    private void removeAt(final int adapterPosition, final int position) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
                data.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(adapterPosition, data.size());
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder
    {

        ImageView datayugeprooImg;
        String title;
  //      String[][] storepriceurl=new String[4][3];
        TextView datayugeproTitlee;
        Button button1,button2,button3,button4,btn;
        public MyHolder(View itemView) {
            super(itemView);
         //   button1=itemView.findViewById(R.id.button1);
          //  button2=itemView.findViewById(R.id.button2);
          //  button3=itemView.findViewById(R.id.button3);
          //  button4=itemView.findViewById(R.id.button4);
            datayugeprooImg=itemView.findViewById(R.id.datayugeprooImg);
            datayugeproTitlee=itemView.findViewById(R.id.datayugeproTitlee);
            btn=itemView.findViewById(R.id.btnDatayugeBookMark);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    String dat = formatter.format(date);
                    if(flag==1)
                    {
                        //button.setBackgroundColor(Color.CYAN);
                        btn.setBackgroundResource(R.drawable.ic_star_black_24dp);
                        Toast.makeText(context.getApplicationContext(), "set bookmark", Toast.LENGTH_SHORT).show();
                        dbh.addBookmark(title, dat, "Others Tab", "Variable Price");
                        flag=0;
                    }
                    else
                    {
                        btn.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                   //     Toast.makeText(context.getApplicationContext(), "cancel bookmark", Toast.LENGTH_SHORT).show();
                        dbh.delBookmark(title);
                        flag=1;
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();

                }
            });

        }
    }

}
