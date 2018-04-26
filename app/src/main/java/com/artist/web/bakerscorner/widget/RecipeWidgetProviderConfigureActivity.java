package com.artist.web.bakerscorner.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.database.RecipeContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * The configuration screen for the {@link RecipeWidgetProvider RecipeWidgetProvider} AppWidget.
 */
public class RecipeWidgetProviderConfigureActivity extends Activity implements AdapterView.OnItemSelectedListener {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @BindView(R.id.recipe_spinner)
    Spinner mAppWidgetSpinner;

    public RecipeWidgetProviderConfigureActivity() {
        super();
    }


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.recipe_widget_provider_configure);
        mAppWidgetSpinner.setOnItemSelectedListener(this);

        populateSpinnerValues();

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

    }

    private void populateSpinnerValues() {
        List<String> recipeNames = new ArrayList<>();
        Uri fetchUri = RecipeContract.IngredientEntry.CONTENT_URI;
        Cursor returnCursor = getContentResolver().query(fetchUri,
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
        returnCursor.close();

        //creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, recipeNames);

        //Drop down layout style -list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching dataAdapter to spinner
        mAppWidgetSpinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        final Context context = RecipeWidgetProviderConfigureActivity.this;

        //on selecting a spinner item
        String recipeName = parent.getItemAtPosition(pos).toString();

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RecipeWidgetProvider.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

