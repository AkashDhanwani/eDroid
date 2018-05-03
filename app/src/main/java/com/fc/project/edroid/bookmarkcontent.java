
        package com.fc.project.edroid;

        import android.content.Intent;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Handler;
        import android.os.Looper;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

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

public class bookmarkcontent extends AppCompatActivity {
    TextView tvsearched,tvTitle;
    String title;
    Button[] button=new Button[6];

    String Appid="H3jyQd8owEXnHW32UBXXMUXgpWMr4m9QyHb";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarkcontent);
        tvTitle=findViewById(R.id.tvTitle);
        for(int i=0;i<6;i++){
            button[i]=findViewById(R.id.button+(i+1));
        }
       tvsearched=findViewById(R.id.tvsearched);

        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        tvTitle.setText("Searched product is "+title);
        //title = title.replaceAll("\\p{P}","");
            // tvsea.setText(title);

        Task1 t1 = new Task1();
        t1.execute("http://price-api.datayuge.com/api/v1/compare/search?product="+title+"&api_key="+Appid);

    }
    class Task1 extends AsyncTask<String, Void, String[][]> {
        String jsonstr="";
        String prodetails;
        String[][] pricestoresurl=new String[6][3];

        String resultSet="";
       // String prices[]=new String[3];
        String name;

        @Override
        protected String[][] doInBackground(String... strings) {
            jsonstr = connection(strings);

            if(jsonstr!=null){
                try{
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray data=jsonObject.getJSONArray("data");
                    //       for(int i=0;i<10;i++){
                    //for products
                    prodetails="";
                      JSONObject f1=data.getJSONObject(0);
                    String pid=f1.getString("product_id");
                    String jsonstr1 = connection("https://price-api.datayuge.com/api/v1/compare/detail?api_key=H3jyQd8owEXnHW32UBXXMUXgpWMr4m9QyHb&id="+pid);
                    JSONObject jsonObject1=new JSONObject(jsonstr1);
                    JSONObject data1=jsonObject1.getJSONObject("data");

                    //ProductsRes products=new ProductsRes();
                    name=f1.getString("product_title");
                    //  products.imgurl=f1.getString("product_image");
                    JSONArray stores=data1.getJSONArray("stores");
                    int n=0;
                    for(int j=0;j<8;j++){
                        //for different stores
                        JSONObject allstores=stores.getJSONObject(j);   // 0 1 2 3 4
                        Iterator<String> keys= allstores.keys();
                        String storename=keys.next();
                        if(allstores.optJSONObject(storename)!=null){
                            JSONObject store1 = allstores.getJSONObject(storename);
                            String name=store1.getString("product_store");
                            String price=store1.getString("product_price");
                            String url=store1.getString("product_store_url");
                                pricestoresurl[n]= new String[] {name,price,url } ;
                            n++;
                        }
                        //  products.prods=pricestoresurl;
                    }
                        /*for(int j=0;j<n;j++){
                            if(pricestoresurl[j]==null){}
                            else{ data123.add(products); break; }
                        }*/



                    // }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return pricestoresurl;

        }

        @Override
        protected void onPostExecute(String[][] storepriceurl) {
            super.onPostExecute(storepriceurl);
            tvsearched.setText("Retrieved product: "+name);


            String[] storename=new String[6];
            String[] price=new String[6];
            final String[] produrl=new String[6];
            for(int i=0;i<6;i++){
                storename[i]=storepriceurl[i][0];
                price[i]=storepriceurl[i][1];
                produrl[i]=storepriceurl[i][2];
            }
            for(int i=0;i<6;i++) {
                if (storename[i] == null) {

                    button[i].setVisibility(View.GONE);
                    // removeAt(getAdapterPosition(),position);
                } else {
                    final int index=i;
                    button[i].setText(storename[i] + ": " + price[i]);
                    button[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse(produrl[index]);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });

                }
            }


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
            Handler handler = new Handler(Looper.getMainLooper());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonstr;

    }

}