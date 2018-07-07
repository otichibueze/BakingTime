package com.chibusoft.bakingtime.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.chibusoft.bakingtime.Data.Ingredients_Contract;
import com.chibusoft.bakingtime.Data.RecipeProvider.Ingredients;
import com.chibusoft.bakingtime.R;

/**
 * Created by EBELE PC on 7/4/2018.
 */

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListRemoteViewsFactory(this.getApplicationContext(),intent);
    }

}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory  {
    Context mContext;
    Cursor mCursor;
    long ID;


    public ListRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;

        //Save value from intent
        ID = intent.getLongExtra(Bake_widget.EXTRA_RECIPE_ID, Bake_widget.DEFAULT);
    }

    @Override
    public void onCreate() {

    }


    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        Log.d("DataSetChanged","The id from intend is " + ID);
        //query database to get info
        Uri ingredient_url = Ingredients.CONTENT_URI.buildUpon().appendPath(ID + "").build();
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                ingredient_url,
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
        int recipeIDIndex = mCursor.getColumnIndex(Ingredients_Contract.COLUMN_RECIPE_ID);
        int ingredientNameIndex = mCursor.getColumnIndex(Ingredients_Contract.COLUMN_INGREDIENT);
        int ingredientMeasureIndex = mCursor.getColumnIndex(Ingredients_Contract.COLUMN_MEASURE);
        int ingredientQuantityIndex = mCursor.getColumnIndex(Ingredients_Contract.COLUMN_QUANTITY);


        int recipeID = mCursor.getInt(recipeIDIndex);
        String ingredientName = mCursor.getString(ingredientNameIndex);
        String ingredientMeasure = mCursor.getString(ingredientMeasureIndex);
        String ingredientQuantity = mCursor.getString(ingredientQuantityIndex);


        //Here we create a reference to the item view layout . NOte not the layout that has list
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.bake_ingredient_widget_item);

        views.setTextViewText(R.id.widget_ingredient_name, String.valueOf(ingredientName));

        views.setTextViewText(R.id.widget_ingredient_measure_quantity, String.valueOf(ingredientMeasure + " " + ingredientQuantity));

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
