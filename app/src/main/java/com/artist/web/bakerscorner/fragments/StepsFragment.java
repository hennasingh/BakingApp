package com.artist.web.bakerscorner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.activities.StepPagerActivity;
import com.artist.web.bakerscorner.adapters.StepsAdapter;
import com.artist.web.bakerscorner.data.Recipes;
import com.artist.web.bakerscorner.data.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by User on 13-Apr-18.
 */

public class StepsFragment extends Fragment implements StepsAdapter.StepsOnClickListener {

    private static final String ARG_RECIPE = "recipe_selected";
    Recipes mdisplayedRecipe;
    StepsAdapter mStepsAdapter;
    @BindView(R.id.rv_recipes)
    RecyclerView mStepsRecyclerView;
    ArrayList<Steps> stepList;
    private Unbinder unbinder;

    public static StepsFragment newInstance(Recipes recipe) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mdisplayedRecipe = getArguments().getParcelable(ARG_RECIPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View stepsView = inflater.inflate(R.layout.fragment_recipes_list_steps, container, false);

        unbinder = ButterKnife.bind(this, stepsView);
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return stepsView;
    }

    private void updateUI() {

        stepList = mdisplayedRecipe.getStepsList();

        if (mStepsAdapter == null) {
            mStepsAdapter = new StepsAdapter(stepList, this);
            mStepsRecyclerView.setAdapter(mStepsAdapter);
            mStepsRecyclerView.setHasFixedSize(true);
        } else {
            mStepsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(Steps step) {
        Intent intent = StepPagerActivity.newIntent(getActivity(), stepList, step);
        startActivity(intent);
    }

    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
