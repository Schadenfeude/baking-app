package com.itrided.android.bakerstreet.library;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.itrided.android.bakerstreet.BakerStreetApp;
import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.data.service.RecipeService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class RecipeListViewModel extends ViewModel {

    private final RecipeService recipeService = BakerStreetApp.getRecipeService();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    private String requestedRecipeFromWidget;

    public RecipeListViewModel() {
        loadRecipes();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();

    }

    private void loadRecipes() {
        compositeDisposable.add(
                recipeService.getRecipes()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<Recipe>>() {
                            @Override
                            public void onSuccess(List<Recipe> results) {
                                if (results.size() == 0) {
                                    return;
                                }

                                recipes.setValue(results);
                            }

                            @Override
                            public void onError(Throwable e) {
                                //todo handle error
                                e.printStackTrace();
                            }
                        }));
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public void setRequestedRecipeFromWidget(String requestedRecipeFromWidget) {
        this.requestedRecipeFromWidget = requestedRecipeFromWidget;
    }

    public String getRequestedRecipeFromWidget() {
        return requestedRecipeFromWidget;
    }
}
