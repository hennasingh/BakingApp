package com.artist.web.bakerscorner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.data.Steps;

import java.util.List;

/**
 * Created by User on 13-Apr-18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsHolder> {

    private List<Steps> mStepsList;
    private StepsOnClickListener mStepsOnClickListener;

    public StepsAdapter(List<Steps> stepsList, StepsOnClickListener mOnClickListener) {
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

    public interface StepsOnClickListener {
        void onItemClick(int clickedPosition);
    }

    class StepsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mStepNumber;
        TextView mStepDescription;
        View mVerticalSeparator;
        StepsOnClickListener mClickListener;

        public StepsHolder(View itemView, StepsOnClickListener listener) {
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
            mClickListener.onItemClick(clickedPosition);

        }
    }
}
