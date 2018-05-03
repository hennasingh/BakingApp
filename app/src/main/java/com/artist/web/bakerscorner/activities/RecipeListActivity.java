package com.artist.web.bakerscorner.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.artist.web.bakerscorner.IdlingResource.SimpleIdlingResource;
import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.fragments.RecipeListFragment;
import com.artist.web.bakerscorner.network.ConnectivityReceiver;

import butterknife.BindString;
import butterknife.ButterKnife;

public class RecipeListActivity extends BaseActivity {

    @BindString(R.string.disconnected)
    String noNetwork;
    Fragment fragment;
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragment = getSupportFragmentManager().findFragmentById(R.id.recipe_fragment_container);

        showSnack(ConnectivityReceiver.isConnected());
    }

    @Override
    void showSnack(boolean isConnected) {
        if (isConnected) {
            if (fragment == null) {
                fragment = new RecipeListFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_fragment_container, fragment)
                        .commit();
            } else {

            }
        } else {
            setContentView(R.layout.no_connection);
            Toast.makeText(getApplicationContext(), noNetwork, Toast.LENGTH_SHORT).show();
        }
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
