package com.artist.web.bakerscorner.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.artist.web.bakerscorner.R;

/**
 * Created by User on 08-Apr-18.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.recipe_fragment_container);

        if(fragment==null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_fragment_container, fragment)
                    .commit();
        }
    }
}
