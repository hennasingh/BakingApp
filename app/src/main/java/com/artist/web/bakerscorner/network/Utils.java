package com.artist.web.bakerscorner.network;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.artist.web.bakerscorner.database.RecipeContract;
import com.artist.web.bakerscorner.models.Ingredients;

import java.util.ArrayList;

/**
 * Created by User on 05-Apr-18.
 */

public class Utils {

    static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * Utility method to convert first letter of a string to Uppercase
     *
     * @param word
     * @return word with first letter uppercase
     */
    public static String convertStringToFirstCapital(String word) {
        return word.toUpperCase().charAt(0) + word.substring(1).toLowerCase();
    }

    public static void writeIngredientsToDb(ArrayList<Ingredients> ingredientsList, String recipeName,
                                            Context context) {

        checkDatabaseAndDelete(recipeName, context);
        ContentValues ingredientsValues = new ContentValues();

        for (Ingredients ingredient : ingredientsList) {
            ingredientsValues.put(RecipeContract.IngredientEntry.COLUMN_RECIPE_NAME, recipeName);
            ingredientsValues.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_NAME, ingredient.getIngredient());
            ingredientsValues.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE, ingredient.getMeasure());
            ingredientsValues.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY, ingredient.getQuantity());

            context.getContentResolver().insert(RecipeContract.IngredientEntry.CONTENT_URI, ingredientsValues);
        }
    }

    private static void checkDatabaseAndDelete(String recipeName, Context context) {

        Uri fetchUri = RecipeContract.IngredientEntry.CONTENT_URI;
        fetchUri = fetchUri.buildUpon().appendPath(recipeName).build();

        Cursor retCursor = context.getContentResolver().query(fetchUri,
                null,
                null,
                null,
                null);

        if (retCursor.getCount() > 0) {
            context.getContentResolver().delete(fetchUri, null, null);
        }
    }

}
