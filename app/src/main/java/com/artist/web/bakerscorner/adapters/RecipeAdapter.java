package com.artist.web.bakerscorner.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.models.Recipes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 07-Apr-18.
 */

public class RecipeAdapter extends  RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private final RecipeAdapterOnClickListener mRecipeAdapterOnClickListener;
    private List<Recipes> mRecipesList;
    private Context context;

    public RecipeAdapter(ArrayList<Recipes> recipes, RecipeAdapterOnClickListener mListener, Context context) {
        mRecipesList = recipes;
        mRecipeAdapterOnClickListener = mListener;
        this.context = context;
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

        if (!TextUtils.isEmpty(displayRecipe.getImage())) {
            Picasso.with(context)
                    .load(displayRecipe.getImage())
                    .placeholder(getImage(displayRecipe.getRecipeName()))
                    .error(getImage(displayRecipe.getRecipeName()))
                    .into(holder.mRecipeImage);
        } else {
            holder.mRecipeImage.setImageResource(getImage(displayRecipe.getRecipeName()));
        }

    }

    private int getImage(String recipeName) {
        int imageId;
        if (recipeName.contains("Pie")) {
            imageId = R.drawable.ic_pie;
        } else if (recipeName.contains("Brownie")) {
            imageId = R.drawable.ic_brownie;
        } else if (recipeName.contains("Cake")) {
            imageId = R.drawable.ic_cakes;
        } else if (recipeName.contains("Cheese")) {
            imageId = R.drawable.ic_cheese;
        } else {
            imageId = R.drawable.ic_chef;
        }
        return imageId;
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

        @BindView(R.id.imageViewRecipe)
        ImageView mRecipeImage;
        @BindView(R.id.textViewRecipeName)
        TextView mRecipeName;
        @BindView(R.id.textViewServing)
        TextView mRecipeServing;
        RecipeAdapterOnClickListener mRecipeAdapterListener;

        RecipeViewHolder(View itemView, RecipeAdapterOnClickListener mListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
