package com.itrided.android.bakerstreet.library;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itrided.android.bakerstreet.R;
import com.itrided.android.bakerstreet.data.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        final View view = layoutInflater.inflate(R.layout.item_recipe_list, null);

        return new RecipeListViewHolder(view);
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
        @BindView(R.id.thumbnail_iv)
        ImageView thumbnailIv;
        @BindView(R.id.name_tv)
        TextView nameTv;

        RecipeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Recipe recipe, @NonNull RecipeListener clickListener) {
            nameTv.setText(recipe.getName());
//            loadPoster(recipe.getImage());
            itemView.setOnClickListener(v -> clickListener.openRecipe(recipe));
        }

//        private void loadPoster(@Nullable String imageUrl) {
//            if (imageUrl == null || imageUrl.isEmpty()) {
//                return;
//            }
//            final Context context = itemView.getContext();
//
//            ImageLoader.getInstance(context)
//                    .loadImageIntoTarget(imageUrl, posterIv);
//        }
    }
}
