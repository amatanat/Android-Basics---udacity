package com.dictionary.amatanat.azedictionary;

/**
 * Created by amatanat on 01.05.17.
 */

public class Word {

    /* Azeri translation for the word */
    private String mAzeriTranslation;

    /* English translation for the word */
    private String mEnglishTranslation;

    /* Image for the word */
    private int mImageResourceID = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    /*
    Resource ID for the audio file
     */
    private int mAudioResourceID;

    /**
     * default constructor
     */
    public Word(String azeriTranslation, String englishTranslation, int audioResourceID){
        mAzeriTranslation = azeriTranslation;
        mEnglishTranslation = englishTranslation;
        mAudioResourceID = audioResourceID;
    }


    /**
     * default constructor
     */
    public Word(String azeriTranslation, String englishTranslation, int imageResourceID, int audioResourceID){
        mAzeriTranslation = azeriTranslation;
        mEnglishTranslation = englishTranslation;
        mImageResourceID = imageResourceID;
        mAudioResourceID = audioResourceID;
    }

    /*
    Get the Azeri translation of the word
     */
    public String getAzeriTranslation(){
        return mAzeriTranslation;
    }

    /*
    Get the English translation of the word
     */
    public String getEnglishTranslation(){
        return mEnglishTranslation;
    }

    /*
    Get the image ID for the word
     */
    public int getmImageResourceID(){
        return mImageResourceID;
    }

    /*
    Return boolean value wheather or not word has imageview
     */
    public boolean hasImage(){
        return  mImageResourceID != NO_IMAGE_PROVIDED;
    }

    /*
    Return the audio resource ID
     */
    public int getmAudioResourceID(){
        return mAudioResourceID;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mAzeriTranslation='" + mAzeriTranslation + '\'' +
                ", mEnglishTranslation='" + mEnglishTranslation + '\'' +
                ", mImageResourceID=" + mImageResourceID +
                ", mAudioResourceID=" + mAudioResourceID +
                '}';
    }
}
