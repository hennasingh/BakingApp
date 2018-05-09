package com.artist.web.bakerscorner.activities;

import android.os.Bundle;
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
    RecipeListFragment recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recipeList = (RecipeListFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_fragment_container);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }


    void showSnack(boolean isConnected) {
        if (isConnected) {

            recipeList.updateUI();

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
