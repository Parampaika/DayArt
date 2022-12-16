package com.example.myapplication.bd;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public void Insert_toDb(String url, String name, String author, String disc, String like){
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.URL, url);
        cv.put(MyConstants.NAME, name);
        cv.put(MyConstants.AUTHOR, author);
        cv.put(MyConstants.DISC, disc);
        cv.put(MyConstants.LIKE, like);
        db.insert(MyConstants.TABLE_NAME, null, cv);
    }

    public void DeleteStringFromDb(String name){
        db.delete(MyConstants.TABLE_NAME, MyConstants.NAME + " = ?", new String[]{name});
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

    public List<String> getStringByName(String name){
        List<String> urls = new ArrayList<>();
        String selection =  MyConstants.NAME + " = ?";
        String[] selectionArgs = { name };
        Cursor cursor = db.query(
                MyConstants.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        while(cursor.moveToNext()) {
            @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(MyConstants.URL));
            urls.add(url);
        }
        cursor.close();
        return urls;
    }

    public Integer getSizeBD(String name){
        Integer size = 0;
        Cursor cursor = db.query(
                name,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        while(cursor.moveToNext()) {
            size++;
        }
        cursor.close();
        return size;
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
                @SuppressLint("Range") String likes = cursor.getString(cursor.getColumnIndex(MyConstants.LIKE));
                urls.add(likes);
            }
        }
        cursor.close();
        return urls;
    }


    public void update(String url, String name, String author, String disc, String like) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.URL, url);
        cv.put(MyConstants.NAME, name);
        cv.put(MyConstants.AUTHOR, author);
        cv.put(MyConstants.DISC, disc);
        cv.put(MyConstants.LIKE, like);
        // обновляем по id
        db.update(MyConstants.TABLE_NAME, cv, MyConstants.URL + " = ?", new String[] { url });
    }

    public void CloseDB(){
        myDbHelper.close();
    }

    public String getDbPath(Context context,String YourDbName)
    {
        return context.getDatabasePath(YourDbName).getAbsolutePath();
    }


    public void Insert_toAuthors(String author, String years, String photo, String biogr){
        List<String> authors = getOneStringFromDB_AUTHORS(author);
        if (authors.get(0) == "") {
            ContentValues cv = new ContentValues();
            cv.put(MyConstants.AUTHOR, author);
            cv.put(MyConstants.YEARS, years);
            cv.put(MyConstants.PHOTO, photo);
            cv.put(MyConstants.BIOGR, biogr);
            db.insert(MyConstants.TABLE_NAME_2, null, cv);
        }
    }

    public List<String> getFavorites(String Index){
        List<String> urls = new ArrayList<>();
        String selection =  MyConstants.LIKE + " = ?";
        String[] selectionArgs = { "1" };
        Cursor cursor = db.query(
                MyConstants.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
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


    public List<String> getOneStringFromDB_AUTHORS(String Author){
        List<String> urls = new ArrayList<>();
        String selection =  MyConstants.AUTHOR + " = ?";
        String[] selectionArgs = { Author };
        Cursor cursor = db.query(
                MyConstants.TABLE_NAME_2,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        while(cursor.moveToNext()) {
            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(MyConstants.AUTHOR));
            urls.add(author);
            @SuppressLint("Range") String years = cursor.getString(cursor.getColumnIndex(MyConstants.YEARS));
            urls.add(years);
            @SuppressLint("Range") String photo = cursor.getString(cursor.getColumnIndex(MyConstants.PHOTO));
            urls.add(photo);
            @SuppressLint("Range") String biogr = cursor.getString(cursor.getColumnIndex(MyConstants.BIOGR));
            urls.add(biogr);
        }
        urls.add("");
        cursor.close();
        return urls;
    }

    public void DeleteStringFromDb_Author(String name){
        db.delete(MyConstants.TABLE_NAME_2, MyConstants.AUTHOR + " = ?", new String[]{name});
    }

}
