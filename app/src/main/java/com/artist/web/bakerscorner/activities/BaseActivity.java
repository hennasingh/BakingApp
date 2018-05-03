package com.artist.web.bakerscorner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.artist.web.bakerscorner.MainApplication;
import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.network.ConnectivityReceiver;

public class BaseActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        checkConnection();
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        if (isConnected) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.no_connection);
        }
    }

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
