package com.am.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.am.inventory.data.ProductContract;
import com.am.inventory.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ProductDbHelper mProductDbHelper;

    private final int LOADER_INIT = 1;

    ProductCursorAdapter mProductCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        mProductDbHelper = new ProductDbHelper(this);

        // get instance of {@link ProductCursorAdapter} and set it as adapter to listview
        mProductCursorAdapter = new ProductCursorAdapter(this, null);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(mProductCursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                // send Content Uri with the id of the clicked item
                intent.setData(ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI, id));
                startActivity(intent);
                //Todo: set price background color here
            }
        });

        // get emptyview id and set it as emptyview in listview
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        getSupportLoaderManager().initLoader(LOADER_INIT, null, this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.dummy_data:
                insertDataIntoDatabase();
                return true;
            case R.id.delete:
                deleteDataFromDatabase();
                return true;
            default:
                 return super.onOptionsItemSelected(menuItem);
        }
    }

    private void insertDataIntoDatabase(){

        ContentValues values = new ContentValues();

        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,"book");
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE, 7);
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, 6);
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL,"test@gmail.com");
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_SUPPLIER, "Thalia");
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_PICTURE,"");

        Uri resultUri= getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);

        Log.i("MainActivity", "Result uri in insert method......" + resultUri);
    }

    private void deleteDataFromDatabase(){
        getContentResolver().delete(ProductContract.ProductEntry.CONTENT_URI, null,null);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY
        };

        // This loader will execute ContentProvider's query method in a background thread
        return new CursorLoader(this, ProductContract.ProductEntry.CONTENT_URI, projection, null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mProductCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProductCursorAdapter.swapCursor(null);
    }
}
