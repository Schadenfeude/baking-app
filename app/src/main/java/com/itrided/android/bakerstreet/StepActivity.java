package com.itrided.android.bakerstreet;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.itrided.android.bakerstreet.data.model.Step;
import com.itrided.android.bakerstreet.databinding.ActivityStepBinding;
import com.itrided.android.bakerstreet.recipe.step.StepFragmentAdapter;
import com.itrided.android.bakerstreet.recipe.step.StepListViewModel;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {
    public static final String EXTRA_STEPS_LIST = "step";
    public static final String EXTRA_STEP_POSITION = "stepPosition";

    private ActivityStepBinding binding;
    private StepListViewModel stepListViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_step);

        final Intent intent = getIntent();
        if (!intent.hasExtra(EXTRA_STEPS_LIST)) {
            finish();
            return;
        }

        final int position = intent.getIntExtra(EXTRA_STEP_POSITION, 0);
        final ArrayList<Step> steps = intent.getParcelableArrayListExtra(EXTRA_STEPS_LIST);
        final StepFragmentAdapter adapter = new StepFragmentAdapter(getSupportFragmentManager(), this);
        stepListViewModel = ViewModelProviders.of(this).get(StepListViewModel.class);

        stepListViewModel.setStepsValue(steps);
        adapter.setSize(stepListViewModel.getStepsValue().size());
        binding.stepFragmentContainer.setAdapter(adapter);

        if (getIntent().hasExtra(EXTRA_STEP_POSITION)) {
            binding.stepFragmentContainer.setCurrentStepPosition(position);
        }

        final boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);
        if (!isLandscape) {
            setTitle(steps.get(position).getShortDescription());
        }
    }
}

