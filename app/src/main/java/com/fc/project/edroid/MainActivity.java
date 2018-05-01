package com.fc.project.edroid;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.firebase.auth.FirebaseAuth;
import com.stone.vega.library.VegaLayoutManager;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.v7.widget.LinearLayoutManager.*;

public class
MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Button btnSearch, btnAmazon, btnEbay, btnDatayuge;
    Button btnSearch,btnSpeak;
    TextView textView;
    //EditText serachItem;
    FloatingSearchView serachItem;
    String dataa;
    ViewPager viewPager;
    FirebaseAuth mAuth;
    private RecyclerView recyclerView,recyclerView1;
    private AdapterOffers adapterOffers;
    private AdapterDotd adapterDod;

    String[] productnames={"iphone","dell","nokia","samsung"};
    public List<Productsoffers> data=new ArrayList<>();
    public List<Productsoffers> data1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int orientaton = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientaton);


//TODO extract (categorySpeciificInfoV1,detailedSpecs)
        //TODO handle nullPointerEXception
//        viewPager=findViewById(R.id.viewPager);
//        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this);
//        viewPager.setAdapter(viewPagerAdapter);
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);
//        btnSearch=findViewById(R.id.btnsearch);
//        btnSpeak=findViewById(R.id.btnspeak);
        mAuth=FirebaseAuth.getInstance();
//        textView=findViewById(R.id.textView);
//        String text="<font color=#3F51B5>E</font><font color=#f50808>d</font><font color=#ff6d40>r</font><font color=#3F51B5>o</font>" +
//                "<font color=#08f563>i</font><font color=#f50808>d</font>";
//        textView.setText(Html.fromHtml(text));
////        etProduct = findViewById(R.id.etProduct);
        serachItem=findViewById(R.id.Etsearch);
       // tvList = findViewById(R.id.tvList);
        Random random = new Random();
        int index = random.nextInt(productnames.length);

        //productImg=findViewById(R.id.imageView);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo =
                cm.getActiveNetworkInfo();

        if (!(networkInfo != null && networkInfo.isConnected())) {
            Toast.makeText(MainActivity.this, "Please Check Your network", Toast.LENGTH_SHORT).show();
            btnSearch.setEnabled(false);
//            Snackbar.make(, "No Internet Connection.", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Settings", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            //startActivityForResult(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS), 0);
//                        }
//                    }).setActionTextColor(Color.WHITE).setActionTextColor(Color.RED)
//                    .show();

        } else {
            Toast.makeText(MainActivity.this,
                    "Good to see your Internet is working well!", Toast.LENGTH_SHORT).show();
            Task1 t1 = new Task1();
            Task2 t2 = new Task2();
            t1.execute("https://affiliate-api.flipkart.net/affiliate/offers/v1/dotd/json");
            t2.execute("https://affiliate-api.flipkart.net/affiliate/offers/v1/all/json");
        }

        serachItem.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                if(currentQuery.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter a Product", Toast.LENGTH_SHORT).show();
                    serachItem.clearQuery();
                    return;
                }
                else
                {
                    dataa=currentQuery;
                    Toast.makeText(MainActivity.this, "Searching for "+dataa, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),nav2Activity.class);
                    intent.putExtra("myExtra",dataa);
                    startActivity(intent);

                }
            }
        });

//        btnSpeak.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                serachItem.setVisibility(View.VISIBLE);
//                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//
//                dataa=serachItem.getQuery();
//                Toast.makeText(MainActivity.this, "Searching for "+dataa, Toast.LENGTH_SHORT).show();
//                Intent intent1=new Intent(getApplicationContext(),nav2Activity.class);
//                intent1.putExtra("myExtra",dataa);
//                startActivity(intent);
//
//                if(intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, 10);
////                    dataa = serachItem.getText().toString();
////                    Toast.makeText(MainActivity.this, "Searching for " + dataa, Toast.LENGTH_SHORT).show();
////                    Intent intent1 = new Intent(getApplicationContext(), nav2Activity.class);
////                    intent1.putExtra("myExtra", dataa);
////                    startActivity(intent);
//                }
//                else
//                    Toast.makeText(MainActivity.this, "Your Device Don't Support Speech Text", Toast.LENGTH_SHORT).show();
//            }
//
//        });



//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(serachItem.getQuery().equals(""))
//                {
//                    revealEditText();
//                }
//                else
//                {
//                    dataa=serachItem.getQuery();
//                    Toast.makeText(MainActivity.this, "Searching for "+dataa, Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(getApplicationContext(),nav2Activity.class);
//                    intent.putExtra("myExtra",dataa);
//                    startActivity(intent);
//
//                }
//            }
//        });


//        serachItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if (i == EditorInfo.IME_ACTION_SEARCH) {
//                    //performSearch();
//                    if(serachItem.getText().toString().equals(""))
//                    {
//                        revealEditText();
//                    }
//                    else
//                    {
//                        dataa=serachItem.getText().toString();
//                        Toast.makeText(MainActivity.this, "Searching for "+dataa, Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(getApplicationContext(),nav2Activity.class);
//                        intent.putExtra("myExtra",dataa);
//                        startActivity(intent);
//
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);

        View headerView = navigationView.getHeaderView(0);
        headerView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);

    }//end of onCreate

    private void revealEditText(){
        View view = findViewById(R.id.Etsearch);

        //findind middle of widget
        int cx = view.getWidth()/2;
        int cy=view.getHeight()/2;

        float finalRadius=(float)Math.hypot(cx,cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(view,cx,cy,0,finalRadius);
        view.setVisibility(View.VISIBLE);
        anim.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String query = data.getStringExtra("title");
            dataa=query;
            Toast.makeText(MainActivity.this, "Searching for "+dataa, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),nav2Activity.class);
            intent.putExtra("myExtra",dataa);
            startActivity(intent);

        }
        switch (requestCode)
        {
            case 10:

                //ArrayList<String>result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //serachItem.setText(result.get(0));
                if(data!=null) {
                    ArrayList<String>result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    serachItem.setSearchText(result.get(0));
                }
                else
                {
                    Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(this, "welcome"+serachItem.getText().toString(), Toast.LENGTH_SHORT).show();

                break;
        }
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        }
         if (id == R.id.nav_manage) {
            Intent intent=new Intent(getApplicationContext(),ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_share) {
             String subject = "Hey there have a look at this great App";
             String body = "This App is the one solution for all the famous shopping Apps like Amazon, Flipkart and eBay" +
                     "\nhttp://bit.do/Edroid ";
             Intent intent = new Intent(Intent.ACTION_SEND);
             intent.setType("text/plain");
             intent.putExtra(Intent.EXTRA_SUBJECT, subject);
             intent.putExtra(Intent.EXTRA_TEXT, body);
             startActivity(Intent.createChooser(intent,"Share eDroid via"));

        } else if (id == R.id.nav_dis) {
             Intent intent=new Intent(getApplicationContext(),Disclaimer.class);
             startActivity(intent);

        }
        else if(id==R.id.nav_logout)
         {
             mAuth.signOut();
             Intent intent=new Intent(getApplicationContext(),AuthActivity.class);
             startActivity(intent);
             finish();
         }
         else if (id == R.id.nav_bookmark) {
             Intent intent=new Intent(MainActivity.this,bookmark.class);
           //  startActivityForResult(intent, 1 );
             startActivity(intent);

         }
         else if(id == R.id.nav_compare){
             Intent intent = new Intent(MainActivity.this, Spec_Compare.class);
             startActivity(intent);
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Task1 extends AsyncTask<String,Void,String>
    {


        String jsonstr="";
        String line= "";
        String imgurl="";
        String resultSet=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);


        }

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


//                List<Products> data=new ArrayList<>();


                while ((line = br.readLine()) != null)
                {
                    jsonstr+=line +"\n";
                }
            } catch (MalformedURLException e) {
                //   Toast.makeText(MainActivity.this, "URL Malformed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                // Toast.makeText(MainActivity.this,"Connection IOException", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            if(jsonstr!=null){
                try{
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray array=jsonObject.getJSONArray("dotdList");
                    for(int i=0;i<8;i++) {
                        //Public  List<Products> data=new ArrayList<>();
                        Productsoffers products=new Productsoffers();
                        JSONObject dotdprod = array.getJSONObject(i);
                        JSONArray images = dotdprod.getJSONArray("imageUrls");
                        JSONObject images1 = images.getJSONObject(0);
                        /*JSONObject quote = firstarray.getJSONObject("productBaseInfoV1");
                        JSONObject quote1=quote.getJSONObject("imageUrls");
                        JSONObject quote2=quote.getJSONObject("maximumRetailPrice");
                        JSONObject quote3=quote.getJSONObject("flipkartSpecialPrice");
//                        JSONObject quote4=quote.getJSONObject("categorySpecificInfoV1"); */
                        // JSONObject quote4=quote.getJSONObject("inStock");
                        //  JSONObject quote1=secondArray.getJSONObject("imageUrls");
//                        if(resultSet==null) {
                        //    resultSet=quote.getString("title");
                        products.title = dotdprod.getString("title");
                        products.produrl=dotdprod.getString("url");
                        products.desc=dotdprod.getString("description");
                        products.imgUrl=images1.getString("url");
                        //  products.desc=quote.getString("productDescription");
                        // products.imgUrl=quote1.getString("400x400");
                        //products.price=quote2.getString("amount");
                        //products.flipkartSellingPrice=quote3.getString("amount");

                        //products.inStock=quote4.getString("amount");
                        data.add(products);
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
            recyclerView=findViewById(R.id.recyclerView);
            //      recyclerView.setLayoutManager(new VegaLayoutManager());
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
            adapterDod=new AdapterDotd(MainActivity.this,data);
            adapterDod.notifyDataSetChanged();
            recyclerView.invalidate();
            recyclerView.setAdapter(adapterDod);
//            adapterProducts.notifyDataSetChanged();
            //recyclerView.invalidate();
            //recyclerView.invaldate();
            //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//            recyclerView.setLayoutManager(new VegaLayoutManager());
        }
    }


    class Task2 extends AsyncTask<String,Void,String>
    {


        String jsonstr="";
        String line= "";
        String imgurl="";
        String resultSet=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);


        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
                //   Toast.makeText(MainActivity.this, "URL Malformed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                // Toast.makeText(MainActivity.this,"Connection IOException", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            if(jsonstr!=null){
                try{
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray array=jsonObject.getJSONArray("allOffersList");
                    for(int i=0;i<20;i++) {
                        //Public  List<Products> data=new ArrayList<>();
                        Productsoffers products=new Productsoffers();
                        JSONObject offerprod = array.getJSONObject(i);
                        JSONArray images = offerprod.getJSONArray("imageUrls");
                        JSONObject images1 = images.getJSONObject(0);
                        products.title = offerprod.getString("title");
                        products.produrl=offerprod.getString("url");
                        products.desc=offerprod.getString("description");
                        products.endmil=offerprod.getLong("endTime");
                        products.imgUrl=images1.getString("url");
                        data1.add(products);
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
            recyclerView1=findViewById(R.id.recyclerView1);
            //      recyclerView.setLayoutManager(new VegaLayoutManager());
            recyclerView1.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
            adapterOffers=new AdapterOffers(MainActivity.this,data1);
            adapterOffers.notifyDataSetChanged();
            recyclerView1.invalidate();
            recyclerView1.setAdapter(adapterOffers);
//            adapterProducts.notifyDataSetChanged();
            //recyclerView.invalidate();
            //recyclerView.invaldate();
            //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//            recyclerView.setLayoutManager(new VegaLayoutManager());
        }
    }


}