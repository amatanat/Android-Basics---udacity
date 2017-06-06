package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;

/**
 * Created by amatanat on 06.06.17.
 */

public class PetCursorAdapter extends CursorAdapter {

    /*
    Constructor for the Cursor Adapter
     */
    public PetCursorAdapter(Context context, Cursor cursor){
        super(context,cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // inflate layout for the custom cursor adapter
        return  LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // find textview by id in layout
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView summary = (TextView) view.findViewById(R.id.summary);

        // get value of 'name' and 'breed' columns
        String nameText = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_NAME));
        String breed =  cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_BREED));

        // set text of tetxviews
        name.setText(nameText);
        summary.setText(breed);

    }
}
