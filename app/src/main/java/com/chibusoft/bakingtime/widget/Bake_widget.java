package com.chibusoft.bakingtime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.chibusoft.bakingtime.MainActivity;
import com.chibusoft.bakingtime.R;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link GridWidgetService}
 */
public class Bake_widget extends AppWidgetProvider {

    private static final String ACTION_OPEN_LIST = "com.chibusoft.bakingtime.widget.action.open_list";
    private static final String ACTION_OPEN_GRID = "com.chibusoft.bakingtime.widget.action.open_grid";
    public static final String EXTRA_RECIPE_ID = "com.chibusoft.bakingtime.widget.extra.RECIPE_ID";
    public static final long DEFAULT = 1;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,int appWidgetId)
    {

        appWidgetManager = AppWidgetManager.getInstance(context);


        RemoteViews rv;

        //To be able to distinguish between multiple instances of the same AppWidgetProvider you must pass appWidgetId to
        //the method that load the remote view
        rv = getRecipeGridRemoteView(context, appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //This method is for updating values that the widget has
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
          updateAppWidget(context, appWidgetManager, appWidgetId);
       }
    }




    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    /**
     * Creates and returns the RemoteViews to be displayed in the GridView mode widget
     *
     * @param context The context
     * @param appWidgetId of widget we want to update
     * @return The RemoteViews for the GridView mode widget
     */
    public static RemoteViews getRecipeGridRemoteView(Context context, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake_recipes_widget);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);

        // Set click to launch the ListService view
        Intent showListIntent = new Intent(context, Bake_widget.class);
        showListIntent.setAction(ACTION_OPEN_LIST);
        //when registering the “onClick” event (intent) you must add an extra value with the widget ID (appWidgetId)
        //use AppWidgetManager reference to save the widget we choose to update in intent extra
        showListIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        //note PendingIntent.getBroadcast to send broadcast to open remoteview button with onrecieve in this class will handle
        //pass the id of the widget we choose to update in the second parameter of pending intent method
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,appWidgetId,showListIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, pendingIntent);

        // Set the click to launch home activity
        Intent homeIntent = new Intent(context, MainActivity.class);
        // Widgets creating pending intent on view
        //note PendingIntent.getActivity to open activity
        PendingIntent pendingIntentHome = PendingIntent.getActivity(context, appWidgetId, homeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.home_widget_btn, pendingIntentHome);


        // Handle empty gardens
        views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        return views;
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the ListView mode widget
     *
     * @param context The context
     * @param id of recipe to open
     * @param appWidgetId of widget we want to update
     * @return The RemoteViews for the ListView mode widget
     */
    public static RemoteViews getRecipeListRemoteView(Context context,long id,int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake__ingredients_widget);

        // Set the ListWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(EXTRA_RECIPE_ID,id);
        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        //Failing to put this line below the intent extra might be lost and intent wont work
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.widget_listview, intent);
        // Handle empty gardens
        views.setEmptyView(R.id.widget_listview, R.id.empty_view);


        // Set the GridService to launch when clicked
        Intent showGridIntent = new Intent(context, Bake_widget.class);
        showGridIntent.setAction(ACTION_OPEN_GRID);
        // when registering the “onClick” event (intent) you must add an extra value with the widget ID (appWidgetId)
        //use AppWidgetManager reference to save the widget we choose to update in intent extra
        showGridIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // Widgets creating pending intent
        //pass the id of the widget we choose to update in the second parameter of pending intent method
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, showGridIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.back_widget_btn, pendingIntent);

        return views;
    }


    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
        if (intent.getAction().equals(ACTION_OPEN_LIST))
        {
            long id = intent.getLongExtra(EXTRA_RECIPE_ID, DEFAULT);
            //Get the widget we choose to update from intent extra
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,0);
            Log.d("Recieved","The id from intend is " + id);
            RemoteViews rv;

            //Start new remote view and passing id
             rv = getRecipeListRemoteView(context,id, appWidgetId);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            //Use the id appWidgetId we got from intent to update a particular widget
            appWidgetManager.updateAppWidget(appWidgetId,rv);
        }
        else if (intent.getAction().equals(ACTION_OPEN_GRID)){

            RemoteViews rv;

            //Get widget  we choose to update from intent extra
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,0);

            rv = getRecipeGridRemoteView(context,appWidgetId);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            //Use the id appWidgetId we got from intent to update a particular widget
            appWidgetManager.updateAppWidget(appWidgetId,rv);
        }

    }
}

