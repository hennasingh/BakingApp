package com.artist.web.bakerscorner.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by User on 23-Apr-18.
 */

public class RecipesContentProvider extends ContentProvider {

    //integer constants for directories and single tasks
    public static final int INGREDIENTS = 100;

    public static final int INGREDIENT_WITH_NAME = 101;
    public static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String TAG = RecipesContentProvider.class.getSimpleName();
    RecipeDbHelper mRecipeDbHelper;
    SQLiteDatabase databaseReadable, databaseWritable;

    //static buildUriMatcher method that associates URI with their int match

    public static UriMatcher buildUriMatcher() {
        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //add matches with addUri(String authority, String path, int code)
        //directories
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_INGREDIENTS, INGREDIENTS);

        //single movie
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_INGREDIENTS + "/*", INGREDIENT_WITH_NAME);

        return uriMatcher;
    }

    /* onCreate() is where you should initialize anything you’ll need to setup
   your underlying data source.
   In this case, you’re working with a SQLite database, so you’ll need to
   initialize a DbHelper to gain access to it.
    */
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mRecipeDbHelper = new RecipeDbHelper(context);
        databaseReadable = mRecipeDbHelper.getReadableDatabase();
        databaseWritable = mRecipeDbHelper.getWritableDatabase();
        return true;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {


        int match = sUriMatcher.match(uri);
        Cursor returnCursor;

        switch (match) {
            case INGREDIENTS:
                returnCursor = databaseReadable.query(RecipeContract.IngredientEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case INGREDIENT_WITH_NAME:
                String name = uri.getPathSegments().get(1);

                selection = RecipeContract.IngredientEntry.COLUMN_RECIPE_NAME + "=?";
                selectionArgs = new String[]{name};

                returnCursor = databaseReadable.query(RecipeContract.IngredientEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {


        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case INGREDIENTS:
                long id = databaseWritable.insert(RecipeContract.IngredientEntry.TABLE_NAME,
                        null, contentValues);

                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(RecipeContract.IngredientEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        int rowDeleted;

        /*
         * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
         * deleted. However, if we do pass null and delete all of the rows in the table, we won't
         * know how many rows were deleted. According to the documentation for SQLiteDatabase,
         * passing "1" for the selection will delete all rows and return the number of rows
         * deleted, which is what the caller of this method expects.
         */
        if (null == selection) selection = "1";
        switch (match) {
            case INGREDIENTS:
                rowDeleted = databaseWritable.delete(RecipeContract.IngredientEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case INGREDIENT_WITH_NAME:
                String recipe = uri.getPathSegments().get(1);
                selection = RecipeContract.IngredientEntry.COLUMN_RECIPE_NAME + " =?";
                selectionArgs = new String[]{recipe};
                rowDeleted = databaseWritable.delete(RecipeContract.IngredientEntry.TABLE_NAME,
                        selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        Log.d(TAG, "No of rows deleted " + rowDeleted);

        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


}
