package com.am.readnews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.format;

/**
 * Created by amatanat on 26.05.17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CustomViewHolder> {

    // list of news
    private List<News> mNewsList;

    /*
    * constructor for custom adapter
    */
    public NewsAdapter(List<News> newsList){
        this.mNewsList = newsList;
    }

    // view holder
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView title, section, publishedDate;

        private CustomViewHolder(View view) {
            super(view);

            // find textview ID for news title
            title = (TextView) view.findViewById(R.id.title);

            // find textview ID for section name
            section = (TextView) view.findViewById(R.id.section);

            // find textview id for published date
            publishedDate = (TextView) view.findViewById(R.id.date);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate layout for custom adapter
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        // get position of current news
        News news = mNewsList.get(position);

        // set title
        customViewHolder.title.setText(news.getTitle());

        // set section
        customViewHolder.section.setText(news.getSectionName());

        // set published date
        customViewHolder.publishedDate.setText(formatDate(news.getPublicationDate()));
    }

    @Override
    public int getItemCount() {
        // get news list size
        return mNewsList.size();
    }

    /*
    Format {@link Date} object in month, day and year format and return
     */
    private String formatDate(String stringDate){
        Date parsed;
        SimpleDateFormat dateFormatter;

        try {
            dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
            parsed = dateFormatter.parse(stringDate);

        }
        catch(ParseException pe) {
            throw new IllegalArgumentException();
        }

        return dateFormatter.format(parsed);
    }
}
