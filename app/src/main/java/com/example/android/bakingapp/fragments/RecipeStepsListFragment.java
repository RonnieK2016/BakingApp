package com.example.android.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeStepsAdapter;
import com.example.android.bakingapp.constants.Constants;
import com.example.android.bakingapp.domain.Ingredient;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.domain.Step;
import com.example.android.bakingapp.listeners.AdapterCallbacks;
import com.example.android.bakingapp.listeners.ParentActivityCallback;
import com.example.android.bakingapp.ui.StepDetailsActivity;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Getter;
import lombok.Setter;

import static com.example.android.bakingapp.utils.ConverterUtils.ingredientsListToString;

/**
 * Created by angelov on 9/20/2018.
 */

public class RecipeStepsListFragment extends Fragment implements AdapterCallbacks<Step> {

    @Getter
    @Setter
    private Recipe mRecipe;
    private Unbinder unbinder;
    @BindView(R.id.recipe_details_ingredients)
    public TextView mRecipeIngredientsTextView;
    @BindView(R.id.recipe_details_steps)
    public RecyclerView mRecipeStepsRv;
    private RecipeStepsAdapter mRecipeStepsAdapter;
    @BindBool(R.bool.is_tablet)
    public boolean sTwoPane;
    private int selectedStepId;
    @Getter
    @Setter
    private ParentActivityCallback parentActivityCallback;
    private static final String RECIPE_SAVED_TAG = "RECIPE_SAVED_TAG";
    private static final String SELECTED_STEP_ID = "SELECTED_STEP_ID";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        selectedStepId = 0;

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(RECIPE_SAVED_TAG);
            selectedStepId = savedInstanceState.getInt(SELECTED_STEP_ID);
        }

        View view = inflater.inflate(R.layout.recipe_steps_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecipeIngredientsTextView.setText(ingredientsListToString(mRecipe.getIngredients()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecipeStepsRv.setLayoutManager(linearLayoutManager);
        mRecipeStepsRv.setHasFixedSize(true);

        initAdapter();

        return view;
    }

    private void initAdapter() {
        mRecipeStepsAdapter = new RecipeStepsAdapter(getContext(), mRecipe.getSteps(), selectedStepId);
        mRecipeStepsAdapter.setMCallbacks(this);
        mRecipeStepsRv.setAdapter(mRecipeStepsAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_SAVED_TAG, mRecipe);
        outState.putInt(SELECTED_STEP_ID, selectedStepId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(Step item) {
        selectedStepId = item.getId();
        if(!sTwoPane) {
            Intent intent = new Intent(getContext(), StepDetailsActivity.class);
            intent.putParcelableArrayListExtra(Constants.STEPS_INTENT_EXTRA_TAG,
                    (ArrayList<? extends Parcelable>) mRecipe.getSteps());
            intent.putExtra(Constants.CURRENT_STEP_INTENT_EXTRA_TAG, item);
            startActivity(intent);
        }
        else {
            parentActivityCallback.itemClicked(item);
        }
    }
}
