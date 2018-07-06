package com.chibusoft.bakingtime.widget;

/**
 * Created by EBELE PC on 6/25/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.chibusoft.bakingtime.Data.RecipeProvider.Recipes;
import com.chibusoft.bakingtime.Data.Recipe_Contract;
import com.chibusoft.bakingtime.R;

import java.io.Console;

import timber.log.Timber;

/**
 * Created by EBELE PC on 6/25/2018.
 */

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}


class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory  {

    Context mContext;
    Cursor mCursor;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

       }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        //query database to get info
        Uri recipe_url = Recipes.CONTENT_URI;
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                recipe_url,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDestroy() {
        if (mCursor == null) return;
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        //int idIndex = mCursor.getColumnIndex(Recipe_Contract.COLUMN_ID);
        int recipeIDIndex = mCursor.getColumnIndex(Recipe_Contract.COLUMN_RECIPE_ID);
        int recipeNameIndex = mCursor.getColumnIndex(Recipe_Contract.COLUMN_NAME);


       // long id = mCursor.getLong(idIndex);
        int recipeID = mCursor.getInt(recipeIDIndex);
        String recipeName = mCursor.getString(recipeNameIndex);



        //Here we create a reference to the item view layout . NOte not the layout that has grid
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.bake_recipe_widget_item);

        // Update the image and set values from item.xml that widget will use
        //Note images dont need to come from content provider they can be reference directly from main application if local available
        int imgRes = 0;
        if(recipeID == 1) imgRes = R.drawable.nutella;
        else if(recipeID == 2) imgRes = R.drawable.brownies;
        else if(recipeID == 3) imgRes = R.drawable.yellowcake;
        else if(recipeID == 4) imgRes = R.drawable.cheesecake;
        else imgRes = R.drawable.cheesecake;

        views.setImageViewResource(R.id.recipe_widget_img, imgRes);
       views.setTextViewText(R.id.recipe_widget_name, String.valueOf(recipeName));


        // Fill in the onClick PendingIntent Template using the specific recipe id for each item individually
           Bundle extras = new Bundle();
            extras.putLong(Bake_widget.EXTRA_RECIPE_ID, recipeID);
           Intent fillInIntent = new Intent();
           fillInIntent.putExtras(extras);
           //Set on click filll in event to an item on the layout.xml for each item
           views.setOnClickFillInIntent(R.id.recipe_widget_img, fillInIntent);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
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
