package com.artist.web.bakerscorner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.network.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoConnectionActivity extends AppCompatActivity {

    @BindView(R.id.retry)
    Button mRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
        ButterKnife.bind(this);
        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.checkConnectivity(getApplicationContext())) {

                }
            }
        });
    }
}
