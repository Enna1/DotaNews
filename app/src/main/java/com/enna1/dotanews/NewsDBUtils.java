package com.enna1.dotanews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NewsDBUtils {
    private NewsDBHelper dbHelper;

    public NewsDBUtils(Context context){
        dbHelper = new NewsDBHelper(context);
    }

    public void saveNews(ArrayList<NewsBean> arrayList){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        for (NewsBean newsBean: arrayList){
            ContentValues val = new ContentValues();
            val.put("_id", newsBean.getNewsId());
            val.put("date", newsBean.getDate());
            val.put("title", newsBean.getTitle());
            val.put("cover_img_url", newsBean.getCoverImgUrl());
            val.put("news_url", newsBean.getNewsUrl());
            sqLiteDatabase.insert("DotaNews", null, val);
        }
        sqLiteDatabase.close();
    }

    public void deleteNews(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        sqLiteDatabase.delete("DotaNews", null, null);
        sqLiteDatabase.close();
    }

    public ArrayList<NewsBean> getNews(){
        ArrayList<NewsBean> arrayList = new ArrayList<NewsBean>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("DotaNews", null,null,
                null,null, null, null, null);
        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                NewsBean newsBean = new NewsBean();
                newsBean.setNewsId(cursor.getInt(0));
                newsBean.setDate(cursor.getString(1));
                newsBean.setTitle(cursor.getString(2));
                newsBean.setCoverImgUrl(cursor.getString(3));
                newsBean.setNewsUrl(cursor.getString(4));
                arrayList.add(newsBean);
            }
        }
        return arrayList;
    }

    public int getNewsListHash(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("NewsHash", null,null,
                null,null, null, null, "1");
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToNext();
            return cursor.getInt(1);
        }
        //this should not exec, the NewsHash table at least has one init row
        return 415201;
    }

    public void updateNewsListHash(int hash){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("hash", hash);
        sqLiteDatabase.update("NewsHash", val,null, null);
        sqLiteDatabase.close();
    }
}
