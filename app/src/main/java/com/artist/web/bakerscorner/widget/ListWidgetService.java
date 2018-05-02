package com.artist.web.bakerscorner.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.database.RecipeContract;

/**
 * Created by User on 25-Apr-18.
 */

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    Context mContext;
    Cursor mCursor;
    String mRecipeName;
    String defaultRecipe = "Nutella Pie";

    ListRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String recipe = extras.getString(RecipeWidgetProvider.RECIPE_NAME);
            Log.d(TAG, "Recipe received from Intents " + recipe);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        //get all ingredients for asked recipes
        SharedPreferences mPreferences = mContext.getSharedPreferences(RecipeWidgetConfigureActivity.prefFile, Context.MODE_PRIVATE);
        mRecipeName = mPreferences.getString(RecipeWidgetProvider.RECIPE_NAME, defaultRecipe);

        Uri INGREDIENT_URI = RecipeContract.IngredientEntry.CONTENT_URI.buildUpon().appendPath(mRecipeName).build();

        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(INGREDIENT_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        mCursor.moveToPosition(position);
        String ingredient = mCursor.getString(mCursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_NAME));
        String measure = mCursor.getString(mCursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE));
        double quantity = mCursor.getDouble(mCursor.getColumnIndex(RecipeContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY));

        remoteViews.setTextViewText(R.id.ingredientNameTextView, ingredient);
        remoteViews.setTextViewText(R.id.ingredientQuantityTextView, String.valueOf(quantity));
        remoteViews.setTextViewText(R.id.ingredientMeasureTextView, measure);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

