package com.itrided.android.bakerstreet;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.itrided.android.bakerstreet.library.RecipeListAdapter;
import com.itrided.android.bakerstreet.library.RecipeListViewModel;
import com.itrided.android.bakerstreet.library.RecipeListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity {

    @BindView(R.id.recipes_rv)
    RecyclerView recipesRv;

    private RecipeListViewModel recipeListViewModel;
    private RecipeListAdapter recipeListAdapter;

    //region API Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

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
        recipesRv.setAdapter(recipeListAdapter);
    }
}
