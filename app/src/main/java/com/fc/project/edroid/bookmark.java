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

import static android.widget.AdapterView.*;

public class bookmark extends AppCompatActivity {
    ListView lvbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        lvbm=findViewById(R.id.lvbm);

        final MyDatabaseHelper dbh=new MyDatabaseHelper(this);
         ArrayAdapter adapter=new ArrayAdapter(bookmark.this,android.R.layout.simple_list_item_1,dbh.getAllbookmark());
        lvbm.setAdapter(adapter);
        lvbm.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFromList = (String) lvbm.getItemAtPosition(i);
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
            dbh.delBookmark((String) lvbm.getItemAtPosition(pos));
                ArrayAdapter adapter=new ArrayAdapter(bookmark.this,android.R.layout.simple_list_item_1,dbh.getAllbookmark());
                lvbm.setAdapter(adapter);
            return true;
            }
        });
    }


}
