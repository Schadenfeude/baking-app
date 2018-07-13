package com.itrided.android.bakerstreet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Application;

import com.itrided.android.bakerstreet.data.service.RecipeService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakerStreetApp extends Application {
    private static final String RECIPE_SERVICE_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static RecipeService recipeService;

    @Override
    public void onCreate() {
        super.onCreate();

        final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPE_SERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        recipeService = retrofit.create(RecipeService.class);
    }

    public static RecipeService getRecipeService() {
        return recipeService;
    }
}
