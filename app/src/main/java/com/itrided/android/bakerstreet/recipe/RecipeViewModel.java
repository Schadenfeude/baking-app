package com.itrided.android.bakerstreet.recipe;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.itrided.android.bakerstreet.data.model.Ingredient;
import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.data.model.Step;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<Recipe> recipe = new MutableLiveData<>();
    private MutableLiveData<List<Step>> steps = new MutableLiveData<>();
    private MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();

    public void setRecipe(Recipe recipe) {
        this.recipe.setValue(recipe);
        this.steps.setValue(recipe.getSteps());
        this.ingredients.setValue(recipe.getIngredients());
    }

    public MutableLiveData<Recipe> getRecipe() {
        return recipe;
    }

    public MutableLiveData<List<Step>> getSteps() {
        return steps;
    }

    public MutableLiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

    public Recipe getRecipeValue() {
        return recipe.getValue();
    }

    public List<Step> getStepsValue() {
        return steps.getValue();
    }

    public List<Ingredient> getIngredientsValue() {
        return ingredients.getValue();
    }
}
