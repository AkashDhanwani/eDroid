package com.fc.project.edroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

    Context context;
    String[][] specification;

    ListAdapter (Context localContext, String[][] s)
    {
        context = localContext;
        specification = s;
    }

    @Override
    public int getCount() {
        return specification.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null) {
            //Only creates new view when recycling isn't possible
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listcontent, null);
        }

        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);

        tv1.setText(specification[i][0]);
        tv2.setText(specification[i][1]);
        return view;
    }
}