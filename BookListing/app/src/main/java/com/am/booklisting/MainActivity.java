package com.am.booklisting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set layout
        setContentView(R.layout.activity_main);

        // find {@link SearchView}
        final SearchView searchView = (SearchView) findViewById(R.id.search_view);

        // find {@link Button}
        Button searchButton = (Button) findViewById(R.id.search_button);

        // start {@link BooksActivity} on 'searchbutton' click
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create {@link Intent} and pass user entered data from 'searchview' to it
                Intent intent = new Intent(MainActivity.this, BooksActivity.class);
                intent.putExtra("Keyword", searchView.getQuery().toString());

                // start {@link Activity}
                startActivity(intent);
            }
        });
    }
}
