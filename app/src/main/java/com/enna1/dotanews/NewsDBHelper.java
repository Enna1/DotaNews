package com.enna1.dotanews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDBHelper extends SQLiteOpenHelper{

    public NewsDBHelper(Context context){
        super(context, "DotaNews", null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table DotaNews(_id integer primary key, date varchar(50), " +
                "title varchar(100), cover_img_url varchar(200), news_url varchar(200))");
        sqLiteDatabase.execSQL("create table NewsHash(_id integer primary key autoincrement, hash integer)");
        sqLiteDatabase.execSQL("insert into NewsHash(hash) values(415201)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
