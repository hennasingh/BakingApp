package com.artist.web.bakerscorner.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;

import com.artist.web.bakerscorner.IdlingResource.SimpleIdlingResource;
import com.artist.web.bakerscorner.MainApplication;
import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.fragments.RecipeListFragment;
import com.artist.web.bakerscorner.network.ConnectivityReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends SingleFragmentActivity implements
        ConnectivityReceiver.ConnectivityReceiverListener {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected Fragment createFragment() {
        ButterKnife.bind(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.setReceiverStatus(true);
        MainApplication.setConnectivityListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MainApplication.setReceiverStatus(false);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if (isConnected) {
            RecipeListFragment mRecipeListFragment = (RecipeListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.recipe_fragment_container);
            mRecipeListFragment.updateUI();

        } else {
            Intent noConnection = new Intent(this, NoConnectionActivity.class);
            noConnection.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(noConnection);
        }
    }

}
