package com.itrided.android.bakerstreet;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.databinding.ActivityRecipeBinding;
import com.itrided.android.bakerstreet.recipe.RecipePagerAdapter;
import com.itrided.android.bakerstreet.recipe.RecipeViewModel;

public class RecipeActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPE = "EXTRA_RECIPE";

    ActivityRecipeBinding binding;

    private RecipeViewModel recipeViewModel;
    private RecipePagerAdapter recipePagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        setupToolbar();
        setupViewModel();
        setupAdapter();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupAdapter() {
        recipePagerAdapter = new RecipePagerAdapter(this, getSupportFragmentManager());
        binding.viewpager.setAdapter(recipePagerAdapter);
        binding.slidingTl.setupWithViewPager(binding.viewpager);
    }

    private void setupViewModel() {
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.setRecipe(retrieveRecipe());

        recipeViewModel.getRecipe().observe(this, (recipe -> {
            if (recipe == null) {
                return;
            }

            setTitle(recipe.getName());
        }));
    }

    @Nullable
    private Recipe retrieveRecipe() {
        final Intent intent = this.getIntent();
        if (intent.hasExtra(EXTRA_RECIPE)) {
            return intent.getParcelableExtra(EXTRA_RECIPE);
        } else {
            return null;
        }
    }
}
