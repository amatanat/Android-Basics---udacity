package com.am.readnews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * Created by amatanat on 26.05.17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CustomViewHolder> {

    // list of news
    private List<News> mNewsList;

    private Context mContext;

    /*
    * constructor for custom adapter
    */
    public NewsAdapter(List<News> newsList){
        this.mNewsList = newsList;
    }

    // view holder class
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView title, section, publishedDate, mPosition;

        private CustomViewHolder(View view) {
            super(view);

            mContext = view.getContext();

            // find textview ID for news title
            title = (TextView) view.findViewById(R.id.title);

            // find textview ID for section name
            section = (TextView) view.findViewById(R.id.section);

            // find textview id for published date
            publishedDate = (TextView) view.findViewById(R.id.date);

            //find position id
            mPosition = (TextView) view.findViewById(R.id.position);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // get clicked layout position
                    int itemPosition = getLayoutPosition();

                    // get news for clicked position
                    News news = mNewsList.get(itemPosition);

                    // get instance of {@link Intent} and get {@link News} url
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));

                    // open url in browser
                    mContext.startActivity(browserIntent);
                }
            });
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

        // set position
        customViewHolder.mPosition.setText(news.getPosition());

    }

    @Override
    public int getItemCount() {
        // get news list size
        return mNewsList.size();
    }

    /*
    Format {@link Date} object in year, month and date format and return
     */
    private String formatDate(String input){
        String date;
        try {
            // date format : yyyy-MM-dd
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            //get date part from input string
            String stringDate = input.substring(0,10);

            //convert string date to date format
            Date convertedCurrentDate = simpleDateFormat.parse(stringDate);

            // format date into string format
            date = simpleDateFormat.format(convertedCurrentDate );
        }
        catch(ParseException e) {
            throw new IllegalArgumentException();
        }

        return date;
    }
}
