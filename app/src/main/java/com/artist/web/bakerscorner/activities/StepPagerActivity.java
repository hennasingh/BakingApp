package com.artist.web.bakerscorner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.adapters.StepVideoPagerAdapter;
import com.artist.web.bakerscorner.data.Steps;
import com.layer_net.stepindicator.StepIndicator;

import java.util.ArrayList;

public class StepPagerActivity extends AppCompatActivity {

    private static final String PARCEL_LIST = "step_list";
    private static final String ARG_CLICKED_POSITION = "clicked_step";
    private static final int DEFAULT_POSITION = 0;
    private Steps clickedStep;

    public static Intent newIntent(Context packageContext, ArrayList<Steps> stepList, Steps step) {
        Intent intent = new Intent(packageContext, StepPagerActivity.class);
        intent.putExtra(PARCEL_LIST, stepList);
        intent.putExtra(ARG_CLICKED_POSITION, step);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_pager);

        ArrayList<Steps> stepList = getIntent().getParcelableArrayListExtra(PARCEL_LIST);
        Steps clickedStep = getIntent().getParcelableExtra(ARG_CLICKED_POSITION);

        //Set the pager with an adapter
        ViewPager pager = findViewById(R.id.viewpager);
        pager.setAdapter(new StepVideoPagerAdapter(getSupportFragmentManager(), stepList, clickedStep.getStepId()));

        //Bind the step indicator to the adapter
        StepIndicator stepIndicator = findViewById(R.id.step_indicator);
        stepIndicator.setupWithViewPager(pager);
        stepIndicator.setStepsCount(stepList.size());
        stepIndicator.setCurrentStepPosition(clickedStep.getStepId());

    }
}
