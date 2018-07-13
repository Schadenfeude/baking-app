package com.itrided.android.bakerstreet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.data.service.RecipeService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class RecipeListActivity extends AppCompatActivity {

    @BindView(R.id.recipes_rv)
    RecyclerView recipiesRv;

    private final RecipeService recipeService = BakerStreetApp.getRecipeService();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    //region API Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Recipe> recipes = new ArrayList<>();

        compositeDisposable.add(
                recipeService.getRecipes()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<Recipe>>() {
                            @Override
                            public void onNext(List<Recipe> results) {
                                if (results.size() == 0)
                                    return;

                                recipes.addAll(results);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    //endregion API Methods
}
