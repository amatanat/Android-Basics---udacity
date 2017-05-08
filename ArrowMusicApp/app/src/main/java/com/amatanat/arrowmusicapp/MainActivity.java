package com.amatanat.arrowmusicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button genres = (Button) findViewById(R.id.genres);
        genres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent genresIntent = new Intent(MainActivity.this, GenresActivity.class);
                startActivity(genresIntent);
            }
        });

        Button mood = (Button) findViewById(R.id.mood);
        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moodIntent = new Intent(MainActivity.this, MoodActivity.class);
                startActivity(moodIntent);

            }
        });

        Button artists = (Button) findViewById(R.id.artists);
        artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent artistsIntent = new Intent(MainActivity.this, ArtistsActivity.class);
                startActivity(artistsIntent);

            }
        });

        Button popularSongs = (Button) findViewById(R.id.popular_songs);
        popularSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popularSongsIntent = new Intent(MainActivity.this, PopularSongsActivity.class);
                startActivity(popularSongsIntent);

            }
        });

        Button newReleases = (Button) findViewById(R.id.new_releases);
        newReleases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newReleasesIntent = new Intent(MainActivity.this, NewReleasesActivity.class);
                startActivity(newReleasesIntent);

            }
        });


    }
}
