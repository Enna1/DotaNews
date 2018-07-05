package com.enna1.dotanews;

import java.util.Comparator;

public class NewsBean {

    private int news_id;
    private String date;
    private String title;
    private String news_url;
    private String cover_img_url;//the cover image of news

    public int getNewsId() { return news_id; }

    public String getDate() { return date; }

    public String getTitle() { return title; }

    public String getNewsUrl() { return news_url; }

    public String getCoverImgUrl() { return cover_img_url; }

    public void setNewsId(int news_id) { this.news_id = news_id; }

    public void setDate(String date) { this.date = date; }

    public void setTitle(String title) { this.title = title; }

    public void setNewsUrl(String news_url) { this.news_url = news_url; }

    public void setCoverImgUrl(String img_url) { this.cover_img_url = img_url; }
}

class NewsListComparator implements Comparator {
    @Override
    public int compare(Object o, Object t1) {
        NewsBean n1 = (NewsBean) o;
        NewsBean n2 = (NewsBean) t1;
        if (n1.getNewsId() <= n2.getNewsId())
            return 1;
        return -1;
    }
}