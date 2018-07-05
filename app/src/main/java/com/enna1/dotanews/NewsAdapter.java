package com.enna1.dotanews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private List<NewsBean> newsData;
    public NewsAdapter(Context context, List<NewsBean> listNewsBean){
        this.layoutInflater = LayoutInflater.from(context);
        this.newsData = listNewsBean;
    }
    @Override
    public int getCount() {
        return newsData.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = layoutInflater.inflate(R.layout.layout_item, null);
            viewHolder = new ViewHolder();
            viewHolder.item_cover_img = (MyImageView) view.findViewById(R.id.item_cover_img);
            viewHolder.item_tv_date = (TextView) view.findViewById(R.id.item_tv_date);
            viewHolder.item_tv_title = (TextView) view.findViewById(R.id.item_tv_title);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        NewsBean newsBean = newsData.get(i);
        viewHolder.item_cover_img.setImageUrl(newsBean.getNewsId(), newsBean.getCoverImgUrl());
        viewHolder.item_tv_title.setText(newsBean.getTitle());
        viewHolder.item_tv_date.setText(newsBean.getDate());

        return view;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return newsData.get(i);
    }
}

class ViewHolder{
    MyImageView item_cover_img;
    TextView item_tv_title;
    TextView item_tv_date;
}