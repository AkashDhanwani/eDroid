package com.fc.project.edroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.AdapterView.*;

public class bookmark extends AppCompatActivity {
    ListView lvbm;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        lvbm=findViewById(R.id.lvbm);

        final MyDatabaseHelper dbh=MyDatabaseHelper.getInstance(this);
         adapter=new ArrayAdapter(bookmark.this,android.R.layout.simple_list_item_1,dbh.getAllbookmark());
        lvbm.setAdapter(adapter);
        lvbm.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFromList = (String) lvbm.getItemAtPosition(i);
                  selectedFromList = selectedFromList.substring(0, selectedFromList.length() - 1);

                Intent replyIntent = new Intent();
                replyIntent.putExtra("title", selectedFromList);
                setResult(RESULT_OK, replyIntent);
                finish();

            }
        });

        lvbm.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
            {
                String title= (String)lvbm.getItemAtPosition(pos);
                String title1= title.substring(0, title.length() - 1);
                Toast.makeText(bookmark.this, title, Toast.LENGTH_SHORT).show();
               dbh.delBookmark(title1);
                adapter.remove(title);
                adapter.notifyDataSetChanged();
            return true;
            }
        });

    }


}
