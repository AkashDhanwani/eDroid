package com.fc.project.edroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;
    public MyDatabaseHelper(Context context) {
        super(context,"OurDatabase",null,1);
        this.context = context;
        db= this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table bookmark(title text PRIMARY KEY)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    public void addBookmark(String title) {

        ContentValues values=new ContentValues();
        values.put("title",title);
        long rid= db.insert("bookmark",null,values);
        if(rid<0){
            Toast.makeText(context, "Insert issue", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(context, "Insert Successful", Toast.LENGTH_SHORT).show();



    }

    public ArrayList getAllbookmark() {
        Cursor cursor=db.query("bookmark",null,null,null,null,null,null,null);
        ArrayList<String> bms=new ArrayList<>();
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            do{
                String name=cursor.getString(0);
                bms.add(name+"\n");

            }while(cursor.moveToNext());
        }
        else bms.add("No Records");
        return bms;
    }

    public void delBookmark(String title) {
        String sql = "delete from bookmark where title=\'"+title+"\'";
        db.execSQL(sql);
        /*long rid=db.delete("bookmark","title='"+title+"'",null);
        if(rid<0){
            Toast.makeText(context, "delelte issue", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(context, ""+rid+" rows deleted", Toast.LENGTH_SHORT).show();
*/
    }
}

