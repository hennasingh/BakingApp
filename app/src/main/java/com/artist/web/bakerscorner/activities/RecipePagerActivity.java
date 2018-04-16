package com.artist.web.bakerscorner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.adapters.RecipePagerAdapter;
import com.artist.web.bakerscorner.data.Recipes;

public class RecipePagerActivity extends AppCompatActivity {

    private static final String PARCEL_DATA = "recipe_data";

    public static Intent newIntent(Context packageContext, Recipes recipe) {
        Intent intent = new Intent(packageContext, RecipePagerActivity.class);
        intent.putExtra(PARCEL_DATA, recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_pager);

        Recipes displayRecipe = getIntent().getParcelableExtra(PARCEL_DATA);
        ViewPager mViewPager = findViewById(R.id.viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new RecipePagerAdapter(fragmentManager, displayRecipe));
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }
}
