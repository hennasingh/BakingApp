package com.artist.web.bakerscorner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.models.Ingredients;
import com.artist.web.bakerscorner.models.Recipes;
import com.artist.web.bakerscorner.network.Utils;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by User on 11-Apr-18.
 */

public class IngredientsFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe_selected";
    Recipes mdisplayedRecipe;
    @BindView(R.id.textViewIngredient)
    TextView mTextViewIngredients;
    @BindString(R.string.mIngredientsDescription)
    String mTextIngredients;
    @BindView(R.id.expandableView)
    ExpandableTextView mExpandableTextView;

    private Unbinder unbinder;

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

        unbinder = ButterKnife.bind(this, ingredientView);
        List<Ingredients> ingredientsList = mdisplayedRecipe.getIngredientsList();

        StringBuilder detailIngredients = new StringBuilder();
        for (Ingredients mIngredient : ingredientsList) {
            detailIngredients.append(
                    String.format(
                            mTextIngredients,
                            Utils.convertStringToFirstCapital(mIngredient.getIngredient()),
                            Html.fromHtml(Double.toString(mIngredient.getQuantity())),
                            mIngredient.getMeasure().toLowerCase()
                    )
            );

        }

        //throws an error, can you help figure this out
        mExpandableTextView.setText(detailIngredients);
        mExpandableTextView.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {

            }
        });
        // mTextViewIngredients.setText(detailIngredients);

        return ingredientView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
