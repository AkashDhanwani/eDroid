package com.fc.project.edroid;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class datayuge_detail extends AppCompatActivity {
    String[] storename=new String[4];
    String[] price=new String[4];
    final String[] produrl=new String[4];
    Button button1,button2,button3,button4;
    ImageView ivimg;
    TextView tvTitle;

    int flag;
    String appid="H3jyQd8owEXnHW32UBXXMUXgpWMr4m9QyHb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datayuge_detail);
        ivimg=findViewById(R.id.ivimg);
        tvTitle=findViewById(R.id.tvTitle);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        Intent intent=getIntent();
        tvTitle.setText(intent.getStringExtra("title"));
        Glide.with(this).load(intent.getStringExtra("img")).into(ivimg);
        String pid=intent.getStringExtra("pid");

        Task t1 = new Task();
        t1.execute("https://price-api.datayuge.com/api/v1/compare/detail?api_key="+appid+"&id="+pid);

    }
    class Task extends AsyncTask<String, Void, String[][]>
    {
        String jsonstr="";
//        String prodetails;
        String resultSet="";
        String[][] pricestoresurl=new String[4][3];



        @Override
        protected String[][] doInBackground(String... strings) {
            jsonstr = connection(strings);

            if(jsonstr!=null){
                try{
//                    String[][] pricestoresurl=new String[4][3];

//                    JSONObject jsonObject=new JSONObject(jsonstr);
  //                  JSONArray data=jsonObject.getJSONArray("data");
    //                for(int i=0;i<10;i++){
      //                  //for products
        //                prodetails="";
          //              String[][] pricestoresurl=new String[4][3];
            //            JSONObject f1=data.getJSONObject(i);
              //          String pid=f1.getString("product_id");
                        JSONObject jsonObject1=new JSONObject(jsonstr);
                        JSONObject data1=jsonObject1.getJSONObject("data");

//                        ProductsRes products=new ProductsRes();
  //                      products.title=f1.getString("product_title");
    //                    products.imgurl=f1.getString("product_image");
                        JSONArray stores=data1.getJSONArray("stores");
                        int n=0;
                        for(int j=4;j<stores.length();j++){
                            //for different stores
                            JSONObject allstores=stores.getJSONObject(j);   // 0 1 2 3 4
                            Iterator<String> keys= allstores.keys();
                            String storename=keys.next();
                            if(allstores.optJSONObject(storename)!=null){
                                JSONObject store1 = allstores.getJSONObject(storename);
                                String name1=store1.getString("product_store");
                                String price1=store1.getString("product_price");
                                String url1=store1.getString("product_store_url");
                                pricestoresurl[n]= new String[] {name1,price1,url1 } ;
                                n++;
                            }
                       //     products.prods=pricestoresurl;
                        }
                        //for(int j=0;j<n;j++){
                          //  if(pricestoresurl[j]==null){}
                        //    else{ data123.add(products); break; }
                       // }



//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return pricestoresurl;

        }

        @Override
        protected void onPostExecute(String[][] storepriceurl) {
            super.onPostExecute(storepriceurl);
            //tvList.setText();


  /*          recyclerView.setLayoutManager(new VegaLayoutManager());


            if(getActivity()!=null){
                adapterProducts=new AdapterProductsRes(getActivity(),data123);

                adapterProducts.notifyDataSetChanged();
                recyclerView.setAdapter(adapterProducts);  }
            pb.setVisibility(view.GONE);
            recyclerView.setVisibility(view.VISIBLE);


            recyclerView.setOnFlingListener(null);
            recyclerView.invalidate();

*/          flag=0;
            String[] storename=new String[4];
            String[] price=new String[4];
            final String[] produrl=new String[4];
            for(int i=0;i<4;i++){
                storename[i]=storepriceurl[i][0];
                price[i]=storepriceurl[i][1];
                produrl[i]=storepriceurl[i][2];
            }
            if(storename[0]==null){ button1.setVisibility(View.GONE);
                // removeAt(getAdapterPosition(),position);
            }
            else {
                button1.setText(storename[0] + ": " + price[0]);
                flag++;
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(produrl[0]);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }

                });

            }
            if(storename[1]==null) button2.setVisibility(View.GONE);

            else {
                button2.setText(storename[1] + ": " + price[1]);
                flag++;
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(produrl[1]);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });

            }
            if(storename[2]==null) button3.setVisibility(View.GONE);
            else {
                button3.setText(storename[2] + ": " + price[2]);
                flag++;
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(produrl[2]);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });

            }
            if(storename[3]==null){ button4.setVisibility(View.GONE);    }
            else {
                button4.setText(storename[3] + ": " + price[3]);
                flag++;
                button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(produrl[3]);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });

            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(flag==0) Toast.makeText(datayuge_detail.this, "Product not available in other stores, Check in Amazon,Flipkart and Ebay",Toast.LENGTH_LONG).show();

                }
            }, 1000);

        }
    }
    public String connection(String... strings) {
        String jsonstr = "";
        String line = "";
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null)
            {
                jsonstr+=line +"\n";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
                    Toast.makeText(this, "Too many attempts on others tab, search again", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return jsonstr;

    }

}
