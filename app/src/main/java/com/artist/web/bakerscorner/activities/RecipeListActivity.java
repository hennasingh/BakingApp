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
    @BindString(R.string.disconnected)
    String noNetwork;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragment = getSupportFragmentManager().findFragmentById(R.id.recipe_fragment_container);

        showSnack(ConnectivityReceiver.isConnected());
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }


    void showSnack(boolean isConnected) {
        Log.v(TAG, "isConnected is :" + isConnected);
        Log.v(TAG, "fragment is: " + fragment);
        if (isConnected) {
            if (fragment != null && fragment.isAdded()) {
                RecipeListFragment recipeList = (RecipeListFragment) fragment;
                recipeList.updateUI();

            } else {
                fragment = new RecipeListFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_fragment_container, fragment)
                        .commit();
            }
        } else {
            setContentView(R.layout.no_connection);
            Log.v(TAG, "no connection displayed");
            Toast.makeText(getApplicationContext(), noNetwork, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.setConnectivityListener(this);
    }
}
