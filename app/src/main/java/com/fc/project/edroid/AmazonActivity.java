package com.fc.project.edroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AmazonActivity extends AppCompatActivity {

    String requestUrl = null, product;
    Button btnSearch;
    EditText etProduct;
    TextView tvList;
    SignedRequestsHelper helper;// We have to use this java file for encoding purpose

    /*The below three variables are used for the authentication purpose by passing it to
        the signed request helper class
    */
    // This the id unique to my Amazon account can be considered same as flipkart
    private static final String ACCESS_KEY_ID = "AKIAJREHRMEIPF5AWDRQ";

    // The amazon use a extra variable along with the ID that is secret key
    private static final String SECRET_KEY = "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";

    // This is the endpoint from where we will fetch the data (.in or .com)
    private static final String ENDPOINT = "webservices.amazon.in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon);

        btnSearch = findViewById(R.id.btnSearch);
        etProduct = findViewById(R.id.etProduct);
        tvList = findViewById(R.id.tvList);

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, ACCESS_KEY_ID, SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product = etProduct.getText().toString();

                /*  In this, we cannot directly pass the url like the flipkart's one
                We have to fetch a unique url each time we fire a query for the product
                Each url is the signed url containing the id and secret key
                */

                Map<String, String> params = new HashMap<String, String>();

                params.put("Service", "AWSECommerceService");
                params.put("Operation", "ItemSearch");
                params.put("AWSAccessKeyId", "AKIAJREHRMEIPF5AWDRQ");
                params.put("AssociateTag", "akashdhanwa05-21");
                params.put("SearchIndex", "All");
                params.put("ResponseGroup", "Images,ItemAttributes,Offers");
                params.put("Keywords", product);

                // the purpose of the signed class used above is to get the signed url
                requestUrl = helper.sign(params);

                Task1 t1 = new Task1();
                t1.execute(requestUrl);
            }
        });
    }
    class Task1 extends AsyncTask<String,Void,String>
    {
        String jsonstr="";
        String line= "";
        String searchResults="";
        int price;
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                //connection.setRequestProperty("Fk-Affiliate-Id", "akashdeveloper");
                // connection.setRequestProperty("Fk-Affiliate-Token", "281eb157bf61470b91ba4fa9a2cdc98e");
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null)
                {
                    jsonstr+=line +"\n";
                }
            } catch (MalformedURLException e) {
                Toast.makeText(AmazonActivity.this, "URL Malformed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                //Toast.makeText(MainActivity.this,
                //      "Connection IOException", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            return jsonstr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvList.setText(s);
        }
    }
}
