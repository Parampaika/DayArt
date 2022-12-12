package com.example.myapplication.bd;

public class MyConstants {
    public static final String TABLE_NAME = "Pictures";
    public static final String TABLE_NAME_2 = "Authors";
    public static final String _id = "_id";
    public static final String URL = "url";
    public static final String NAME = "name";
    public static final String AUTHOR = "author";
    public static final String DISC = "disk";
    public static final String LIKE = "like";

    public static final String BIOGR = "biography";
    public static final String YEARS = "years_of_life";
    public static final String PHOTO = "photo_of_author";

    public static final int VERSION = 4;
    public static final String DB_NAME = "DailyArt.db";

    public static final String TABLE_STRUCTURE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _id + " INTEGER PRIMARY KEY," +
                    URL + " TEXT," +
                    NAME + " TEXT," +
                    AUTHOR + " TEXT," +
                    DISC + " TEXT, " +
                    LIKE + " TEXT)";

    public static final String TABLE_STRUCTURE_2 =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_2 + " (" +
                    _id + " INTEGER PRIMARY KEY," +
                    AUTHOR + " TEXT," +
                    YEARS + " TEXT," +
                    PHOTO + " TEXT," +
                    BIOGR + " TEXT)";

    public static final String Delete_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}