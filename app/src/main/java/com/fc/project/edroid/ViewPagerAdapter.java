package com.fc.project.edroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

class ViewPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    Integer[] images={R.drawable.amazon,R.drawable.ebay,R.drawable.flipkart,R.drawable.shopclues,R.drawable.tatacliq};


    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom,null);
        ImageView imageView=view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        ViewPager viewPager=(ViewPager) container;
        viewPager.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager viewPager=(ViewPager) container;
        View view=(View) object;
        viewPager.removeView(view);

    }
}