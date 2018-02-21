package com.fc.project.edroid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnSearch, btnAmazon, btnEbay,btnyuge;
    EditText etProduct;
    TextView tvList;
    ImageView productImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSearch = findViewById(R.id.btnSearch);
        etProduct = findViewById(R.id.etProduct);
        tvList = findViewById(R.id.tvList);
        productImg=findViewById(R.id.imgVIew);
        btnAmazon = findViewById(R.id.btnAmazon);
        btnEbay = findViewById(R.id.btnEbay);
        btnyuge=findViewById(R.id.btndatayuge);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo =
                cm.getActiveNetworkInfo();

        if (!(networkInfo != null && networkInfo.isConnected())) {
            Toast.makeText(MainActivity.this, "Please Check Your network", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(MainActivity.this,
                    "Connected to Internet Internet", Toast.LENGTH_SHORT).show();

        btnAmazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AmazonActivity.class);
                startActivity(intent);
            }
        });

        btnyuge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, datayuge.class);
                startActivity(intent);
            }
        });


        btnEbay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EbayActivity.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String product = etProduct.getText().toString();
                if (product.length() == 0) {
                    etProduct.setError("Please a Product name");
                    etProduct.requestFocus();
                    return;
                }

                Task1 t1 = new Task1();
                t1.execute("https://affiliate-api.flipkart.net/affiliate/1.0/search.json?query="+product+"&resultCount=5");

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Task1 extends AsyncTask<String,Void,String>
    {
        String jsonstr="";
        String line= "";
        String desc="";
        String imgurl="";
        String resultSet="";
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.setRequestMethod("GET");
                connection.setRequestProperty("Fk-Affiliate-Id", "akashdeveloper");
                connection.setRequestProperty("Fk-Affiliate-Token", "281eb157bf61470b91ba4fa9a2cdc98e");
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null)
                {
                    jsonstr+=line +"\n";
                }
            } catch (MalformedURLException e) {
                Toast.makeText(MainActivity.this, "URL Malformed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this,
                        "Connection IOException", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            if(jsonstr!=null){
                try{
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray array=jsonObject.getJSONArray("productInfoList");
                    for(int i=0;i<1;i++) {
                        JSONObject firstarray = array.getJSONObject(i);
                        JSONObject quote = firstarray.getJSONObject("productBaseInfoV1");
                        JSONObject quote1=quote.getJSONObject("imageUrls");
                        //  JSONObject quote1=secondArray.getJSONObject("imageUrls");
                        if(resultSet==null) {
                            //    resultSet=quote.getString("title");
                            line = quote.getString("title");
                            desc=quote.getString("productDescription");
                            imgurl=quote1.getString("200x200");

                            resultSet="Product Title is:"+line+"\n"+"Description:"+desc+"\n"+"ImageUrl"+imgurl;
                        }
                        else
                        {
                            line = quote.getString("title");
                            desc=quote.getString("productDescription");
                            imgurl=quote1.getString("200x200");

                            resultSet=resultSet+"Product Title is:"+line+"\n"+"Description:"+desc+"\n"+"ImageUrl"+imgurl;
                        }
//                        if(resultSet==null)
//                            resultSet=quote.getString("productDescription");
//                        else
//                            resultSet=resultSet+quote.getString("productDescription");
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
//            Glide.with(getApplicationContext()).load(imgurl).into(productImg);
            Glide.with(MainActivity.this).load(imgurl).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(productImg);
        }
    }

}