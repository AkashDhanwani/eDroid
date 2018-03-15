package com.fc.project.edroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        EasySplashScreen config=new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(AuthActivity.class)
                .withSplashTimeOut(3000)
//                .withBackgroundColor(Color.parseColor("#24898e"))
                .withBackgroundResource(R.drawable.new_login_gradient)
                .withLogo(R.drawable.logo2)
                .withHeaderText("")
                .withFooterText("Created by FC developers")
                .withBeforeLogoText("Hello Customer")
                .withAfterLogoText("All set to go inside");


        //setting the text color
        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);


        //setting to view

        View view=config.create();

        //settng view to content view
        setContentView(view);

    }
}