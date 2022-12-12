package com.example.myapplication.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(@Nullable Context context) {
        super(context, MyConstants.DB_NAME, null, MyConstants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MyConstants.TABLE_STRUCTURE);
        sqLiteDatabase.execSQL(MyConstants.TABLE_STRUCTURE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(MyConstants.Delete_TABLE);
        onCreate(sqLiteDatabase);
    }
}
