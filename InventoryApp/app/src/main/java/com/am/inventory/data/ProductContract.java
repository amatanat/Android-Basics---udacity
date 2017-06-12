package com.am.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by amatanat on 09.06.17.
 */

public class ProductContract {

    // Authority name for the {@link ProductProvider}
    public static final String CONTENT_AUTHORITY = "com.am.inventory";

    /*
     Content Uri without data type
     * Parse string into URI
      */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // table name for the {@link ProductProvider} Content URI
    public static final String PATH_PRODUCTS = "products";

    private ProductContract(){}

    public static final class ProductEntry implements BaseColumns{

        // Content URI with the table name. Appends string table name to the base URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        public static final String TABLE_NAME = "products";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_PRODUCT_NAME = "name";

        public static final String COLUMN_PRODUCT_PRICE = "price";

        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";

        public static final String COLUMN_PRODUCT_SUPPLIER = "supplier";

        public static final String COLUMN_PRODUCT_PICTURE = "picture";


        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

    }
}
