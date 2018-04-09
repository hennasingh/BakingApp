package com.artist.web.bakerscorner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by User on 08-Apr-18.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
