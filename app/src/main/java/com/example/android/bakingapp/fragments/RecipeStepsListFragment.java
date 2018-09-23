package com.example.android.bakingapp.fragments;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeStepsAdapter;
import com.example.android.bakingapp.domain.Ingredient;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.domain.Step;
import com.example.android.bakingapp.listeners.AdapterCallbacks;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by angelov on 9/20/2018.
 */

public class RecipeStepsListFragment extends Fragment implements AdapterCallbacks<Step> {

    @Getter
    @Setter
    private Recipe recipe;
    private Unbinder unbinder;
    @BindView(R.id.recipe_details_ingredients)
    public TextView mRecipeIngredientsTextView;
    @BindView(R.id.recipe_details_steps)
    public RecyclerView mRecipeStepsRv;
    private RecipeStepsAdapter mRecipeStepsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_steps_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecipeIngredientsTextView.setText(ingredientsListToString(recipe.getIngredients()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecipeStepsRv.setLayoutManager(linearLayoutManager);
        mRecipeStepsRv.setHasFixedSize(true);

        initAdapter();

        return view;
    }

    private void initAdapter() {
        mRecipeStepsAdapter = new RecipeStepsAdapter(getContext(), recipe.getSteps());
        mRecipeStepsAdapter.setMCallbacks(this);
        mRecipeStepsRv.setAdapter(mRecipeStepsAdapter);
    }

    private String ingredientsListToString(List<Ingredient> ingredients) {
        StringBuilder stringBuilder = new StringBuilder();

        if (!CollectionUtils.isEmpty(ingredients)) {
            for (Ingredient ingredient : ingredients) {
                stringBuilder.append(ingredient.getIngredientName()).append(" - ")
                        .append(ingredient.getQuantity())
                        .append(ingredient.getMeasure())
                        .append("\n");
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(Step item) {

    }
}
