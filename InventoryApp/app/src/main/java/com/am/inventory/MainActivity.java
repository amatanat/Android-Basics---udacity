package com.am.inventory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.am.inventory.data.ProductContract;
import com.am.inventory.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {

    private ProductDbHelper mProductDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayDbInfo();


    }

    private void displayDbInfo(){

        mProductDbHelper = new ProductDbHelper(this);
        SQLiteDatabase db =  mProductDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ProductContract.ProductEntry.TABLE_NAME,null);

        try{

            TextView textView = (TextView) findViewById(R.id.textview);
            textView.setText("Number of rows = " + cursor.getCount());

        }finally {
            cursor.close();
        }

    }
}
