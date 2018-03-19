package com.fc.project.edroid;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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


@SuppressLint("ValidFragment")
public class EbayFragment extends Fragment {
    EditText etProduct;
    TextView tvList;
    Button btnSearch;
    String Appid="AkashDha-edroid-PRD-110683bd3-0ab96331";
    View view;
    ProgressBar pb;
    CharSequence query;
    private RecyclerView recyclerView;
    private AdapterProductsEba adapterProducts;
    public List<ProductsEba> data=new ArrayList<>();

    @SuppressLint("ValidFragment")
    public EbayFragment(CharSequence query1) {
query=query1;
//tvList.setText(query);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ebay,container,false);


        //etProduct=view.findViewById(R.id.etProduct);
        recyclerView=view.findViewById(R.id.recyclerView);
        pb=view.findViewById(R.id.pb);

                String product = query.toString();
                if (product.length() == 0) {

                    tvList.setText(query);
                }
else {
                    EbayFragment.Task2 t1 = new EbayFragment.Task2();
                    t1.execute("http://svcs.ebay.com/services/search/FindingService/v1?" +
                            "OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=" + Appid + "" +
                            "&GLOBAL-ID=EBAY-IN&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&sortOrder=BestMatch&keywords=" + product);

                }
                    return view;


    }

    public void refresh(String query) {
        data.clear();
        pb.setVisibility(view.VISIBLE);
        recyclerView.setVisibility(view.GONE);
        String product = query.toString();
        if (product.length() == 0) {

            tvList.setText(query);
        }
        else {
            EbayFragment.Task2 t1 = new EbayFragment.Task2();
            t1.execute("http://svcs.ebay.com/services/search/FindingService/v1?" +
                    "OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=" + Appid + "" +
                    "&GLOBAL-ID=EBAY-IN&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&sortOrder=BestMatch&keywords=" + product);

        }

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
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null)
                {
                    jsonstr+=line +"\n";
                }
            } catch (MalformedURLException e) {

                Toast.makeText(getActivity(), "URL Malformed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
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
                    for(int i=0;i<50;i++) {
                        JSONObject f3 = item.getJSONObject(i);
                        JSONArray title = f3.getJSONArray("title");
                        JSONArray itemlink = f3.getJSONArray("viewItemURL");

                        JSONArray image = f3.getJSONArray("galleryURL");
                        JSONArray ss = f3.getJSONArray("sellingStatus");
                        JSONObject f4=ss.getJSONObject(0);
                        JSONArray currentprice=f4.getJSONArray("currentPrice");
                        JSONObject pricejson=currentprice.getJSONObject(0);
                        ProductsEba products=new ProductsEba();

                            products.title = title.getString(0);
                            products.produrl=itemlink.getString(0);
                            products.imgurl=image.getString(0);
                            products.price=pricejson.getString("__value__");
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
            //tvList.setText(s);

            recyclerView.setLayoutManager(new VegaLayoutManager());
            if(getActivity()!=null)
            {
            adapterProducts=new AdapterProductsEba(getActivity(),data);
            adapterProducts.notifyDataSetChanged(); }
            pb.setVisibility(view.GONE);
            recyclerView.setVisibility(view.VISIBLE);
            recyclerView.setOnFlingListener(null);

            recyclerView.invalidate();

            recyclerView.setAdapter(adapterProducts);


        }
    }





}

