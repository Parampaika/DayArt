package com.example.myapplication.bd;

public class MyConstants {
    public static final String TABLE_NAME = "Pictures";
    public static final String _id = "_id";
    public static final String URL = "url";
    public static final String NAME = "name";
    public static final String AUTHOR = "author";
    public static final String DISC = "disk";

    public static final int VERSION = 1;
    public static final String DB_NAME = "DailyArt.db";

    public static final String TABLE_STRUCTURE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    URL + " TEXT  PRIMARY KEY," +
                    NAME + " TEXT," +
                    AUTHOR + " TEXT," +
                    DISC + " TEXT)";
    public static final String Delete_TABLE = "DELETE IF EXISTS " + TABLE_NAME;


}
