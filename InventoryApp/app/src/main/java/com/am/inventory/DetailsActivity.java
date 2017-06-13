package com.am.inventory;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
                // osends an intent to either a phone app or an email app to
                // contact the supplier using the information stored in the database.
                //Todo pop up a toast
                return true;
            case R.id.delete:
                //Todo delete that product from the database
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void saveProduct(){

    }
}
