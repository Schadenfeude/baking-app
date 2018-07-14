package com.itrided.android.bakerstreet.recipe.step;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.itrided.android.bakerstreet.data.model.Step;
import com.itrided.android.bakerstreet.databinding.ItemStepBinding;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<Step> steps;

    public StepAdapter(List<Step> stepList) {
        steps = new ArrayList<>(stepList.size());
        steps.addAll(stepList);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ItemStepBinding binding = ItemStepBinding.inflate(layoutInflater, viewGroup, false);

        return new StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder stepViewHolder, int position) {
        final String stepNumber = String.valueOf(position + 1);
        stepViewHolder.bind(steps.get(position), stepNumber);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setSteps(List<Step> steps) {
        this.steps.addAll(steps);
        notifyDataSetChanged();
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        private ItemStepBinding binding;

        StepViewHolder(ItemStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull Step step, String stepNumber) {
            binding.stepNumberTv.setText(stepNumber);
            binding.stepNameTv.setText(step.getShortDescription());
        }
    }
}
