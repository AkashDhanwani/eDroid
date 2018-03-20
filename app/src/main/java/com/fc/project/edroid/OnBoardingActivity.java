package com.fc.project.edroid;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_on_boardng);

        AhoyOnboarderCard card1=new AhoyOnboarderCard("Welcome User!","We are thrilled to have you on our service.", R.drawable.logo2);
        AhoyOnboarderCard card2=new AhoyOnboarderCard("Compare","Compare Prices of your Favourite products with ease", R.drawable.comapre);
        AhoyOnboarderCard card3=new AhoyOnboarderCard("Bookmark","Bookmark your products to keep a track on them.", R.drawable.bookmark);
        AhoyOnboarderCard card4=new AhoyOnboarderCard("A little reminder","Always keep your wifi or mobile data on and now you are all set to go :)", R.drawable.wfi);

        card1.setBackgroundColor(R.color.black_transparent);
        card2.setBackgroundColor(R.color.black_transparent);
        card3.setBackgroundColor(R.color.black_transparent);
        card4.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> cards=new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);

        for (AhoyOnboarderCard card: cards)
        {
            card.setTitleColor(R.color.white);
            card.setDescriptionColor(R.color.grey_200);
        }
        setFinishButtonTitle("Finish");
        setGradientBackground();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        }
        setOnboardPages(cards);
    }

    @Override
    public void onFinishButtonPressed() {
        Intent intent=new Intent(getApplicationContext(),AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
