package com.fc.project.edroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import br.com.bloder.magic.view.MagicButton;

public class AuthActivity extends AppCompatActivity {

    MagicButton magicButton;
    ActionProcessButton btnprocess;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // private MyPageAdapter myPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
//        tabLayout = findViewById(R.id.tablayout);
//        viewPager = findViewById(R.id.pager);
        //viewPager.setAdapter(myPageAdapter);
        btnprocess = findViewById(R.id.FireBaseSignUp);
//        btnprocess.setMode(ActionProcessButton.Mode.ENDLESS);
   //     btnprocess.setProgress(0);
        // magicButton=findViewById(R.id.googleSignUp);
//        magicButton.setMagicButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

    public void tryBtn(View view) {
        ActionProcessButton btn = (ActionProcessButton) view;
        // we add 25 in the button progress each click
        if (btn.getProgress() < 100) {
            btn.setProgress(btn.getProgress() + 15);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(4000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    Intent intent=new Intent(getApplicationContext(),SearchWithImageSliderActivity.class);
//                    Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
//                    startActivity(intent);
                }
            }).start();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();

        }
    }

    public void login(View view) {
        ActionProcessButton btn = (ActionProcessButton) view;
        // we add 25 in the button progress each click
        if (btn.getProgress() < 100) {
            btn.setProgress(btn.getProgress() + 15);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
    }

    public void Googlelogin(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Toast.makeText(AuthActivity.this, "Creating Account...", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}
//    public static class MyFragment extends Fragment
//    {
//        public MyFragment()
//        {}
//
//
//        @Override
//        public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
//           // return super.onCreateView(inflater, container, savedInstanceState);
//           // return inflater.inflate(R.layout.tab1signup,container,false)
//            TextView textView=new TextView(getActivity());
//            textView.setText("hello world!");
//            textView.setGravity(Gravity.CENTER);
//            return textView;
//        }
//    }
//
//    private class MyPageAdapter extends FragmentStatePagerAdapter{
//
//        public MyPageAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            AuthActivity.MyFragment myFragment=new AuthActivity.MyFragment();
//            return myFragment;
//        }
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);
//        }
