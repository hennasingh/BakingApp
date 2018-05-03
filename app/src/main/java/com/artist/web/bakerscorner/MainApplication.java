package com.artist.web.bakerscorner;

import android.app.Application;
import android.content.IntentFilter;

import com.artist.web.bakerscorner.network.ConnectivityReceiver;
import com.artist.web.bakerscorner.network.HttpClient;

/**
 * Created by User on 05-Apr-18.
 */

public class MainApplication extends Application {

    public static HttpClient sHttpClient;
    private static MainApplication sMainApplication;
    private static IntentFilter mIntentFilter;
    private static ConnectivityReceiver mReceiver;

    public static synchronized MainApplication getInstance() {
        return sMainApplication;
    }

    public static void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    /**
     * Method that registers/unregisters receiver based on whether the application is visible or not
     */
    public static void setReceiverStatus(boolean isActivityVisible) {
        if (isActivityVisible) {
            // register broadcast receiver
            getInstance().registerReceiver(mReceiver, mIntentFilter);
        } else {
            // unregister broadcast receiver to prevent memory leaks
            getInstance().unregisterReceiver(mReceiver);
        }
    }

    public void onCreate(){
        super.onCreate();
        sHttpClient = HttpClient.getInstance();
        sMainApplication = this;
        mReceiver = new ConnectivityReceiver();

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(getString(R.string.intent_filter_connectivity_change));
        mIntentFilter.addAction(getString(R.string.intent_filter_wifi_state_changed));
        mIntentFilter.addAction(getString(R.string.intent_filter_airplane_mode_changed));
        setReceiverStatus(true);
    }
}
