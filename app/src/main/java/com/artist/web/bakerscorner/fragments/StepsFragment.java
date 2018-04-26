package com.artist.web.bakerscorner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.adapters.StepsAdapter;
import com.artist.web.bakerscorner.models.Recipes;
import com.artist.web.bakerscorner.models.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by User on 13-Apr-18.
 */

public class StepsFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe_selected";
    Recipes mdisplayedRecipe;
    StepsAdapter mStepsAdapter;
    @BindView(R.id.rv_recipes)
    RecyclerView mStepsRecyclerView;
    ArrayList<Steps> stepList;
    private Unbinder unbinder;

    private OnStepClickListener mCallbacks;

    public static StepsFragment newInstance(Recipes recipe) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallbacks = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
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
            mStepsAdapter = new StepsAdapter(stepList, mCallbacks);
            mStepsRecyclerView.setAdapter(mStepsAdapter);
            mStepsRecyclerView.setHasFixedSize(true);

        } else {
            mStepsAdapter.notifyDataSetChanged();
        }
    }

    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Required interface for hosting activities
     */
    public interface OnStepClickListener {
        void OnStepSelected(Steps step, ArrayList<Steps> stepList);
    }
}
