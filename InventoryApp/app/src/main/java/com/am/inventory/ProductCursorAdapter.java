package com.am.inventory;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void bindView(View view, Context context, Cursor cursor) {

        // find tetxviews with the corresponding ids
        TextView productName = (TextView) view.findViewById(R.id.product_name);
        TextView productPrice = (TextView) view.findViewById(R.id.product_price);
        TextView productQuantity = (TextView) view.findViewById(R.id.product_quantity);
        ImageView productImage =  (ImageView) view.findViewById(R.id.product_image);


        // get values of corresponding columns from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY));
        String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PICTURE));

/*        // get background circle of price
        GradientDrawable priceBackgroundCircle = (GradientDrawable) productPrice.getBackground();

        // get random color and set price's background color
        int priceBackgroundColor = ColorGenerator.MATERIAL.getRandomColor();
        priceBackgroundCircle.setColor(priceBackgroundColor);*/

        // set text of textviews
        productName.setText(name);
        productPrice.setText(price);
        productQuantity.setText(Integer.toString(quantity));
        if (!TextUtils.isEmpty(imageUri)){
            Uri image =  Uri.parse(imageUri);
            productImage.setImageURI(image);
        }
        Log.i("ProductCurosrAdapter", "data is setted..........");
    }
}
