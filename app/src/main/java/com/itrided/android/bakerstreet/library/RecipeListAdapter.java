package com.itrided.android.bakerstreet.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.itrided.android.bakerstreet.R;
import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.databinding.ItemRecipeListBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {
    private List<Recipe> recipes;
    private RecipeListener listener;

    public RecipeListAdapter(RecipeListener listener) {
        this(null, listener);
    }

    public RecipeListAdapter(@Nullable List<Recipe> recipes, @NonNull RecipeListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ItemRecipeListBinding binding = ItemRecipeListBinding.inflate(layoutInflater, viewGroup, false);

        return new RecipeListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder recipeListViewHolder, int position) {
        final Recipe recipe = recipes.get(position);
        recipeListViewHolder.bind(recipe, listener);
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    static class RecipeListViewHolder extends RecyclerView.ViewHolder {
        private ItemRecipeListBinding binding;

        RecipeListViewHolder(ItemRecipeListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull Recipe recipe, @NonNull RecipeListener clickListener) {
            binding.nameTv.setText(recipe.getName());
            loadImageIntoView(recipe.getImage());
            itemView.setOnClickListener(v -> clickListener.openRecipe(recipe));
        }

        private void loadImageIntoView(@Nullable String imageUrl) {
            if (imageUrl == null || imageUrl.isEmpty()) {
                return;
            }
            final Context context = itemView.getContext();

            Picasso.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_cake)
                    .error(R.drawable.ic_cake)
                    .into(binding.thumbnailIv);
        }
    }
}
