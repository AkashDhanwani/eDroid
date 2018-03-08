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
import android.widget.TextView;
import android.widget.Toast;

import com.stone.vega.library.VegaLayoutManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@SuppressLint("ValidFragment")
public class AmazonFragment extends Fragment {
CharSequence query;
View view,view2;

    String requestUrl = null, product;
    Button btnSearch,btnNext;
    TextView tvList;
    int i;
    SignedRequestsHelper helper;// We have to use this java file for encoding purpose
    private RecyclerView recyclerView;
    private AdapterProductsAma adapterProducts;
    public List<ProductsAma> data=new ArrayList<>();


    /*The below three variables are used for the authentication purpose by passing it to
        the signed request helper class
    */
    // This the id unique to my Amazon account can be considered same as flipkart
    private static final String ACCESS_KEY_ID = "AKIAJREHRMEIPF5AWDRQ";

    // The amazon use a extra variable along with the ID that is secret key
    private static final String SECRET_KEY = "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";

    // This is the endpoint from where we will fetch the data (.in or .com)
    private static final String ENDPOINT = "webservices.amazon.in";


    @SuppressLint("ValidFragment")
    public AmazonFragment(CharSequence query1) {
        query=query1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_amazon,container,false);

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, ACCESS_KEY_ID, SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

                product = query.toString();
                i=1;
                callPages(i,product);
            /*

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product =query.toString();
                i++;
                callPages(i,product);
            }

        });*/


        return view;
    }


    private void callPages(int i, String product) {



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
        params.put("ItemPage", String.valueOf(i)); // increment this for second page
        params.put("ResponseGroup", "Images,ItemAttributes,Offers");
        params.put("Keywords", product);

        // the purpose of the signed class used above is to get the signed url
        requestUrl = helper.sign(params);

        Task1 t1 = new Task1();
        t1.execute(requestUrl);
    }

    class Task1 extends AsyncTask<String,Void,String>
    {
        String jsonstr="";
        String line= "";
        String title="";
        String produrl="";
        String imgurl="";
        String resultSet="";
        String price;
        @Override
        protected String doInBackground(String... strings) {
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
                Toast.makeText(getActivity(), "URL Malformed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
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
                NodeList nList2 = document.getElementsByTagName("MediumImage");

                //for multiple elements use for loop
                for(int i=0;i<nList.getLength();i++) {
                    Node node = nList.item(i);
                    Node nodeimage=nList2.item(i);
                    ProductsAma products=new ProductsAma();
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        Element element3 = (Element) nodeimage;
                        products.title=getValue("Title",element2);
                        products.price=getValue("FormattedPrice",element2);
                        products.produrl=getValue("DetailPageURL",element2);
                        products.imgurl=getValue("URL",element3);

                        //getValue() is method defined in the end which passes the attribute and return data that we want
                        //resultSet=resultSet+"\n\n\tTitle: "+title+"\n\tprice: "+price+"\n\tproduct url :"+produrl+"\n\timage url :"+imgurl;
                        data.add(products);
                    }
                }
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }

            return resultSet;

        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //tvList.setText(s);
            recyclerView=view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new VegaLayoutManager());
            adapterProducts=new AdapterProductsAma(getActivity(),data);
            adapterProducts.notifyDataSetChanged();
            recyclerView.invalidate();

            recyclerView.setAdapter(adapterProducts);
        }
    }
    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
