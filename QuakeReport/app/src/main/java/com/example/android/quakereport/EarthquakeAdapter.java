package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by amatanat on 09.05.17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    public EarthquakeAdapter(Activity context, List<Earthquake> earthquakes){

        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for three TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // find textview for magnitude in xml file and set its text
        TextView mMagnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        mMagnitude.setText(currentEarthquake.getMagnitute());

        //find textview for location in xml file
        TextView mLocation = (TextView) listItemView.findViewById(R.id.location);

        //find textview for distance in xml file
        TextView mDistance = (TextView) listItemView.findViewById(R.id.distance);

        // get location of current earthquake
        String originalLocation = currentEarthquake.getLocation();

        // check if location contains 'of' keyword
        // to see if distance is included
        if (originalLocation.contains("of")){
            // get index of 'of' from string
            int indexOfOf = originalLocation.indexOf("of");

            // get string from index 0 to the index of 'of' keyword from originalLocation
            String distance = originalLocation.substring(0, indexOfOf + 2);

            // set text of distance textview
            mDistance.setText(distance);

            // get string after 'of' keyword from originalLocation
            String place = originalLocation.substring(indexOfOf + 3);

            // set text of location textview
            mLocation.setText(place);
        }else {
            // otherwise use 'near of' keyword for distance
            mDistance.setText(getContext().getText(R.string.near));
            mLocation.setText(currentEarthquake.getLocation());
        }

        // get time in milliseconds for current earthquake
        Date dateAndTimeObject = new Date(currentEarthquake.getTimeInMilliSeconds());

        //find textview for date in xml file
        TextView mDate = (TextView) listItemView.findViewById(R.id.date);

        // format {@link Date} object in date format and set it as text of textview
        mDate.setText(formatDate(dateAndTimeObject));

        //find textview id for time in xml file
        TextView mTime = (TextView) listItemView.findViewById(R.id.time);

        // format {@link Date} object in time format and set it as text of textview
        mTime.setText(formatTime(dateAndTimeObject));

        // return list item view
        return listItemView;
    }

    /*
    Format {@link Date} object in month, day and year format and return
     */
    private String formatDate(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormatter.format(date);
    }

    /*
   Format {@link Date} object in "h:mm a" format and return
    */
    private String formatTime(Date date){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        return timeFormatter.format(date);
    }
}
