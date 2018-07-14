package com.itrided.android.bakerstreet.recipe.step;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.itrided.android.bakerstreet.data.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter {
    private List<Step> items;

    public StepAdapter(List<Step> stepsValue, StepsFragment stepsFragment) {

    }

    public void setItems(List<Step> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
