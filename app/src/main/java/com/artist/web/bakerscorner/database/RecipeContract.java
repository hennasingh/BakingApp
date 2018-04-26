package com.artist.web.bakerscorner.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by User on 23-Apr-18.
 */

public class RecipeContract {

    //code knows through authority which provider to access
    static final String AUTHORITY = "com.artist.web.bakerscorner";

    //base URI = content:// +<authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    //possible path for accessing data, ingredients table
    static final String PATH_INGREDIENTS = "ingredients";

    //possible path for accesisng recipes
    static final String PATH_RECIPES = "recipes";

    public static final class RecipeEntry {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        public static final String TABLE_NAME = "recipes";

        public static final String COLUMN_RECIPE_NAME = "recipeName";

        public static final String COLUMN_RECIPE_SERVINGS = "recipeServing";
    }

    public static final class IngredientEntry implements BaseColumns {

        //content Uri = base Uri + Path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();

        public static final String TABLE_NAME = "ingredients";

        public static final String COLUMN_INGREDIENT_NAME = "ingredientName";

        public static final String COLUMN_INGREDIENT_QUANTITY = "ingredientQuantity";

        public static final String COLUMN_INGREDIENT_MEASURE = "ingredientMeasure";

        public static final String COLUMN_RECIPE_NAME = "recipeName";
    }


}
