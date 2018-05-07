package com.artist.web.bakerscorner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.artist.web.bakerscorner.MainApplication;
import com.artist.web.bakerscorner.R;

import butterknife.BindString;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @BindString(R.string.disconnected)
    String noNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.setReceiverStatus(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MainApplication.setReceiverStatus(false);
    }
}
