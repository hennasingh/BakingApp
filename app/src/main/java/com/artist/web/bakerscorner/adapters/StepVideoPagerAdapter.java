package com.artist.web.bakerscorner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.artist.web.bakerscorner.data.Steps;

import java.util.List;

/**
 * Created by User on 14-Apr-18.
 */

public class StepVideoPagerAdapter extends FragmentPagerAdapter {

    private List<Steps> stepList;
    private Steps step;

    public StepVideoPagerAdapter(FragmentManager fm, Steps step, List<Steps> stepList) {
        super(fm);
        this.step = step;
        this.stepList = stepList;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return stepList.size();
    }
}
