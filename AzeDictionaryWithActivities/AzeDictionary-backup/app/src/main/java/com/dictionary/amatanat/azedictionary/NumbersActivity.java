package com.dictionary.amatanat.azedictionary;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    // receive audio focus change from system
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {

                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                        // Pause playback because your Audio Focus was
                        // temporarily stolen, but will be back soon.
                        // i.e. for a phone call
                        mediaPlayer.pause();

                        // move audio to beginning because our words are very short
                        // so that when continuing to play it'll start from beginning
                        // but not continue from where it has stopped
                        mediaPlayer.seekTo(0);

                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                        // Stop playback, because you lost the Audio Focus.
                        // i.e. the user started some other playback app
                        // Remember to unregister your controls/buttons here.
                        // And release the kra — Audio Focus!

                        releaseMediaPlayer();

                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                        // Resume playback, because you hold the Audio Focus
                        // again!
                        // i.e. the phone call ended or the nav directions
                        // are finished
                        // If you implement ducking and lower the volume, be
                        // sure to return it to normal here, as well.
                        mediaPlayer.start();
                    }
                }
    };


    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // get audio manager system service
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

       final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("bir", "one", R.drawable.number_one, R.raw.a));
        words.add(new Word("iki", "two",R.drawable.number_two, R.raw.a));
        words.add(new Word("üç", "three", R.drawable.number_three, R.raw.a));
        words.add(new Word("dörd", "four", R.drawable.number_four, R.raw.a));;
        words.add(new Word("beş", "five", R.drawable.number_five, R.raw.a));
        words.add(new Word("altı", "six", R.drawable.number_six, R.raw.a));
        words.add(new Word("yeddi", "seven", R.drawable.number_seven, R.raw.a));
        words.add(new Word("səkkiz", "eight", R.drawable.number_eight, R.raw.a));
        words.add(new Word("doqquz", "nine", R.drawable.number_nine, R.raw.a));
        words.add(new Word("on", "ten", R.drawable.number_ten, R.raw.a));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.color_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                releaseMediaPlayer();

                //request audio focus from audio manager for playing music
                int audioFocus = audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                // if audio focus is granted by the system
                if (audioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // use mediaplayer for playing audio sounds
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceID());

                    // system granted audio focus and we can start media player
                    mediaPlayer.start();

                    // set up on complition listener that releases media player from memory
                    //after finishing audio playing
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // release audio player
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // release Media player resources when activity in stopped
        // because we don't need to play sound when leaving activity
        releaseMediaPlayer();
    }

}
