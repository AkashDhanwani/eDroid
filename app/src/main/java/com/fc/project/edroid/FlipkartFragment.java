package com.fc.project.edroid;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
public class FlipkartFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private AdapterProducts adapterProducts;
    private ProgressBar pb;
    public List<Products> data=new ArrayList<>();
    CharSequence query;


    @SuppressLint("ValidFragment")
    public FlipkartFragment(CharSequence query1) {
        query=query1;
        // Required empty public constructor
    }

     public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){

            view = inflater.inflate(R.layout.fragment_flipkart, container, false);
         recyclerView=view.findViewById(R.id.recyclerView);
         pb=view.findViewById(R.id.pb);
         recyclerView.setVisibility(view.GONE);
         recyclerView.setLayoutManager(new VegaLayoutManager());


         final String product = (String) query;
         final int[] i = {0};
        callpages(product);


/*
         recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
             @Override
             public void onLoadMore() {

                i[0] = i[0] + 1;
                 callpages(product+" "+Integer.toString(i[0]));

             }
         });

*/


         return view;
        }

    private void callpages(String product) {
        Task1 t1 = new Task1();
        t1.execute("https://affiliate-api.flipkart.net/affiliate/1.0/search.json?query=" + product + "&resultCount=10");

    }

    public void refresh(final String query) {
        pb.setVisibility(view.VISIBLE);
        recyclerView.setVisibility(view.GONE);
            data.clear();
        final String product = query;
        callpages(product);
        Toast.makeText(getActivity(), "This is refresh method", Toast.LENGTH_SHORT).show();
        /*final int[] i = {0};
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                i[0] = i[0] + 1;
                callpages(product+" "+Integer.toString(i[0]));

            }
        });
*/

    }



    class Task1 extends AsyncTask<String,Void,String>
    {


        String jsonstr="";
        String line= "";
        String resultSet=null;
        String[] specs=new String[5];

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
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(jsonstr!=null){
                try{
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray array=jsonObject.getJSONArray("products");
                    for(int i=0;i<10;i++) {
                        //Public  List<Products> data=new ArrayList<>();
                        Products products=new Products();
                        JSONObject firstarray = array.getJSONObject(i);
                        JSONObject quote = firstarray.getJSONObject("productBaseInfoV1");
                        JSONObject specs1 = firstarray.getJSONObject("categorySpecificInfoV1");
                        JSONArray detailedspecs = specs1.getJSONArray("detailedSpecs");
                        int size=detailedspecs.length();
                        for(int s = 0; s<size && s<5; s++){
                            products.specs[s]=detailedspecs.getString(s);
                        }
                        JSONObject quote1=quote.getJSONObject("imageUrls");
                        JSONObject quote2=quote.getJSONObject("maximumRetailPrice");
                        JSONObject quote3=quote.getJSONObject("flipkartSpecialPrice");
                        // JSONObject quote4=quote.getJSONObject("inStock");
                        //  JSONObject quote1=secondArray.getJSONObject("imageUrls");
//                        if(resultSet==null) {
                            //    resultSet=quote.getString("title");
                            products.title = quote.getString("title");
                            products.produrl=quote.getString("productUrl");
                            products.desc=quote.getString("productDescription");
                            products.imgUrl=quote1.getString("400x400");
                            products.price=quote2.getString("amount");
                            products.flipkartSellingPrice=quote3.getString("amount");
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

            if (getActivity() != null) {
                adapterProducts=new AdapterProducts(getActivity(),data);
                adapterProducts.notifyDataSetChanged();
            }
            pb.setVisibility(view.GONE);
            recyclerView.setVisibility(view.VISIBLE);
            recyclerView.setAdapter(adapterProducts);
            recyclerView.setOnFlingListener(null);
            recyclerView.invalidate();


        }
    }



}
