package com.itrided.android.bakerstreet;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.databinding.ActivityRecipeListBinding;
import com.itrided.android.bakerstreet.library.RecipeListAdapter;
import com.itrided.android.bakerstreet.library.RecipeListViewModel;
import com.itrided.android.bakerstreet.library.RecipeListener;

import java.util.List;

import static com.itrided.android.bakerstreet.widget.RecipeIngredientsWidget.EXTRA_WIDGET_REQUESTED_RECIPE;

public class RecipeListActivity extends AppCompatActivity {

    private ActivityRecipeListBinding binding;
    private RecipeListViewModel recipeListViewModel;
    private RecipeListAdapter recipeListAdapter;

    //region API Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list);

        setupViewModel();
        setupAdapter();

        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_WIDGET_REQUESTED_RECIPE)) {
            recipeListViewModel.setRequestedRecipeFromWidget(
                    intent.getStringExtra(EXTRA_WIDGET_REQUESTED_RECIPE));
        }
    }

    //endregion API Methods
    private void setupViewModel() {
        recipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        recipeListViewModel.getRecipes().observe(this, recipes -> {
            if (recipeListAdapter == null) {
                return;
            }

            recipeListAdapter.setRecipes(recipes);

            final String requestedRecipe = recipeListViewModel.getRequestedRecipeFromWidget();
            if (!TextUtils.isEmpty(requestedRecipe)) {
                openRecipeFromWidget(requestedRecipe, recipes);
            }
        });
    }

    private void setupAdapter() {
        final RecipeListener recipeListener = recipe -> {
            final Intent startRecipesIntent = new Intent(this, RecipeActivity.class);
            startRecipesIntent.putExtra(RecipeActivity.EXTRA_RECIPE, recipe);

            startActivity(startRecipesIntent);
        };

        recipeListAdapter = new RecipeListAdapter(recipeListener);
        binding.recipesRv.setAdapter(recipeListAdapter);
    }

    private void openRecipeFromWidget(@NonNull String recipeString, List<Recipe> recipes) {
        for (final Recipe recipe : recipes) {
            if (recipeString.equals(recipe.getName())) {
                final Intent startRecipesIntent = new Intent(this, RecipeActivity.class);
                startRecipesIntent.putExtra(RecipeActivity.EXTRA_RECIPE, recipe);

                startActivity(startRecipesIntent);
            }
        }
    }
}
