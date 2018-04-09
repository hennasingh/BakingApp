package com.artist.web.bakerscorner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.data.Recipes;

import java.util.List;

/**
 * Created by User on 07-Apr-18.
 */

public class RecipeAdapter extends  RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private List<Recipes> mRecipesList;

    public RecipeAdapter(List<Recipes> recipes){
        mRecipesList = recipes;
    }
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_recipes_list,parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
         Recipes displayRecipe = mRecipesList.get(position);
         holder.mRecipeName.setText(displayRecipe.getRecipeName());
         holder.mRecipeServing.setText(displayRecipe.getServings());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

     class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView mRecipeImage;
        TextView mRecipeName;
        TextView mRecipeServing;

         RecipeViewHolder(View itemView) {
            super(itemView);
            mRecipeImage = itemView.findViewById(R.id.imageViewRecipe);
            mRecipeName = itemView.findViewById(R.id.textViewRecipeName);
            mRecipeServing = itemView.findViewById(R.id.textViewServing);

        }
    }
}
