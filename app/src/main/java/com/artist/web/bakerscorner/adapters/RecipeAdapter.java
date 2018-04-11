package com.artist.web.bakerscorner.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.data.Recipes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 07-Apr-18.
 */

public class RecipeAdapter extends  RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private final RecipeAdapterOnClickListener mRecipeAdapterOnClickListener;
    private List<Recipes> mRecipesList;

    public RecipeAdapter(ArrayList<Recipes> recipes, RecipeAdapterOnClickListener mListener) {
        mRecipesList = recipes;
        mRecipeAdapterOnClickListener = mListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_item_list, parent, false);
        return new RecipeViewHolder(view, mRecipeAdapterOnClickListener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
         Recipes displayRecipe = mRecipesList.get(position);
        Log.e(TAG, "The Recipe to be displayed is  " + displayRecipe);
         holder.mRecipeName.setText(displayRecipe.getRecipeName());
        holder.mRecipeServing.setText(String.valueOf(displayRecipe.getServings()));

    }

    @Override
    public int getItemCount() {
        if (mRecipesList == null) return 0;
        return mRecipesList.size();
    }

    public interface RecipeAdapterOnClickListener {
        void onItemClick(int clickedPosition);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mRecipeImage;
        TextView mRecipeName;
        TextView mRecipeServing;
        RecipeAdapterOnClickListener mRecipeAdapterListener;

        RecipeViewHolder(View itemView, RecipeAdapterOnClickListener mListener) {
            super(itemView);
            mRecipeImage = itemView.findViewById(R.id.imageViewRecipe);
            mRecipeName = itemView.findViewById(R.id.textViewRecipeName);
            mRecipeServing = itemView.findViewById(R.id.textViewServing);
            mRecipeAdapterListener = mListener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mRecipeAdapterListener.onItemClick(clickedPosition);

        }
    }
}
