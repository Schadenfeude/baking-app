package com.itrided.android.bakerstreet.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.itrided.android.bakerstreet.BakerStreetApp;
import com.itrided.android.bakerstreet.R;
import com.itrided.android.bakerstreet.RecipeListActivity;
import com.itrided.android.bakerstreet.data.model.Ingredient;
import com.itrided.android.bakerstreet.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

public class RecipeIngredientsWidget extends AppWidgetProvider {

    public static final String EXTRA_WIDGET_REQUESTED_RECIPE = "widgetRequestedRecipe";
    public static final String EXTRA_WIDGET_INGREDIENTS = "widgetIngredients";
    public static final String EXTRA_WIDGET_ID = "widgetId";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RecipeIngredientsWidgetConfigureActivity.deleteRecipePreference(context, appWidgetId);
        }
    }

    @SuppressLint("CheckResult")
    static void updateWidget(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager,
                             int widgetId) {
        final CharSequence recipeName = RecipeIngredientsWidgetConfigureActivity.loadRecipePreference(context, widgetId);
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredient_list);

        BakerStreetApp.getRecipeService().getRecipes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Recipe>>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onSuccess(List<Recipe> results) {
                        if (results.size() == 0) {
                            return;
                        }

                        final Intent intent = new Intent(context, RecipeIngredientsWidgetService.class);
                        setupMainViews(context, views, recipeName);
                        Observable.fromIterable(results)
                                .filter(recipe -> recipe.getName().contentEquals(recipeName))
                                .flatMapIterable(Recipe::getIngredients)
                                .map(ingredient -> formatIngredient(context, ingredient))
                                .toList()
                                .subscribeWith(new SingleObserver<List<String>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(List<String> ingredients) {
                                        intent.putExtra(EXTRA_WIDGET_INGREDIENTS, new ArrayList<>(ingredients));
                                        intent.putExtra(EXTRA_WIDGET_ID, widgetId);

                                        views.setRemoteAdapter(R.id.recipe_list_lv, intent);
                                        appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.recipe_list_lv);
                                        appWidgetManager.updateAppWidget(widgetId, views);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

        appWidgetManager.updateAppWidget(widgetId, views);
    }

    private static void setupMainViews(@NonNull Context context, @NonNull RemoteViews views,
                                       @Nullable CharSequence recipeName) {

        views.setTextViewText(R.id.recipe_name_tv,
                String.format(context.getString(R.string.widget_recipe_title), recipeName));
        views.setEmptyView(R.id.recipe_list_lv, R.id.loading);

        final Intent listIntent = new Intent(context, RecipeListActivity.class);
        final PendingIntent listPendingIntent = PendingIntent
                .getActivity(context, 0, listIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recipe_list_lv, listPendingIntent);

        final Intent titleIntent = new Intent(context, RecipeListActivity.class);
        titleIntent.putExtra(EXTRA_WIDGET_REQUESTED_RECIPE, recipeName);
        final PendingIntent titlePendingIntent = PendingIntent
                .getActivity(context, 0, titleIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.recipe_name_tv, titlePendingIntent);
    }

    private static String formatIngredient(@NonNull Context context, @NonNull Ingredient ingredient) {
        return String.format(context.getString(R.string.widget_ingredient_item_text),
                ingredient.getName(),
                Double.toString(ingredient.getQuantity()),
                ingredient.getMeasure());
    }
}
