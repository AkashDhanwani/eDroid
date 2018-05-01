package com.fc.project.edroid;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class Spec_Compare extends AppCompatActivity {

    EditText editText, editText1;
    Button button, button1;
    TextView textView, textView1;
    public ListView lv1, lv2;

    public String device, device1;
    public String[][] spec = new String[100][10];
    public String[][] spec1 = new String[100][10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec__compare);

        int orientaton = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientaton);


        button= findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        // textView = findViewById(R.id.textView);
        lv1 = findViewById(R.id.lv1);

        button1= findViewById(R.id.button1);
        editText1 = findViewById(R.id.editText1);
        //textView1 = findViewById(R.id.textView1);
        lv2 = findViewById(R.id.lv2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device = editText.getText().toString();

                Task1 t1 = new Task1();
                t1.execute("https://fonoapi.freshpixl.com/v1/getdevice");
                //curl https://fonoapi.freshpixl.com/v1/getdevice -XPOST
                // -H'Accept: application/json' -d 'token=YOUR_TOKEN_HERE&limit=5&device=A8'
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device1 = editText1.getText().toString();

                Task2 t2 = new Task2();
                t2.execute("https://fonoapi.freshpixl.com/v1/getdevice");
            }
        });
    }

    class Task1 extends AsyncTask<String, Void, String[][]>
    {
        String jsonstr="";
        String line= "";

        @Override
        protected String[][] doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");

                String data = "token=fd98b6513c41ee09af2426279745c90bd58be90e1b292511&limit=5&device="+device;
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(data);
                out.close();

                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null)
                {
                    jsonstr+=line +"\n";
                }
            } catch (MalformedURLException e) {
                Toast.makeText(Spec_Compare.this, "URL Malformed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(jsonstr != null)
            {
                try {
                    JSONArray jsonArray = new JSONArray(jsonstr);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    Iterator<String> name = jsonObject.keys();
                    int n = 0;
                    while (name.hasNext()) {
                        String key = name.next();
                        spec[n][0] = key;
                        spec[n][1] = jsonObject.getString(key);
                        n++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    spec = new String[100][10];
                    spec[0][0] = "No results found";
                    spec[1][0] = "Please try with proper words";
                    spec[2][0] = "Or matching with proper cases";
                }
            }
            return spec;
        }
        @Override
        protected void onPostExecute(String[][] s) {
            super.onPostExecute(s);

            lv1.setAdapter(new ListAdapter(Spec_Compare.this ,s));

        }
    }

    class Task2 extends AsyncTask<String, Void, String[][]>
    {
        String jsonstr="";
        String line= "";
        String message= "";


        @Override
        protected String[][] doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");

                String data = "token=fd98b6513c41ee09af2426279745c90bd58be90e1b292511&limit=5&device="+device1;
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(data);
                out.close();

                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null)
                {
                    jsonstr+=line +"\n";
                }
            } catch (MalformedURLException e) {
                Toast.makeText(Spec_Compare.this, "URL Malformed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(jsonstr != null)
            {
                try {
                    JSONArray jsonArray = new JSONArray(jsonstr);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    Iterator<String> name = jsonObject.keys();
                    int n = 0;
                    while (name.hasNext()) {
                        String key = name.next();
                        spec1[n][0] = key;
                        spec1[n][1] = jsonObject.getString(key);
                        n++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    spec1 = new String[100][10];
                    spec1[0][0] = "No results found";
                    spec1[1][0] = "Please try with proper words";
                    spec1[2][0] = "Or matching with proper cases";
                }
            }

            return spec1;
        }
        @Override
        protected void onPostExecute(String[][] s) {
            super.onPostExecute(s);

            lv2.setAdapter(new ListAdapter(Spec_Compare.this ,s));

        }
    }

}
