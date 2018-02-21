package com.fc.project.edroid;

import android.content.Intent;
import android.net.Uri;
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
import java.util.Iterator;

public class datayuge extends AppCompatActivity {
    EditText etProduct;
    TextView tvList;
    Button btnSearch;
    String Appid="hhUP4ZbI8aQ1Tf0ufyoZBBH9E3KhG7DwfSK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datayuge);

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
                t1.execute("http://price-api.datayuge.com/api/v1/compare/search?product="+product+"&api_key="+Appid);

            }
        });

    }

    public void perform_action(View v)
    {
        String link= (String) tvList.getText();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }


    class Task2 extends AsyncTask<String,Void,String>
    {
        String jsonstr="";
        String name="";
        String prodetails;
        String resultSet="";
        @Override
        protected String doInBackground(String... strings) {
           jsonstr = connection(strings);

            if(jsonstr!=null){
                try{
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray data=jsonObject.getJSONArray("data");
                    for(int i=0;i<5;i++){
                        //for products
                        prodetails="";
                    JSONObject f1=data.getJSONObject(i);
                   String pid=f1.getString("product_id");
                    String jsonstr1 = connection("https://price-api.datayuge.com/api/v1/compare/detail?api_key=hhUP4ZbI8aQ1Tf0ufyoZBBH9E3KhG7DwfSK&id="+pid);
                    JSONObject jsonObject1=new JSONObject(jsonstr1);
                    JSONObject data1=jsonObject1.getJSONObject("data");
                    name="Name: "+data1.getString("product_name");
                    prodetails=name;
                        JSONArray stores=data1.getJSONArray("stores");
                        for(int j=0;j<stores.length();j++){
                            //for different stores
                            JSONObject allstores=stores.getJSONObject(j);   // 0 1 2 3 4
                            Iterator<String> keys= allstores.keys();
                            String storename=keys.next();
                            if(allstores.optJSONObject(storename)!=null){
                                JSONObject store1 = allstores.getJSONObject(storename);
                                 prodetails = prodetails+"\n"+store1.getString("product_store")
                                         +": "+store1.getString("product_price");
                            }
                        }
                        resultSet =resultSet +  prodetails+"\n\n";
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
            Toast.makeText(datayuge.this, "URL Malformed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(datayuge.this,
                    "Connection IOException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return jsonstr;

    }

}
