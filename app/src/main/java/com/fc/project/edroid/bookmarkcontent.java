package com.fc.project.edroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class bookmarkcontent extends AppCompatActivity {
    TextView tvTitle,tvFlipkart,tvAmazon,tvEbay;
    String title,title1;
    EditText ettest;
    String Appid="antfjRhXLpFSSCH0iVYGq0wBoG5hj6wKBTE";
    SignedRequestsHelper helper;
    String requestUrl = null;


    private static final String ACCESS_KEY_ID = "AKIAJREHRMEIPF5AWDRQ";

    // The amazon use a extra variable along with the ID that is secret key
    private static final String SECRET_KEY = "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";

    // This is the endpoint from where we will fetch the data (.in or .com)
    private static final String ENDPOINT = "webservices.amazon.in";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarkcontent);
        tvTitle=findViewById(R.id.tvTitle);
        tvFlipkart=findViewById(R.id.tvFlipkart);
        tvAmazon=findViewById(R.id.tvAmazon);
        tvEbay=findViewById(R.id.tvEbay);
        ettest=findViewById(R.id.ettest);



        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, ACCESS_KEY_ID, SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        tvTitle.setText(title);
    title1 = title.replaceAll("\\p{P}","");
  //     ettest.setText(title);


        Map<String, String> params = new HashMap<String, String>();

        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("AWSAccessKeyId", "AKIAJREHRMEIPF5AWDRQ");
        params.put("AssociateTag", "akashdhanwa05-21");
        params.put("SearchIndex", "All");
        params.put("ItemPage", String.valueOf(1)); // increment this for second page
        params.put("ResponseGroup", "Images,ItemAttributes,Offers");
        params.put("Keywords", title);

        // the purpose of the signed class used above is to get the signed url
        requestUrl = helper.sign(params);


        Task1 t1 = new Task1();
        Task2 t2 = new Task2();

        t1.execute("https://affiliate-api.flipkart.net/affiliate/1.0/search.json?query=" + title1 + "&resultCount=10");
        t2.execute(requestUrl);

    }

    class Task1 extends AsyncTask<String, Void, Bundle>
    {


        String jsonstr="";
        String line= "";
        String resultSet=null;
        String[] specs=new String[5];
        Bundle bundle=new Bundle();
        String fksp,ttfk;

        @Override
        protected Bundle doInBackground(String... strings) {
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
                       // Products products=new Products();
                        JSONObject firstarray = array.getJSONObject(i);
                        JSONObject quote = firstarray.getJSONObject("productBaseInfoV1");
                        //JSONObject specs1 = firstarray.getJSONObject("categorySpecificInfoV1");
                        //JSONArray detailedspecs = specs1.getJSONArray("detailedSpecs");
                    /*    int size=detailedspecs.length();
                        for(int s = 0; s<size && s<5; s++){
                            products.specs[s]=detailedspecs.getString(s);
                        }*/
                        //JSONObject quote1=quote.getJSONObject("imageUrls");
                        //JSONObject quote2=quote.getJSONObject("maximumRetailPrice");
                        JSONObject quote3=quote.getJSONObject("flipkartSpecialPrice");
                        // JSONObject quote4=quote.getJSONObject("inStock");
                        //  JSONObject quote1=secondArray.getJSONObject("imageUrls");
                        if(resultSet==null && title.equals(quote.getString("title"))) {
                            //    resultSet=quote.getString("title");
                            //products.title = quote.getString("title");
                            //products.produrl=quote.getString("productUrl");
                            //products.desc=quote.getString("productDescription");
                            //products.imgUrl=quote1.getString("400x400");
                            //products.price=quote2.getString("amount");
                            bundle.putString("title",quote.getString("title"));
                            //ttfk=quote.getString("title");
                            bundle.putString("price",quote3.getString("amount"));
                            //fksp = quote3.getString("amount");
                            //products.inStock=quote4.getString("amount");
                            //data.add(products);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return bundle;

        }

        @Override
        protected void onPostExecute(Bundle s) {
            super.onPostExecute(s);
            tvFlipkart.setText("Flipkart price: "+s.getString("price"));
          //  ettest.setText(s.getString("title"));

        }
    }




    class Task2 extends AsyncTask<String, Void, Bundle>
    {
        String jsonstr="";
        String line= "";
        String title="";
        String produrl="";
        String imgurl="";
        String resultSet="";
        Bundle bundle=new Bundle();
        @Override
        protected Bundle doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
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


              /*  handler.post(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Too many attempts on amazon tab, hold your horses", Toast.LENGTH_LONG).show();

                    }
                });*/


                e.printStackTrace();
            }catch (IOException e) {
                //Toast.makeText(MainActivity.this,
                //      "Connection IOException", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            try {
                // we use document builder to extract data from xml

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(jsonstr)));
                //everything in xml is a node so we create nodes for tags
                // then use the attribute of data which we want to extract

                NodeList nList = document.getElementsByTagName("Item");
                NodeList nList2 = document.getElementsByTagName("LargeImage");
                NodeList nList3 = document.getElementsByTagName("OfferSummary");
                NodeList nList4 = document.getElementsByTagName("ItemAttributes");

                //for multiple elements use for loop
                for(int i=0;i<nList.getLength();i++) {
                    Node node = nList.item(i);
                    Node nodeimage=nList2.item(i);
                    Node nodeprice=nList3.item(i);
                    Node nodespecs=nList4.item(i);

                  //  ProductsAma products=new ProductsAma();
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        Element element5 = (Element) nodespecs;
                        NodeList featurelist = element5.getElementsByTagName("Feature");

                       // for(int j=0;j<featurelist.getLength() && j<4 ;j++){
                         //   products.specs[j]=getValue("Feature",element5, j);
                      //  }
                        Element element3 = (Element) nodeimage;
                        Element element4 = (Element) nodeprice;
                       // products.title=getValue("Title",element2, 0);
                       // if(title.equals(getValue("Title",element2, 0))){
                       bundle.putString("title",getValue("Title",element2, 0));
                        bundle.putString("price",getValue("FormattedPrice",element4, 0));

                      //  products.price=getValue("FormattedPrice",element4, 0);
                       // products.produrl=getValue("DetailPageURL",element2, 0);
                       // products.imgurl=getValue("URL",element3, 0);

                        //getValue() is method defined in the end which passes the attribute and return data that we want
                        //resultSet=resultSet+"\n\n\tTitle: "+title+"\n\tprice: "+price+"\n\tproduct url :"+produrl+"\n\timage url :"+imgurl;
                        //data.add(products);
                    }
                    //}
                }
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }

            return bundle;

        }



        @Override
        protected void onPostExecute(Bundle s) {
            super.onPostExecute(s);
            //tvList.setText(s);
            String text=s.getString("title");
            tvAmazon.setText(text+" : "+s.getString("price"));
            ettest.setText(text);
            //recyclerView.setLayoutManager(new VegaLayoutManager());
         /*   adapterProducts=new AdapterProductsAma(getActivity(),data);
            recyclerView.setOnFlingListener(null);
            if(getActivity()!=null){
                adapterProducts.notifyDataSetChanged();
                recyclerView.setAdapter(adapterProducts);

            }
            pb.setVisibility(view.GONE);
            recyclerView.setVisibility(view.VISIBLE);

            recyclerView.invalidate(); */

        }
    }
    private static String getValue(String tag, Element element, int i) {
        if (element.getElementsByTagName(tag).item(i).hasChildNodes()) {
            NodeList nodeList = element.getElementsByTagName(tag).item(i).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        }
        return null;
    }





}
