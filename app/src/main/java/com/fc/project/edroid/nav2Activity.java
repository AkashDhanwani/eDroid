package com.fc.project.edroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.firebase.auth.FirebaseAuth;

public class nav2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager viewPager;
    Button btnSearch;
    //EditText etsearch;
    FloatingSearchView etsearch;
    String query;
    FirebaseAuth mAuth;
    EbayFragment ebayFragment;
    FlipkartFragment flipkartFragment;
    AmazonFragment amazonFragment;
    DatayugeFragment datayugeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        int orientaton = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientaton);

        // btnSearch=findViewById(R.id.btnsearch);
       etsearch=findViewById(R.id.Etsearch);
        setSupportActionBar(toolbar);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        mAuth=FirebaseAuth.getInstance();
        mSectionsPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
//        int defaultValue = 0;
//        int page = getIntent().getIntExtra("One", defaultValue);
//        viewPager.setCurrentItem(page);

        final MyDatabaseHelper dbh=MyDatabaseHelper.getInstance(this);


        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       etsearch.attachNavigationDrawerToMenuButton(drawer);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);

        View headerView = navigationView.getHeaderView(0);
        headerView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);

        query=etsearch.getQuery();
        Intent myIntent = getIntent();
        if (myIntent.hasExtra("myExtra")){
            query=myIntent.getStringExtra("myExtra");
            etsearch.setSearchText(query);
        }
            viewPager.setOffscreenPageLimit(4);

        etsearch.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                query=etsearch.getQuery();
                if(!query.equals("")) {
                    flipkartFragment.refresh(query);
                    amazonFragment.refresh(query);
                    ebayFragment.refresh(query);
                   datayugeFragment.refresh(query);
                    Toast.makeText(nav2Activity.this, "Searching for " + query, Toast.LENGTH_SHORT).show();
                }
            }
        });

//            btnSearch.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    query=etsearch.getQuery();
//                    if(!query.equals("")) {
//                        flipkartFragment.refresh(query);
//                        amazonFragment.refresh(query);
//                        ebayFragment.refresh(query);
//                        datayugeFragment.refresh(query);
//                        Toast.makeText(nav2Activity.this, "Searching for " + query, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
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
        getMenuInflater().inflate(R.menu.nav2, menu);
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
//
////            return true;
//            Intent intent=new Intent(getApplicationContext(),ForgotPasswordActivity.class);
//            startActivity(intent);
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
//            Intent intent=new Intent(getApplicationContext(),ForgotPasswordActivity.class);
//            startActivity(intent);
//            finish();
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {

        //}
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
         else if (id == R.id.nav_bookmark) {
             Intent intent=new Intent(nav2Activity.this,bookmark.class);
             startActivityForResult(intent, 1 );

         }
        else if(id==R.id.nav_logout)
         {
             mAuth.signOut();
             Intent intent=new Intent(getApplicationContext(),AuthActivity.class);
             startActivity(intent);
             finish();
         }
         else if(id == R.id.nav_compare){
             Intent intent = new Intent(nav2Activity.this, Spec_Compare.class);
             startActivity(intent);
         }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        String titles[]=new String[]{"flipkart","amazon","ebay","others"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    flipkartFragment=new FlipkartFragment(query);
                    return flipkartFragment;
                case 1:
                    amazonFragment=new AmazonFragment(query);
                    return amazonFragment;
                case 2:
                    ebayFragment=new EbayFragment(query);
                    return ebayFragment;
                case 3:
                    datayugeFragment=new DatayugeFragment(query);
                    return datayugeFragment;

            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String query = data.getStringExtra("title");
            etsearch.setSearchText(query);
            flipkartFragment.refresh(query);
            amazonFragment.refresh(query);
            ebayFragment.refresh(query);
            datayugeFragment.refresh(query);

}
    }

    }
