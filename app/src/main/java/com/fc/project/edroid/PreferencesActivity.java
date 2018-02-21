package com.fc.project.edroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_preferences);

        //if(sharedPreferences)
        sharedPreferences=getSharedPreferences("prefs",0);
        boolean first=sharedPreferences.getBoolean("firstTime",false);
        if(first==false)
        {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("firstTime",true);
            editor.commit();
            Intent intent=new Intent(getApplicationContext(),OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent1=new Intent(getApplicationContext(),SplashActivity.class);
            startActivity(intent1);
            finish();
        }
    }
}
//TODO add handling in splash activity to avoid crashing of app if internet is not on
