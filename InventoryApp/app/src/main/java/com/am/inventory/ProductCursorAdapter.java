package com.am.inventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.am.inventory.data.ProductContract.ProductEntry;

/**
 * Created by amatanat on 09.06.17.
 */

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor cursor){
        super(context, cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // find tetxviews with the corresponding ids
        TextView productName = (TextView) view.findViewById(R.id.product_name);
        TextView productPrice = (TextView) view.findViewById(R.id.product_price);
        TextView productQuantity = (TextView) view.findViewById(R.id.product_quantity);

        // get values of corresponding columns from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
        int price = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PICTURE));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY));

        // set text of textviews
        productName.setText(name);
        productPrice.setText(Integer.toString(price));
        productQuantity.setText(Integer.toString(quantity));

    }
}
