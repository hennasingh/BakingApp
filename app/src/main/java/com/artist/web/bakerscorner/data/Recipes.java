package com.artist.web.bakerscorner.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 07-Apr-18.
 */

public class Recipes {

    @SerializedName("id")
    private int mRecipeId;

    @SerializedName("name")
    private String mRecipeName;

    @SerializedName("ingredients")
    private List<Ingredients> mIngredientsList;

    @SerializedName("steps")
    private List<Steps> mStepsList;

    @SerializedName("servings")
    private int mServings;

    @SerializedName("image")
    private String mImage;

    public int getRecipeId() {
        return mRecipeId;
    }

    public Recipes(int recipeId, String recipeName, List<Ingredients> ingredientsList,
                   List<Steps> stepsList, int servings, String image) {
        mRecipeId = recipeId;
        mRecipeName = recipeName;
        mIngredientsList = ingredientsList;
        mStepsList = stepsList;
        mServings = servings;
        mImage = image;
    }

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
    }

    public List<Ingredients> getIngredientsList() {
        return mIngredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        mIngredientsList = ingredientsList;
    }

    public List<Steps> getStepsList() {
        return mStepsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        mStepsList = stepsList;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int servings) {
        mServings = servings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}
