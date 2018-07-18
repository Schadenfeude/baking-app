package com.itrided.android.bakerstreet.recipe.step;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.itrided.android.bakerstreet.data.model.Step;

import java.util.List;

public class StepListViewModel extends ViewModel {
    private MutableLiveData<List<Step>> steps = new MutableLiveData<>();
    private MutableLiveData<Integer> currentStepPosition = new MutableLiveData<>();

    public List<Step> getStepsValue() {
        return steps.getValue();
    }

    public void setStepsValue(List<Step> steps) {
        this.steps.setValue(steps);
    }

    public Integer getCurrentStepValue() {
        return currentStepPosition.getValue();
    }

    public void setCurrentStepPosition(Integer currentStepPosition) {
        this.currentStepPosition.setValue(currentStepPosition);
    }

    public MutableLiveData<Integer> getCurrentStepPosition() {
        return currentStepPosition;
    }
}
