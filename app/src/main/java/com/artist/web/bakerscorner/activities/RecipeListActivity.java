package com.artist.web.bakerscorner.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.artist.web.bakerscorner.MainApplication;
import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.fragments.RecipeListFragment;
import com.artist.web.bakerscorner.network.ConnectivityReceiver;

import butterknife.BindString;
import butterknife.ButterKnife;

public class RecipeListActivity extends BaseActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = RecipeListActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG = "RecipeListFragment";
    @BindString(R.string.disconnected)
    String noNetwork;
    Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }


    void showSnack(boolean isConnected) {
        if (isConnected) {
            if (mFragment == null) {
                mFragment = new RecipeListFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.recipe_fragment_container, mFragment, FRAGMENT_TAG).commit();
            } else {
                RecipeListFragment recipeList = (RecipeListFragment) mFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.recipe_fragment_container, recipeList, FRAGMENT_TAG).commit();
            }
        } else {
            setContentView(R.layout.no_connection);
            Log.v(TAG, "no connection displayed");
            Toast.makeText(getApplicationContext(), noNetwork, Toast.LENGTH_SHORT).show();
            Fragment frag = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            if (frag != null) getSupportFragmentManager().beginTransaction().remove(frag).commit();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.setConnectivityListener(this);
    }
}
