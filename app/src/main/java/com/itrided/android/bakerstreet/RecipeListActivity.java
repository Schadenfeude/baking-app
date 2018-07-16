package com.itrided.android.bakerstreet;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itrided.android.bakerstreet.databinding.ActivityRecipeListBinding;
import com.itrided.android.bakerstreet.library.RecipeListAdapter;
import com.itrided.android.bakerstreet.library.RecipeListViewModel;
import com.itrided.android.bakerstreet.library.RecipeListener;

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
    }

    //endregion API Methods
    private void setupViewModel() {
        recipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        recipeListViewModel.getRecipes().observe(this, (recipes -> {
            if (recipeListAdapter == null) {
                return;
            }

            recipeListAdapter.setRecipes(recipes);
        }));
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
}
