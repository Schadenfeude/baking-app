package com.itrided.android.bakerstreet.recipe.ingredient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itrided.android.bakerstreet.R;
import com.itrided.android.bakerstreet.data.model.Ingredient;
import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.library.RecipeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final ArrayList<Ingredient> ingredients = new ArrayList<>();

    public IngredientAdapter(List<Ingredient> ingredientsValue) {

    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setItems(List<Ingredient> ingredientList) {
        ingredients.addAll(ingredientList);
        notifyDataSetChanged();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbnail_iv)
        ImageView thumbnalIv;
        @BindView(R.id.name_tv)
        TextView nameTv;

        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Recipe recipe, @NonNull RecipeListener clickListener) {
            nameTv.setText(recipe.getName());
//            loadPoster(recipe.getImage());
            itemView.setOnClickListener(v -> clickListener.openRecipe(recipe));
        }
    }
}
