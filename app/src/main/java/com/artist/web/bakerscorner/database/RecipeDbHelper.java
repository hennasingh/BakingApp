package com.artist.web.bakerscorner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 23-Apr-18.
 */

public class RecipeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ingredients.db";

    private static final int DATABASE_VERSION = 1;

    RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " +
                RecipeContract.IngredientEntry.TABLE_NAME + " (" +
                RecipeContract.IngredientEntry._ID + " INTEGER PRIMARY KEY," +
                RecipeContract.IngredientEntry.COLUMN_RECIPE_NAME + " TEXT NOT NULL," +
                RecipeContract.IngredientEntry.COLUMN_INGREDIENT_NAME + " TEXT NOT NULL," +
                RecipeContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY + " REAL NOT NULL," +
                RecipeContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE + " TEXT NOT NULL," +
                ")";
        sqLiteDatabase.execSQL(CREATE_INGREDIENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeContract.IngredientEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
