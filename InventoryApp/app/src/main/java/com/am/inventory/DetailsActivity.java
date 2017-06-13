package com.am.inventory;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.am.inventory.data.ProductContract.ProductEntry;

public class DetailsActivity extends AppCompatActivity {

    private Uri mContentUri;

    private Button mSaveProductButton;
    private EditText mProductNameEditText;
    private EditText mProductPriceEditText;
    private EditText mProductQuantityEditText;
    private EditText mProductSupplierEditText;
    private ImageView mProductImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mProductNameEditText        = (EditText)  findViewById(R.id.productName);
        mProductPriceEditText       = (EditText)  findViewById(R.id.productPrice);
        mProductQuantityEditText    = (EditText)  findViewById(R.id.productQuantity);
        mProductSupplierEditText    = (EditText)  findViewById(R.id.productSupplier);
        mProductImage               = (ImageView) findViewById(R.id.productImage);


        mContentUri = getIntent().getData();

        if (mContentUri == null){
            // fab button is clicked
            getSupportActionBar().setTitle(R.string.addNewProduct);
            invalidateOptionsMenu();
        } else {
            // ListView item is clicked
            getSupportActionBar().setTitle(R.string.editProduct);
        }

        mSaveProductButton = (Button) findViewById(R.id.save);
        mSaveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // If this is a new product, hide the "Delete" and "Order" menu items.
        if (mContentUri == null) {
            MenuItem deleteItem = menu.findItem(R.id.delete);
            deleteItem.setVisible(false);
            MenuItem orderItem = menu.findItem(R.id.order);
            orderItem.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.order:
                // sends an intent to either a phone app or an email app to
                // contact the supplier using the information stored in the database.
                //Todo pop up a toast
                return true;
            case R.id.delete:
                deleteProduct();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void saveProduct(){
        int productPrice = 0;
        int productQuantity = 0;

        String productName = mProductNameEditText.getText().toString().trim();
        String productSupplier = mProductSupplierEditText.getText().toString().trim();

        String price = mProductPriceEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(price)){
            productPrice = Integer.parseInt(price);
        }

        String quantity = mProductQuantityEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(quantity)){
            productQuantity = Integer.parseInt(quantity);
        }

        // if product name or supplier's value is empty then finish {@link DetailsActivity}
        if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(productSupplier)){
            finish();

        } else {

            ContentValues contentValues = new ContentValues();
            contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, productName);
            contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER, productSupplier);
            contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);
            contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);


            if (mContentUri == null){
                // if fab button is clicked then insert new product
                Uri insertedProductUri = getContentResolver().insert(ProductEntry.CONTENT_URI,contentValues);

                if (insertedProductUri == null){
                    Toast.makeText(this, R.string.error_in_inserting, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.product_added, Toast.LENGTH_SHORT).show();
                }

            } else {
                // if ListView item is clicked then update product data
                int updatedProductsRowNumber = getContentResolver().update(mContentUri,contentValues, null,null);
                showToastMessage(updatedProductsRowNumber, "update");
            }
        }
        finish();
    }

    private void deleteProduct(){
        if (mContentUri != null){
            int deletedRowNumber = getContentResolver().delete(mContentUri,null,null);
            showToastMessage(deletedRowNumber,"delete");
        }
        finish();
    }

    private void showToastMessage(int receivedRowNumber, String methodName){
        if (receivedRowNumber == 0){
            Toast.makeText(this, getString(R.string.product_failed) + " " + methodName + " product",
                    Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, getString(R.string.product_success) + " " + methodName + " success",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
