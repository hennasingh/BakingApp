package com.artist.web.bakerscorner.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.Spinner;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.database.RecipeContract;

import java.util.ArrayList;
import java.util.List;

/**
 * The configuration screen for the {@link RecipeWidgetProvider RecipeWidgetProvider} AppWidget.
 */
public class RecipeWidgetConfigureActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String prefFile = "storeRecipes";
    private static final String RECIPE_AT_ZERO = "Nutella Pie";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Spinner mAppWidgetSpinner;
    Cursor returnCursor;
    ArrayAdapter<String> dataAdapter;
    SharedPreferences mPreferences;
    String recipeId;
    private RemoteViews mRemoteViews;
    private boolean userIsInteracting;

    public RecipeWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.recipe_widget_configure);
        mAppWidgetSpinner = findViewById(R.id.recipe_spinner);

        mPreferences = getSharedPreferences(prefFile, MODE_PRIVATE);
        populateSpinnerValues();

        mAppWidgetSpinner.setOnItemSelectedListener(this);


        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            recipeId = extras.getString(RecipeWidgetProvider.RECIPE_NAME);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();

        }

    }

    private void populateSpinnerValues() {
        List<String> recipeNames = new ArrayList<>();
        Uri fetchUri = RecipeContract.IngredientEntry.CONTENT_URI;
        returnCursor = getContentResolver().query(fetchUri,
                new String[]{"DISTINCT " + RecipeContract.IngredientEntry.COLUMN_RECIPE_NAME},
                null,
                null,
                null);

        if (returnCursor.getCount() > 0) {

            returnCursor.moveToFirst();
            do {
                recipeNames.add(returnCursor.getString(returnCursor.getColumnIndex(
                        RecipeContract.IngredientEntry.COLUMN_RECIPE_NAME)));

            } while (returnCursor.moveToNext());
        }

        //creating adapter for spinner
        dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, recipeNames);

        //Drop down layout style -list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching dataAdapter to spinner
        mAppWidgetSpinner.setAdapter(dataAdapter);

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (userIsInteracting) {

            final Context context = RecipeWidgetConfigureActivity.this;

            //on selecting a spinner item
            String recipeName = parent.getItemAtPosition(pos).toString();
            if (pos == 0) {
                recipeName = RECIPE_AT_ZERO;
            }

            SharedPreferences.Editor preferencesEditor = mPreferences.edit();

            preferencesEditor.putString(RecipeWidgetProvider.RECIPE_NAME, recipeName);
            preferencesEditor.apply();


            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            resultValue.putExtra(RecipeWidgetProvider.RECIPE_NAME, recipeName);
            setResult(RESULT_OK, resultValue);

            // Build/Update widget
            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            // This is equivalent to your WidgetProvider.updateAppWidget()
            appWidgetManager.updateAppWidget(mAppWidgetId,
                    RecipeWidgetProvider.buildRemoteViews(getApplicationContext(), recipeName, mAppWidgetId));

            // Updates the collection view, not necessary the first time
            appWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetId, R.id.ingredients_listview);

            //Destroy the Activity
            finish();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}

