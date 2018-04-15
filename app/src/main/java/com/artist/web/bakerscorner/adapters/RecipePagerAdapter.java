package com.artist.web.bakerscorner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.artist.web.bakerscorner.data.Recipes;
import com.artist.web.bakerscorner.fragments.IngredientsFragment;
import com.artist.web.bakerscorner.fragments.StepsFragment;

/**
 * Created by User on 13-Apr-18.
 */

public class RecipePagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    private String tabTitles[] = new String[]{"Ingredients", "Steps"};
    private Recipes recipe;

    public RecipePagerAdapter(FragmentManager fm, Recipes recipe) {
        super(fm);
        this.recipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return IngredientsFragment.newInstance(recipe);
            case 1:
                return StepsFragment.newInstance(recipe);
            default:
                return IngredientsFragment.newInstance(recipe);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
