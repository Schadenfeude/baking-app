package com.itrided.android.bakerstreet;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.databinding.ActivityRecipeBinding;
import com.itrided.android.bakerstreet.recipe.RecipePagerAdapter;
import com.itrided.android.bakerstreet.recipe.RecipeViewModel;
import com.itrided.android.bakerstreet.recipe.step.StepFragmentAdapter;
import com.itrided.android.bakerstreet.recipe.step.StepListViewModel;

public class RecipeActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPE = "EXTRA_RECIPE";
    private static final int DEFAULT_STEP_POSITION = 0;

    private ActivityRecipeBinding binding;
    private RecipeViewModel recipeViewModel;
    private RecipePagerAdapter recipePagerAdapter;
    private StepListViewModel stepListViewModel;
    private StepFragmentAdapter stepFragmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        if (!getIntent().hasExtra(EXTRA_RECIPE)) {
            finish();
            return;
        }

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
        final boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.setRecipe(retrieveRecipe());

        recipeViewModel.getRecipe().observe(this, (recipe -> {
            if (recipe == null) {
                return;
            }

            setTitle(recipe.getName());
            if (isTablet && stepListViewModel != null) {
                setupTabletView(recipe);
            }
        }));

        if (isTablet) {
            setupStepViewModel();
        }
    }

    private void setupStepViewModel() {
        stepListViewModel = ViewModelProviders.of(this).get(StepListViewModel.class);
        stepListViewModel.getCurrentStepPosition().observe(this, step -> {
            if (stepFragmentAdapter == null)
                return;

            binding.stepFragmentContainer.setCurrentStepPosition(step);
        });
    }

    private void setupTabletView(@NonNull Recipe recipe) {
        stepListViewModel.setStepsValue(recipe.getSteps());

        stepFragmentAdapter = new StepFragmentAdapter(getSupportFragmentManager(), this);
        stepFragmentAdapter.setSize(stepListViewModel.getStepsValue().size());
        binding.stepFragmentContainer.setAdapter(stepFragmentAdapter);

        stepListViewModel.setCurrentStepPosition(DEFAULT_STEP_POSITION);
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
