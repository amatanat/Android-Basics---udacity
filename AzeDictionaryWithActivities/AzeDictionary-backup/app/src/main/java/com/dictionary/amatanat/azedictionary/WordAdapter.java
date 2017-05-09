package com.dictionary.amatanat.azedictionary;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amatanat on 01.05.17.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorID;

    public WordAdapter(Activity context, ArrayList<Word> words, int color) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter, the adapter is not going to use this second argument,
        // so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorID = color;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english_word_id);
        // Get the english text from the current Word object and
        // set this text on the TextView
        englishTextView.setText(currentWord.getEnglishTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView azeriTextView = (TextView) listItemView.findViewById(R.id.azeri_word_id);
        // Get the azeri text from the current Word object and
        // set this text on the TextView
        azeriTextView.setText(currentWord.getAzeriTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_id);

        // chech if current word has imageview or not
        if (currentWord.hasImage()){
            // set imageview of the word
            imageView.setImageResource(currentWord.getmImageResourceID());

            //set imageview visible
            imageView.setVisibility(View.VISIBLE);

        } else {
            // if word doesn't contain imageview then set it's imageview to be GONE,
            // i.e no empty space for image
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorID);
        textContainer.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
