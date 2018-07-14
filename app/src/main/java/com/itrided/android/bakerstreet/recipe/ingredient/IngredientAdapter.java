package com.itrided.android.bakerstreet.recipe.ingredient;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.itrided.android.bakerstreet.data.model.Ingredient;
import com.itrided.android.bakerstreet.databinding.ItemIngredientBinding;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private ArrayList<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredientsValue) {
        ingredients = new ArrayList<>(ingredientsValue.size());
        ingredients.addAll(ingredientsValue);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ItemIngredientBinding binding = ItemIngredientBinding.inflate(layoutInflater, viewGroup, false);

        return new IngredientAdapter.IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int position) {
        ingredientViewHolder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setItems(List<Ingredient> ingredientList) {
        ingredients.addAll(ingredientList);
        notifyDataSetChanged();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private ItemIngredientBinding binding;

        IngredientViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull Ingredient ingredient) {
            final String amount = ingredient.getQuantity() + " " + ingredient.getMeasure();
            binding.ingredientNameTv.setText(ingredient.getName());
            binding.ingredientAmountTv.setText(amount);
        }
    }
}
