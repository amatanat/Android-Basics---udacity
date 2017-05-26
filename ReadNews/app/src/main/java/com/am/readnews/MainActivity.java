package com.am.readnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // String url to get data from internet
    private final String STRING_URL = "http://content.guardianapis.com/search?section=technology&api-key=84e62d46-a76e-49d0-a15f-cc05c61fba1a";

    // my api_key
    private final String API_KEY = "84e62d46-a76e-49d0-a15f-cc05c61fba1a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
