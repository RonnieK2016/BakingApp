package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.constants.Constants;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.fragments.FragmentsHelper;
import com.example.android.bakingapp.fragments.RecipeStepsListFragment;

/**
 * Created by angelov on 9/20/2018.
 */

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipe_details);

        ActionBar supportActionBar = getSupportActionBar();

        Recipe recipe = getIntent().getExtras().getParcelable(Constants.RECIPE_INTENT_EXTRA_TAG);

        if (supportActionBar != null) {
            supportActionBar.setTitle(recipe.getName());
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecipeStepsListFragment recipeStepsListFragment =
                (RecipeStepsListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.recipe_steps_fragment_container);

        if (recipeStepsListFragment == null) {
            recipeStepsListFragment = new RecipeStepsListFragment();
            recipeStepsListFragment.setRecipe(recipe);
            FragmentsHelper.addFragmentToView(getSupportFragmentManager(), R.id.recipe_steps_fragment_container,
                    recipeStepsListFragment);
        }
    }

}
