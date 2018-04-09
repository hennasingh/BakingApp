package com.artist.web.bakerscorner;

import android.support.v4.app.Fragment;

import com.artist.web.bakerscorner.fragments.RecipeListFragment;

public class RecipeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }
}
