package com.itrided.android.bakerstreet.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.itrided.android.bakerstreet.R;
import com.itrided.android.bakerstreet.RecipeListActivity;

import java.util.ArrayList;

import static com.itrided.android.bakerstreet.widget.RecipeIngredientsWidget.EXTRA_WIDGET_ID;
import static com.itrided.android.bakerstreet.widget.RecipeIngredientsWidget.EXTRA_WIDGET_INGREDIENTS;
import static com.itrided.android.bakerstreet.widget.RecipeIngredientsWidget.EXTRA_WIDGET_REQUESTED_RECIPE;

public class RecipeWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private int appWidgetId;
    private Context context;
    private ArrayList<String> ingredients;

    public RecipeWidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.appWidgetId = intent.getIntExtra(EXTRA_WIDGET_ID, 0);
        this.ingredients = intent.getStringArrayListExtra(EXTRA_WIDGET_INGREDIENTS);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget_recipe_ingredient);
        remoteViews.setTextViewText(R.id.ingredient_tv, ingredients.get(position));
//        CharSequence recipeName = RecipeIngredientsWidgetConfigureActivity
//                .loadRecipePreference(context, appWidgetId);
        final CharSequence recipeName = "Brownies";
        final Intent intent = new Intent(context, RecipeListActivity.class);
        intent.putExtra(EXTRA_WIDGET_REQUESTED_RECIPE, recipeName);
        remoteViews.setOnClickFillInIntent(R.id.ingredient_tv, intent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(context.getPackageName(), R.layout.item_widget_recipe_ingredient);
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
