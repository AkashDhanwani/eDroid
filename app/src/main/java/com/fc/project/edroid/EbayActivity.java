package com.fc.project.edroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EbayActivity extends AppCompatActivity {
    EditText etProduct;
    TextView tvList;
    Button btnSearch;
    String Appid="AkashDha-edroid-PRD-110683bd3-0ab96331";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebay);

        etProduct=findViewById(R.id.etProduct);
        tvList=findViewById(R.id.tvList);
        btnSearch=findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String product = etProduct.getText().toString();
                if (product.length() == 0) {
                    etProduct.setError("Please a Product name");
                    etProduct.requestFocus();
                    return;
                }

                Task2 t1 = new Task2();
                t1.execute("http://svcs.ebay.com/services/search/FindingService/v1?" +
                        "OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME="+Appid+"" +
                        "&GLOBAL-ID=EBAY-IN&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&sortOrder=BestMatch&keywords="+product);

            }
        });

    }


    class Task2 extends AsyncTask<String,Void,String>
    {
        String jsonstr="";
        String line= "";
        String price="";
        String imgurl="";
        String itemurl="";
        String resultSet="";
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.setRequestMethod("GET");
                //connection.setRequestProperty("Fk-Affiliate-Id", "akashdeveloper");
                //connection.setRequestProperty("Fk-Affiliate-Token", "281eb157bf61470b91ba4fa9a2cdc98e");
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null)
                {
                    jsonstr+=line +"\n";
                }
            } catch (MalformedURLException e) {
                Toast.makeText(EbayActivity.this, "URL Malformed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(EbayActivity.this,
                        "Connection IOException", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            if(jsonstr!=null){
                try{
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray fibkr=jsonObject.getJSONArray("findItemsByKeywordsResponse");
                    JSONObject f1=fibkr.getJSONObject(0);
                    JSONArray search=f1.getJSONArray("searchResult");
                    JSONObject f2=search.getJSONObject(0);
                    JSONArray item=f2.getJSONArray("item");
                    for(int i=0;i<5;i++) {
                        JSONObject f3 = item.getJSONObject(i);
                        JSONArray title = f3.getJSONArray("title");
                        JSONArray itemlink = f3.getJSONArray("viewItemURL");

                        //JSONArray image = f3.getJSONArray("galleryURL");
                        JSONArray ss = f3.getJSONArray("sellingStatus");
                        JSONObject f4=ss.getJSONObject(0);
                        JSONArray currentprice=f4.getJSONArray("currentPrice");
                        JSONObject pricejson=currentprice.getJSONObject(0);



                        if(resultSet==null) {

                            line = title.getString(0);
                            itemurl=itemlink.getString(0);
                            //imgurl=image.getString(0);
                            price=pricejson.getString("__value__");
                            resultSet="Product Title is:"+line+"\n"+"Description:"+itemurl+"\n"+"Price:"+price;
                        }
                        else
                        {
                            line = title.getString(0);
                            itemurl=itemlink.getString(0);
                            //imgurl=image.getString(0);
                            price=pricejson.getString("__value__");
                            resultSet=resultSet+"\n\nProduct Title is:"+line+"\n"+"Description:"+itemurl+"\n"+"Price:"+price;;
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return resultSet;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvList.setText(s);


        }
    }

}
