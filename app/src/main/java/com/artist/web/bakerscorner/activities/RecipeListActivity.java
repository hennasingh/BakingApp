package com.artist.web.bakerscorner.activities;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;

import com.artist.web.bakerscorner.IdlingResource.SimpleIdlingResource;
import com.artist.web.bakerscorner.fragments.RecipeListFragment;

public class RecipeListActivity extends SingleFragmentActivity {

    private SimpleIdlingResource mIdlingResource;
    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }


    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
