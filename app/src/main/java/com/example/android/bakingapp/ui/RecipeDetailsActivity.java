package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.constants.Constants;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.domain.Step;
import com.example.android.bakingapp.fragments.FragmentsHelper;
import com.example.android.bakingapp.fragments.RecipeStepDetailsFragment;
import com.example.android.bakingapp.fragments.RecipeStepsListFragment;
import com.example.android.bakingapp.listeners.ParentActivityCallback;

import butterknife.BindBool;
import butterknife.ButterKnife;

/**
 * Created by angelov on 9/20/2018.
 */

public class RecipeDetailsActivity extends AppCompatActivity implements ParentActivityCallback {

    @BindBool(R.bool.is_tablet)
    public boolean sTwoPane;
    private RecipeStepDetailsFragment mRecipeStepDetailsFragment;
    private Recipe mRecipe;
    private Step mCurStep;
    private static final String RECIPE_SAVED_TAG = "RECIPE_SAVED_TAG";
    private static final String CURRENT_STEP_SAVED_TAG = "CURRENT_STEP_SAVED_TAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);
        ButterKnife.bind(this);

        ActionBar supportActionBar = getSupportActionBar();

        if (getIntent() != null) {
            mRecipe = getIntent().getExtras().getParcelable(Constants.RECIPE_INTENT_EXTRA_TAG);
        }

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(RECIPE_SAVED_TAG);
            mCurStep = savedInstanceState.getParcelable(CURRENT_STEP_SAVED_TAG);
        }
        else {
            mCurStep = mRecipe.getSteps().get(0);
        }


        if (supportActionBar != null) {
            supportActionBar.setTitle(mRecipe.getName());
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecipeStepsListFragment recipeStepsListFragment =
                (RecipeStepsListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.recipe_steps_fragment_container);

        if (recipeStepsListFragment == null) {
            recipeStepsListFragment = new RecipeStepsListFragment();
            recipeStepsListFragment.setMRecipe(mRecipe);
            FragmentsHelper.addFragmentToView(getSupportFragmentManager(), R.id.recipe_steps_fragment_container,
                    recipeStepsListFragment);
        }

        recipeStepsListFragment.setParentActivityCallback(this);

        if(sTwoPane) {
            createOrReplaceStepDetailsFragment(getSupportFragmentManager(), R.id.step_details_fragment_container,
                    mCurStep);
        }
    }

    private void createOrReplaceStepDetailsFragment(FragmentManager fragmentManager, int containerId, Step step) {
        RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
        recipeStepDetailsFragment.setStep(step);
        if(mRecipeStepDetailsFragment == null) {
            FragmentsHelper.addFragmentToView(fragmentManager,containerId,recipeStepDetailsFragment);
        }
        else {
            FragmentsHelper.replaceFragmentInView(fragmentManager,containerId,recipeStepDetailsFragment);
        }
        mRecipeStepDetailsFragment = recipeStepDetailsFragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_SAVED_TAG, mRecipe);
        outState.putParcelable(CURRENT_STEP_SAVED_TAG, mCurStep);
    }

    @Override
    public void itemClicked(Step step) {
        createOrReplaceStepDetailsFragment(getSupportFragmentManager(), R.id.step_details_fragment_container,
                step);
        mCurStep = step;
    }
}
