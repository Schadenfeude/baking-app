package com.itrided.android.bakerstreet.widget;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.itrided.android.bakerstreet.R;
import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.databinding.WidgetRecipeIngredientConfigBinding;
import com.itrided.android.bakerstreet.library.RecipeListViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientsWidgetConfigureActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "com.itrided.android.bakerstreet.widget.RecipeIngredientsWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";

    private WidgetRecipeIngredientConfigBinding binding;
    private ArrayAdapter<String> adapter;
    private RecipeListViewModel recipeListViewModel;

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public RecipeIngredientsWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        binding = DataBindingUtil.setContentView(this, R.layout.widget_recipe_ingredient_config);

        recipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        recipeListViewModel.getRecipes().observe(this, recipes -> {
            final List<String> recipeList = new ArrayList<>();
            for (Recipe recipe : recipes) {
                recipeList.add(recipe.getName());
            }
            adapter = new ArrayAdapter<>(this, R.layout.item_recipe_ingredient_config_spinner, recipeList);
            adapter.setDropDownViewResource(R.layout.item_recipe_ingredient_config_spinner_dropdown);
            binding.recipeChooseSpinner.setAdapter(adapter);

            final String storedValue = loadRecipePreference(RecipeIngredientsWidgetConfigureActivity.this, appWidgetId);
            if (storedValue != null) {
                binding.recipeChooseSpinner.setSelection(adapter.getPosition(storedValue));
            }
        });

        binding.recipeConfirmBtn.setOnClickListener(v -> {
            saveRecipePreference(RecipeIngredientsWidgetConfigureActivity.this, appWidgetId,
                    adapter.getItem(binding.recipeChooseSpinner.getSelectedItemPosition()));

            final AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(RecipeIngredientsWidgetConfigureActivity.this);

            RecipeIngredientsWidget
                    .updateWidget(RecipeIngredientsWidgetConfigureActivity.this,
                            appWidgetManager,
                            appWidgetId);

            final Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        });

        setResult(RESULT_CANCELED);

        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    static void saveRecipePreference(Context context, int appWidgetId, String text) {
        final SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    static String loadRecipePreference(Context context, int appWidgetId) {
        final SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

        return prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
    }

    static void deleteRecipePreference(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }
}
