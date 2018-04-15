package com.artist.web.bakerscorner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.data.Ingredients;
import com.artist.web.bakerscorner.data.Recipes;

import java.util.List;

/**
 * Created by User on 11-Apr-18.
 */

public class IngredientsFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe_selected";
    Recipes mdisplayedRecipe;

    public static IngredientsFragment newInstance(Recipes recipe) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        IngredientsFragment fragment = new IngredientsFragment();
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
        View ingredientView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        TextView mTextViewIngredients = ingredientView.findViewById(R.id.textViewIngredient);
        List<Ingredients> ingredientsList = mdisplayedRecipe.getIngredientsList();

        String detailIngredients = "";
        if (ingredientsList != null) {
            for (int i = 0; i < ingredientsList.size(); i++) {
                String ingred = ingredientsList.get(i).getIngredient();
                double quantity = ingredientsList.get(i).getQuantity();
                String measure = ingredientsList.get(i).getMeasure();
                detailIngredients = detailIngredients.concat(ingred + " ( " + quantity + " " + measure + " ) " + "\n");
            }
        }
        mTextViewIngredients.setText(detailIngredients);

        return ingredientView;
    }
}
