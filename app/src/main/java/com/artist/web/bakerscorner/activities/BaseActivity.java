package com.artist.web.bakerscorner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.artist.web.bakerscorner.MainApplication;
import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.network.ConnectivityReceiver;

import butterknife.BindString;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @BindString(R.string.disconnected)
    String noNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    abstract void showSnack(boolean isConnected);


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
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
}
