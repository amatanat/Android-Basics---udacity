package com.am.myparcelableapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView nameTextView = (TextView) findViewById(R.id.name);

        TextView addressTextView = (TextView) findViewById(R.id.address);

        TextView emailTextView = (TextView) findViewById(R.id.email);

        Bundle b = getIntent().getExtras();
        Person p = b.getParcelable("Parcel data");

        String name = p.getName();
        Log.i("SecondActivity", "Received data: name ...." + name);

        String email = p.getEmail();
        Log.i("SecondActivity", "Received data: name ...." + email);

        String address = p.getAddress();
        Log.i("SecondActivity", "Received data: name ...." + address);

        nameTextView.setText(name);
        emailTextView.setText(email);
        addressTextView.setText(address);
    }
}
