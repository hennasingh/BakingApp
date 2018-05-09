package com.artist.web.bakerscorner.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.artist.web.bakerscorner.MainApplication;
import com.artist.web.bakerscorner.R;

/**
 * Created by User on 02-May-18.
 */

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityReceiver() {
        super();
    }

    public static boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) MainApplication.getInstance().getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String intentAction;
        if (connectivityReceiverListener != null) {
            intentAction = intent.getAction();
            if (!TextUtils.isEmpty(intentAction)) {
                if (intentAction.equals(context.getString(R.string.intent_filter_connectivity_change)) ||
                        intentAction.equals(context.getString(R.string.intent_filter_wifi_state_changed)) ||
                        intentAction.equals(context.getString(R.string.intent_filter_airplane_mode_changed))) {
                    connectivityReceiverListener.onNetworkConnectionChanged(Utils.checkConnectivity(context));
                }

            }
        }
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
