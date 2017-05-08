package com.dictionary.amatanat.azedictionary;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {


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

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        // get audio manager system service
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("Hara gedirsən?", "Where are you going?", R.raw.a));
        words.add(new Word("Sənin adın nədir?", "What is your name?", R.raw.a));
        words.add(new Word("Mənim adım...", "My name is ...", R.raw.a));
        words.add(new Word("Özünü necə hiss edirsən?", "How are you feeling?", R.raw.a));;
        words.add(new Word("Mən özümü yaxşı hiss edirəm", "I'm feeling good", R.raw.a));
        words.add(new Word("Sən gəlirsən?", "Are you coming?", R.raw.a));
        words.add(new Word("Hə", "Yes, I'm.", R.raw.a));
        words.add(new Word("Mən gəlirəm", "I'm coming", R.raw.a));
        words.add(new Word("Bura gəl", "Come here", R.raw.a));
        words.add(new Word("Getdik", "Let's go", R.raw.a));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words,R.color.color_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

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
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioResourceID());

                    // system granted audio focus and we can start media player
                    mediaPlayer.start();

                    // set up on complition listener that releases media player from memory
                    //after finishing audio playing
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });

        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();
        // release Media player resources when activity in stopped
        // because we don't need to play sound when leaving activity
        releaseMediaPlayer();
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

}
