package com.dictionary.amatanat.azedictionary;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Numbers", "Family Members", "Colors", "Greetings", "Phrases" };


    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
         mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1){
            return new FamilyMembersFragment();
        } else if (position == 2){
            return new ColorsFragment();
        } else if (position == 3){
            return new GreetingsFragment();
        } else {
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
