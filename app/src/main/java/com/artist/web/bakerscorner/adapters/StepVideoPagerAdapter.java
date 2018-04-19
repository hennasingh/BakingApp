package com.artist.web.bakerscorner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.artist.web.bakerscorner.data.Steps;
import com.artist.web.bakerscorner.fragments.StepVideoFragment;

import java.util.ArrayList;

/**
 * Created by User on 14-Apr-18.
 */

public class StepVideoPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Steps> stepList;
    private int positionToDisplay;

    public StepVideoPagerAdapter(FragmentManager fm, ArrayList<Steps> stepList, int stepId) {
        super(fm);
        this.stepList = stepList;
        this.positionToDisplay = stepId;
    }

    @Override
    public Fragment getItem(int position) {
        return StepVideoFragment.newInstance(position, stepList);
    }

    @Override
    public int getCount() {
        return stepList.size();
    }
}
