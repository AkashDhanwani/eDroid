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
import java.util.Iterator;
import java.util.List;

@SuppressLint("ValidFragment")
public class DatayugeFragment extends Fragment {
    EditText etProduct;
    TextView tvList;
    Button btnSearch;
    String Appid="hhUP4ZbI8aQ1Tf0ufyoZBBH9E3KhG7DwfSK";
    View view;
    String query;
    private RecyclerView recyclerView;
    private AdapterProductsRes adapterProducts;
    public List<ProductsRes> data123=new ArrayList<>();

    @SuppressLint("ValidFragment")
    public DatayugeFragment(CharSequence query1) {
        query= (String) query1;
//tvList.setText(query);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_datayuge,container,false);

                String product = query;

                Task2 t1 = new Task2();
                t1.execute("http://price-api.datayuge.com/api/v1/compare/search?product="+product+"&api_key="+Appid);

        return view;
    }

    public void refresh(String query) {

        data123.clear();
        String product = query;

        Task2 t1 = new Task2();
        t1.execute("http://price-api.datayuge.com/api/v1/compare/search?product="+product+"&api_key="+Appid);

    }


    class Task2 extends AsyncTask<String, Void, String>
    {
        String jsonstr="";
        String[][] pricestoresurl=new String[4][3];
        String prodetails;
        String resultSet="";


        @Override
        protected String doInBackground(String... strings) {
            jsonstr = connection(strings);

            if(jsonstr!=null){
                try{
                    JSONObject jsonObject=new JSONObject(jsonstr);
                    JSONArray data=jsonObject.getJSONArray("data");
                    for(int i=0;i<10;i++){
                        //for products
                        prodetails="";
                        String[][] pricestoresurl=new String[4][3];
                        JSONObject f1=data.getJSONObject(i);
                        String pid=f1.getString("product_id");
                        String jsonstr1 = connection("https://price-api.datayuge.com/api/v1/compare/detail?api_key=hhUP4ZbI8aQ1Tf0ufyoZBBH9E3KhG7DwfSK&id="+pid);
                        JSONObject jsonObject1=new JSONObject(jsonstr1);
                        JSONObject data1=jsonObject1.getJSONObject("data");

                        ProductsRes products=new ProductsRes();
                        products.title=f1.getString("product_title");
                        products.imgurl=f1.getString("product_image");
                        JSONArray stores=data1.getJSONArray("stores");
                        int n=0;
                        for(int j=4;j<stores.length();j++){
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
                            products.prods=pricestoresurl;

                        }  data123.add(products);
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
            //tvList.setText();
            recyclerView=view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new VegaLayoutManager());
            adapterProducts=new AdapterProductsRes(getActivity(),data123);
            recyclerView.setOnFlingListener(null);
            adapterProducts.notifyDataSetChanged();
            recyclerView.invalidate();

            recyclerView.setAdapter(adapterProducts);

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
            Toast.makeText(getActivity(), "URL Malformed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(getActivity(),
                    "Connection IOException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return jsonstr;

    }


}