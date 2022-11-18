package com.example.myapplication.bd;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyBdMeneger {
    private final MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyBdMeneger(Context context) {
        myDbHelper = new MyDbHelper(context);
    }
    public void Open_db (){
        db = myDbHelper.getWritableDatabase();
    }
    public void Insert_toDb(String url, String name, String author, String disc){
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.URL, url);
        cv.put(MyConstants.NAME, name);
        cv.put(MyConstants.AUTHOR, author);
        cv.put(MyConstants.DISC, disc);
        db.insert(MyConstants.TABLE_NAME, null, cv);
    }

    public void DeleteStringFromDb(String url){
        db.delete(MyConstants.TABLE_NAME, MyConstants.URL + " = ?", new String[]{url});
    }

    public List<String> getALLURLFromDB(String Index){
        List<String> urls = new ArrayList<>();
        Cursor cursor = db.query(
               MyConstants.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        while(cursor.moveToNext()) {
            @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(Index));
            urls.add(url);
        }
        cursor.close();
        return urls;
    }

    public List<String> getOneStringFromDB(Integer numberOfString){
        Integer count = 0;
        List<String> urls = new ArrayList<>();
        Cursor cursor = db.query(
                MyConstants.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        while(cursor.moveToNext()) {
            count++;
            if(count.equals(numberOfString)){
                @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(MyConstants.URL));
                urls.add(url);
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MyConstants.NAME));
                urls.add(name);
                @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(MyConstants.AUTHOR));
                urls.add(author);
                @SuppressLint("Range") String disc = cursor.getString(cursor.getColumnIndex(MyConstants.DISC));
                urls.add(disc);
            }
        }
        cursor.close();
        return urls;
    }

    public void CloseDB(){
        myDbHelper.close();
    }
}
