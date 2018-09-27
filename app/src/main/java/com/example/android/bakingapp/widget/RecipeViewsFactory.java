package com.example.android.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;

/**
 * Created by angelov on 9/27/2018.
 */

public class RecipeViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private String[] ingredientsToShow;
    private Context mContext;


    public RecipeViewsFactory(Context inputContext) {
        mContext = inputContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredientsToShow = BakingAppWidget.loadedIngredients;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientsToShow != null ? ingredientsToShow.length : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget_list_item);
        views.setTextViewText(R.id.ingredient_item, ingredientsToShow[position]);
        return views;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
