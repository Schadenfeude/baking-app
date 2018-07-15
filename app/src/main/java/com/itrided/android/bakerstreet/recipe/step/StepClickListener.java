package com.itrided.android.bakerstreet.recipe.step;

import android.support.annotation.NonNull;

import com.itrided.android.bakerstreet.data.model.Step;

public interface StepClickListener {
    void openStep(@NonNull Step step);
}
