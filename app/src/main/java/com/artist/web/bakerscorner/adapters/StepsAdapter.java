package com.artist.web.bakerscorner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.data.Steps;
import com.artist.web.bakerscorner.fragments.StepsFragment;

import java.util.ArrayList;

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
            holder.mVerticalSeparator.setVisibility(View.GONE);
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

        TextView mStepNumber;
        TextView mStepDescription;
        View mVerticalSeparator;
        StepsFragment.OnStepClickListener mClickListener;

        StepsHolder(View itemView, StepsFragment.OnStepClickListener listener) {
            super(itemView);
            mStepNumber = itemView.findViewById(R.id.textViewStepNum);
            mStepDescription = itemView.findViewById(R.id.step_description);
            mVerticalSeparator = itemView.findViewById(R.id.vertical_connector);
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
