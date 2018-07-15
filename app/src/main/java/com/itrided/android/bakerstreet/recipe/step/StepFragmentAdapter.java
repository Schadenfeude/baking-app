package com.itrided.android.bakerstreet.recipe.step;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;

import static com.itrided.android.bakerstreet.StepActivity.EXTRA_STEP_POSITION;

public class StepFragmentAdapter extends AbstractFragmentStepAdapter {

    private int size;

    public StepFragmentAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        final StepFragment stepFragment = new StepFragment();
        final Bundle bundle = new Bundle();

        bundle.putInt(EXTRA_STEP_POSITION, position);
        stepFragment.setArguments(bundle);

        return stepFragment;
    }

    @Override
    public int getCount() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
