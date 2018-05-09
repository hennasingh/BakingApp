package com.artist.web.bakerscorner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.fragments.StepsFragment;
import com.artist.web.bakerscorner.models.Steps;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 13-Apr-18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsHolder> {

    private ArrayList<Steps> mStepsList;
    private StepsFragment.OnStepClickListener mStepsOnClickListener;

    public StepsAdapter(ArrayList<Steps> stepsList, StepsFragment.OnStepClickListener mOnClickListener) {
        mStepsList = stepsList;
        mStepsOnClickListener = mOnClickListener;
    }

    @Override
    public StepsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.step_list_item, parent, false);
        return new StepsHolder(view, mStepsOnClickListener);
    }

    @Override
    public void onBindViewHolder(StepsHolder holder, int position) {

        Steps mDisplayStep = mStepsList.get(position);
        if (position == mStepsList.size() - 1) {
            holder.mVerticalSeparator.setBackgroundColor(holder.viewBackground);

        }
        holder.mStepNumber.setText(String.valueOf(mDisplayStep.getStepId()));
        holder.mStepDescription.setText(mDisplayStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mStepsList == null) return 0;
        return mStepsList.size();
    }


    class StepsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textViewStepNum)
        TextView mStepNumber;
        @BindView(R.id.step_description)
        TextView mStepDescription;
        @BindView(R.id.vertical_connector)
        View mVerticalSeparator;
        @BindColor(R.color.color_recipe_list)
        int viewBackground;
        StepsFragment.OnStepClickListener mClickListener;

        StepsHolder(View itemView, StepsFragment.OnStepClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mClickListener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Steps step = mStepsList.get(clickedPosition);
            mClickListener.OnStepSelected(step, mStepsList);

        }
    }
}
