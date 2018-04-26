package com.artist.web.bakerscorner.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 07-Apr-18.
 */

public class Recipes implements Parcelable {

    public static final Parcelable.Creator<Recipes> CREATOR = new Parcelable.Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel source) {
            return new Recipes(source);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };
    @SerializedName("id")
    private int mRecipeId;
    @SerializedName("name")
    private String mRecipeName;
    @SerializedName("ingredients")
    private List<Ingredients> mIngredientsList;
    @SerializedName("steps")
    private ArrayList<Steps> mStepsList;
    @SerializedName("servings")
    private int mServings;
    @SerializedName("image")
    private String mImage;

    public Recipes(int recipeId, String recipeName, List<Ingredients> ingredientsList,
                   ArrayList<Steps> stepsList, int servings, String image) {
        mRecipeId = recipeId;
        mRecipeName = recipeName;
        mIngredientsList = ingredientsList;
        mStepsList = stepsList;
        mServings = servings;
        mImage = image;
    }

    Recipes(Parcel in) {
        this.mRecipeId = in.readInt();
        this.mRecipeName = in.readString();
        this.mIngredientsList = new ArrayList<Ingredients>();
        in.readList(this.mIngredientsList, Ingredients.class.getClassLoader());
        this.mStepsList = new ArrayList<Steps>();
        in.readList(this.mStepsList, Steps.class.getClassLoader());
        this.mServings = in.readInt();
        this.mImage = in.readString();
    }

    public int getRecipeId() {
        return mRecipeId;
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

    public ArrayList<Steps> getStepsList() {
        return mStepsList;
    }

    public void setStepsList(ArrayList<Steps> stepsList) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mRecipeId);
        dest.writeString(this.mRecipeName);
        dest.writeList(this.mIngredientsList);
        dest.writeList(this.mStepsList);
        dest.writeInt(this.mServings);
        dest.writeString(this.mImage);
    }
}
