package com.artist.web.bakerscorner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.adapters.RecipePagerAdapter;
import com.artist.web.bakerscorner.fragments.IngredientsFragment;
import com.artist.web.bakerscorner.fragments.StepVideoFragment;
import com.artist.web.bakerscorner.fragments.StepsFragment;
import com.artist.web.bakerscorner.models.Recipes;
import com.artist.web.bakerscorner.models.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipePagerActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener {

    private static final String PARCEL_DATA = "recipe_data";
    @Nullable
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @Nullable
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    private boolean isTwoPane = false;


    public static Intent newIntent(Context packageContext, Recipes recipe) {
        Intent intent = new Intent(packageContext, RecipePagerActivity.class);
        intent.putExtra(PARCEL_DATA, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterDetail);
        ButterKnife.bind(this);
        Recipes displayRecipe = getIntent().getParcelableExtra(PARCEL_DATA);
        setTitle(displayRecipe.getRecipeName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.layout_tablet) != null) {

            isTwoPane = true;
            Fragment ingredFragment = fragmentManager.findFragmentById(R.id.container_ingredients);
            Fragment stepsFragment = fragmentManager.findFragmentById(R.id.container_steps);


            if (ingredFragment == null && stepsFragment == null) {
                ingredFragment = IngredientsFragment.newInstance(displayRecipe);
                stepsFragment = StepsFragment.newInstance(displayRecipe);
                fragmentManager.beginTransaction()
                        .replace(R.id.container_ingredients, ingredFragment)
                        .replace(R.id.container_steps, stepsFragment)
                        .commit();
            }

        } else {

            mViewPager.setAdapter(new RecipePagerAdapter(fragmentManager, displayRecipe));
            tabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public void OnStepSelected(Steps step, ArrayList<Steps> stepList) {
        if (isTwoPane) {
            Fragment videoFragment = StepVideoFragment.newInstance(step.getStepId(), stepList);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_video, videoFragment)
                    .commit();
        } else {
            Intent intent = StepPagerActivity.newIntent(this, stepList, step);
            startActivity(intent);
        }
    }

}
