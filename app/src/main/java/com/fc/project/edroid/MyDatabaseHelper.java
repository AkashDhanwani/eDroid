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
    private static MyDatabaseHelper mInstance = null;

    public static MyDatabaseHelper getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new MyDatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private MyDatabaseHelper(Context ctx) {
        super(ctx, "OurDatabase", null, 1);
        this.context = ctx;
    }
    /*
    public MyDatabaseHelper(Context context) {
        super(context,"OurDatabase",null,1);
        this.context = context;
        db= this.getWritableDatabase();
    }*/


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table bookmark(title text PRIMARY KEY, dat text, store text, price text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    public void addBookmark(String title, String dat, String store, String price) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("dat", dat);
        values.put("store", store);
        values.put("price", price);
        long rid = db.insert("bookmark", null, values);
        if (rid < 0) {
            Toast.makeText(context, "Insert issue!Product might be bookmared!", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "Insert Successful", Toast.LENGTH_SHORT).show();
        db.close();


    }

    public ArrayList getAllbookmark() {
        db = this.getWritableDatabase();
        Cursor cursor = db.query("bookmark", null, null, null, null, null, null, null);
        ArrayList<String> bms = new ArrayList<>();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                String name = cursor.getString(0);
                String date = cursor.getString(1);
                String store = cursor.getString(2);
                String price = cursor.getString(3);
                bms.add(name + "\n\n" + "Bookmarked at: " + date + "\nFrom "+store+" at "+price+"\n");

            } while (cursor.moveToNext());
        } else bms.add("No Records");
        cursor.close();
        db.close();
        return bms;
    }

    public void delBookmark(String title) {
        db = this.getWritableDatabase();
        title = title.replace("\'", "\'\'");
        long rid = db.delete("bookmark", "title=\'" + title + "\'", null);
        if (rid == 0) {
            Toast.makeText(context, "delelte issue", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "" + rid + " rows deleted", Toast.LENGTH_SHORT).show();
        db.close();
    }

//    public String getDate(){
//        db = this.getWritableDatabase();
//        Cursor cursor=db.query("bookmark",null,null,null,null,null,null,null);
//        cursor.moveToFirst();
//        if(cursor.getCount()>0){
//            do{
//                String date=cursor.getString(1);
//            }while(cursor.moveToNext());
//        }
//        else bms.add("No Records");
//        cursor.close();
//        db.close();
//        return bms;
//    }
}

