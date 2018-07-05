package com.enna1.dotanews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;


public class NewsUtils {
    public static ArrayList<NewsBean> getNetNews(Context context, String urlString){
        ArrayList<NewsBean> newsArrayList = new ArrayList<NewsBean>();
        try{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                String res = StreamUtils.convertStream(is);

                JSONObject root_json = new JSONObject(res);
                JSONArray newsList = root_json.getJSONArray("result");
                int newsListHash = 415201;//d:4, o:15, t:20, a:1
                for (int i = 0; i < newsList .length(); i ++ ){
                    JSONObject news_json = newsList.getJSONObject(i);
                    NewsBean newsBean = new NewsBean();
                    newsBean.setNewsId(news_json.getInt("newsid"));
                    newsBean.setDate(news_json.getString("date"));
                    newsBean.setTitle(news_json.getString("title"));
                    newsBean.setCoverImgUrl(news_json.getJSONArray("imgs").getString(0));
                    newsBean.setNewsUrl(news_json.getString("newUrl"));
                    newsArrayList.add(newsBean);
                    newsListHash ^= news_json.getInt("newsid");
                }

                int last_hash = getNewsListHash(context);
                //Log.e("res_hash", Integer.toString(newsListHash));
                //Log.e("db_hash", Integer.toString(last_hash));
                if(newsListHash == last_hash) {
                    newsArrayList = getDBNews(context);
                }
                else {
                    new NewsDBUtils(context).saveNews(newsArrayList);
                    new NewsDBUtils(context).updateNewsListHash(newsListHash);
                }
                saveCache(context,newsArrayList);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Collections.sort(newsArrayList, new NewsListComparator());
        return  newsArrayList;
    }

    public static ArrayList<NewsBean> getDBNews(Context context){
        return new NewsDBUtils(context).getNews();
    }

    public static int getNewsListHash(Context context) {
        return new NewsDBUtils(context).getNewsListHash();
    }
    public static void saveCache(Context context, ArrayList<NewsBean> arrayList){
        for (NewsBean newsBean: arrayList) {
            String news_id = Integer.toString(newsBean.getNewsId());
            String cache_path = context.getExternalCacheDir().getPath() + File.separator +
                    news_id + File.separator;
            String bitmap_file_name = news_id + "_cover_img";
            File cache_path_dir = new File(cache_path);
            if (cache_path_dir.exists())
                continue;
            try {
                URL url = new URL(newsBean.getCoverImgUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                if (conn.getResponseCode() == 200) {
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    //Log.e("NewsUtils", "saveCoverImg");
                    cache_path_dir.mkdir();
                    File cover_img_file = new File(cache_path + bitmap_file_name);
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(cover_img_file));
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    bos.flush();
                    bos.close();
                    //Log.e("NewsUtils", "saveNewsContent");
                    //todo saveNewsContent
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}