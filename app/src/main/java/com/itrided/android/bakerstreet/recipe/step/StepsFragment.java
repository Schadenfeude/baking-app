package com.itrided.android.bakerstreet.recipe.step;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itrided.android.bakerstreet.data.model.Step;
import com.itrided.android.bakerstreet.databinding.FragmentStepsBinding;
import com.itrided.android.bakerstreet.recipe.RecipeViewModel;

public class StepsFragment extends Fragment {

    private FragmentStepsBinding binding;
    private RecipeViewModel recipeViewModel;
    //    private T stepsListViewModel;
    private StepAdapter adapter;

    public StepsFragment() {
    }

    private static StepsFragment INSTANCE = null;

    public static StepsFragment getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StepsFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        recipeViewModel = ViewModelProviders.of(getActivity()).get(RecipeViewModel.class);
//        stepsListViewModel = ViewModelProviders.of(this).get(StepsListViewModel.class);
//        if (getActivity().getResources().getBoolean(R.bool.is_tablet)) {
//            stepsListViewModel.getObservableCurrentStep().observe(this, step -> {
//                if (recipeViewModel.getSteps() == null)
//                    return;
//
//                resetSelectedStep(step);
//            });
//        }

//        recipeViewModel.getSteps().observe(this, (steps) -> {
//            if (adapter == null)
//                return;
//
//            adapter.setSteps(steps);
//        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStepsBinding.inflate(inflater, container, false);

        binding.stepsRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));

        adapter = new StepAdapter(recipeViewModel.getStepsValue());
        binding.stepsRv.setAdapter(adapter);
        binding.stepsRv.addItemDecoration(new DividerItemDecoration(binding.stepsRv.getContext(),
                LinearLayoutManager.VERTICAL));

        return binding.getRoot();
    }

    //    @Override
    public void openStep(Step step) {
//        int position = 0;
//        if (step != null) {
//            position = recipeViewModel.getStepsValue().indexOf(step);
//        }
//
//        if (getActivity().getResources().getBoolean(R.bool.is_tablet)) {
//            resetSelectedStep(position);
//
//            stepsListViewModel.setCurrentStep(position);
//            return;
//        }
//
//        Intent intent = new Intent(getContext(), StepActivity.class);
//        intent.putParcelableArrayListExtra(EXTRA_STEPS, (ArrayList<Step>) recipeViewModel.getSteps());
//        intent.putExtra(EXTRA_STEP_POSITION, position);
//        intent.putExtra(EXTRA_RECIPE_NAME, recipeViewModel.getRecipe().getName());
//        startActivity(intent);
    }

    private void resetSelectedStep(int position) {
//        for (Step s : recipeViewModel.getSteps()) {
//            s.setSelected(false);
//        }
//        recipeViewModel.getSteps().get(position).setSelected(true);
//        adapter.notifyDataSetChanged();
    }
}
