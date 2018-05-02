package com.artist.web.bakerscorner.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.artist.web.bakerscorner.R;
import com.artist.web.bakerscorner.activities.RecipeListActivity;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link RecipeWidgetConfigureActivity RecipeWidgetConfigureActivity}
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String RECIPE_NAME = "recipeName";

    public static RemoteViews buildRemoteViews(Context context, String recipeName) {

        //construct remote views object
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        //Set the ListWidgetService intent to act as the adapter for the listview
        Intent listIntent = new Intent(context, ListWidgetService.class);
        listIntent.putExtra(RECIPE_NAME, recipeName);
        rv.setRemoteAdapter(R.id.ingredients_listview, listIntent);
        rv.setTextViewText(R.id.appwidget_text, recipeName);

        //set the RecipeListActivity intent to launch when text open app is clicked
        Intent appIntent = new Intent(context, RecipeListActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Widgets allow click handlers to only launch pending intents
        rv.setOnClickPendingIntent(R.id.textOpenApp, appPendingIntent);

        //set the ConfigureActivity to launch when text change recipe is clicked
        Intent recipeIntent = new Intent(context, RecipeWidgetConfigureActivity.class);
        PendingIntent recipePendingIntent = PendingIntent.getActivity(context, 0, recipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        rv.setOnClickPendingIntent(R.id.textNewRecipe, recipePendingIntent);
        // The empty view is displayed when the collection has no items. It should be a sibling
        // of the collection view.
        rv.setEmptyView(R.id.ingredients_listview, R.id.emptyView);

        return rv;
    }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName) {

        RemoteViews views = buildRemoteViews(context, recipeName);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, null);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, perform an action for it

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}

