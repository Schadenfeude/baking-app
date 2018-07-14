package com.itrided.android.bakerstreet.recipe.ingredient;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itrided.android.bakerstreet.databinding.FragmentIngredientsBinding;
import com.itrided.android.bakerstreet.recipe.RecipeViewModel;

public class IngredientsFragment extends Fragment {

    private FragmentIngredientsBinding binding;
    private RecipeViewModel recipeViewModel;
    private IngredientAdapter ingredientAdapter;

    public IngredientsFragment() {
    }

    private static IngredientsFragment INSTANCE = null;

    public static IngredientsFragment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IngredientsFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getIngredients().observe(this, ingredients -> {
            if (ingredientAdapter == null)
                return;

            ingredientAdapter.setItems(ingredients);
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIngredientsBinding.inflate(inflater, container, false);

        ingredientAdapter = new IngredientAdapter(recipeViewModel.getIngredientsValue());
        binding.ingredientsRv.setAdapter(ingredientAdapter);
        binding.ingredientsRv.addItemDecoration(new DividerItemDecoration(binding.ingredientsRv.getContext(),
                LinearLayoutManager.VERTICAL));

        return binding.getRoot();
    }
}

