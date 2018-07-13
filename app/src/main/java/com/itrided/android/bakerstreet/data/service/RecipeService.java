package com.itrided.android.bakerstreet.data.service;

import com.itrided.android.bakerstreet.data.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("baking.json")
    Observable<List<Recipe>> getRecipes();
}
