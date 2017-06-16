package com.am.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.inventory.data.ProductContract.ProductEntry;
import com.amulyakhare.textdrawable.util.ColorGenerator;

/**
 * Created by amatanat on 09.06.17.
 */

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor cursor){
        super(context, cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent,false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        // find tetxviews with the corresponding ids
        TextView productName = (TextView) view.findViewById(R.id.product_name);
        TextView productPrice = (TextView) view.findViewById(R.id.product_price);
        final TextView productQuantity = (TextView) view.findViewById(R.id.product_quantity);
        ImageView productImage =  (ImageView) view.findViewById(R.id.product_image);

        // get values of corresponding columns from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY));
        String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PICTURE));

        final Button buyButton = (Button) view.findViewById(R.id.buy_product);
        buyButton.setTag(cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry._ID)));
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qnty = Integer.parseInt(productQuantity.getText().toString().trim());
                if ( qnty > 0 ){
                    qnty--;
                    productQuantity.setText(Integer.toString(qnty));
                    int id = (Integer) v.getTag();
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, qnty);
                    context.getContentResolver().update
                                (ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id),
                                        values, null,null);
                }
            }
        });
        // set text of textviews
        productName.setText(name);
        productPrice.setText(price);
        productQuantity.setText(Integer.toString(quantity));
        if (!TextUtils.isEmpty(imageUri)){
            Uri image =  Uri.parse(imageUri);
            productImage.setImageURI(image);
        }
    }
}
