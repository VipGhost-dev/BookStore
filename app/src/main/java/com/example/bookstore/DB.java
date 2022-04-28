package com.example.bookstore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB  extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BStore";
    public static final String TABLE_BOOKS = "books";
    public static final String TABLE_USERS = "user";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_PRICE = "price";

    public static final String KEY_ID1 = "_id";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_ID2 = "_id";
    public static final String KEY_ADRESS = "adress";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_TIME = "time";


    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_BOOKS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text,"
                + KEY_AUTHOR + " text," + KEY_PRICE + " text"+")");
        db.execSQL("create table " + TABLE_USERS + "(" + KEY_ID1
                + " integer primary key," + KEY_LOGIN + " text,"
                + KEY_PASSWORD + " text"+")");
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID2
                + " integer primary key," + KEY_ADRESS + " text,"
                + KEY_PHONE + " text," + KEY_TIME + " text"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        db.execSQL("drop table if exists " + TABLE_USERS);
        db.execSQL("drop table if exists " + TABLE_BOOKS);
        onCreate(db);
    }
}
