package com.fc.project.edroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Disclaimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
    }

    public void amazon(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amazon.in")));
    }
}
