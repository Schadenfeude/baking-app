package com.itrided.android.bakerstreet.recipe.step;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itrided.android.bakerstreet.R;
import com.itrided.android.bakerstreet.StepActivity;
import com.itrided.android.bakerstreet.data.model.Step;
import com.itrided.android.bakerstreet.databinding.FragmentStepListBinding;
import com.itrided.android.bakerstreet.recipe.RecipeViewModel;

import java.util.ArrayList;

import static com.itrided.android.bakerstreet.StepActivity.EXTRA_STEPS_LIST;
import static com.itrided.android.bakerstreet.StepActivity.EXTRA_STEP_POSITION;

public class StepsListFragment extends Fragment {

    private FragmentStepListBinding binding;
    private RecipeViewModel recipeViewModel;
    private StepListViewModel stepListViewModel;
    private StepListAdapter adapter;
    private boolean isTablet;

    public StepsListFragment() {
    }

    private static StepsListFragment INSTANCE = null;

    public static StepsListFragment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StepsListFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        isTablet = getActivity().getResources().getBoolean(R.bool.is_tablet);
        recipeViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
        stepListViewModel = ViewModelProviders.of(getActivity()).get(StepListViewModel.class);

        if (isTablet) {
            stepListViewModel.getCurrentStepPosition().observe(this, step -> {
                if (recipeViewModel.getSteps() == null)
                    return;

                resetSelectedStep(step);
            });
        }

        recipeViewModel.getSteps().observe(this, (steps) -> {
            if (adapter == null)
                return;

            adapter.setSteps(steps);
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStepListBinding.inflate(inflater, container, false);

        setupAdapter();

        return binding.getRoot();
    }

    private void setupAdapter() {
        adapter = new StepListAdapter(recipeViewModel.getStepsValue(), getStepClickListener());
        binding.stepsRv.setAdapter(adapter);
        binding.stepsRv.addItemDecoration(new DividerItemDecoration(binding.stepsRv.getContext(),
                LinearLayoutManager.VERTICAL));
    }

    private StepClickListener getStepClickListener() {
        return step -> {
            int position = step.getId();

            if (isTablet) {
                resetSelectedStep(position);

                stepListViewModel.setCurrentStepPosition(position);
                return;
            }

            final Intent intent = new Intent(getContext(), StepActivity.class);
            intent.putParcelableArrayListExtra(EXTRA_STEPS_LIST, (ArrayList<Step>) recipeViewModel.getStepsValue());
            intent.putExtra(EXTRA_STEP_POSITION, position);
            startActivity(intent);
        };
    }

    private void resetSelectedStep(int position) {
        for (Step s : recipeViewModel.getStepsValue()) {
            s.setSelected(false);
        }
        recipeViewModel.getStepsValue().get(position).setSelected(true);
        adapter.notifyDataSetChanged();
    }
}
