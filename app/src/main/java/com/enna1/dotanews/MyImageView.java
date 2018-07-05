package com.enna1.dotanews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Handler;
import android.util.Log;

public class MyImageView extends AppCompatImageView{
    private Context context_;
    public MyImageView(Context context){
        super(context);
        context_ = context;
    }
    public MyImageView(Context context, AttributeSet attrs){
        super(context, attrs);
        context_ = context;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg){
            Bitmap bitmap = (Bitmap) msg.obj;
            MyImageView.this.setImageBitmap(bitmap);
        };
    };

    public void setImageUrl(final int newsID, final String urlString){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String news_id = Integer.toString(newsID);
                String cache_path = context_.getExternalCacheDir().getPath() + "/" +
                        news_id + "/";
                String bitmap_file_name = news_id + "_cover_img";
                if (new File(cache_path + bitmap_file_name).exists()){
                    Log.e("MyImageView", "Load StoredCoverImg");
                    Bitmap bitmap = BitmapFactory.decodeFile(cache_path + bitmap_file_name,null);
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    handler.sendMessage(message);
                }
                else {
                    try {
                        URL url = new URL(urlString);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5*1000);
                        if (conn.getResponseCode() == 200){
                            InputStream is = conn.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            Message message = Message.obtain();
                            message.obj = bitmap;
                            handler.sendMessage(message);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
