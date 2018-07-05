package com.enna1.dotanews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private Context context_;
    List<NewsBean> newsBeanList;
    ListView listView;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            newsBeanList = (List<NewsBean>)msg.obj;
            NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, newsBeanList);
            listView.setAdapter(newsAdapter);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_ = MainActivity.this;
        listView = (ListView)findViewById(R.id.list_news);

        /*
        ArrayList<NewsBean> news_from_db = NewsUtils.getDBNews(context_);

        if (news_from_db != null && news_from_db.size() > 0){
            NewsAdapter newsAdapter = new NewsAdapter(context_, news_from_db);
            listView.setAdapter(newsAdapter);
        }
        */

        new Thread(new Runnable() {
            @Override
            public void run() {
                newsBeanList = NewsUtils.getNetNews(context_,
                        "http://news.maxjia.com/maxnews/app/list/");
                Message message = Message.obtain();
                message.obj = newsBeanList;
                handler.sendMessage(message);
            }
        }).start();

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        NewsBean news = (NewsBean) adapterView.getItemAtPosition(i);
        String urlString  = news.getNewsUrl();
        Intent intent = new Intent(MainActivity.this, NewsDisplayActivity.class);
        intent.putExtra("news_url", urlString);
        startActivity(intent);
    }
}
