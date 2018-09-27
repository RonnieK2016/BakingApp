package com.example.android.bakingapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeStepsFragmentAdapter;
import com.example.android.bakingapp.constants.Constants;
import com.example.android.bakingapp.domain.Step;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by angelov on 9/23/2018.
 */

public class StepDetailsActivity extends AppCompatActivity {

    @BindView(R.id.steps_list_pager)
    public ViewPager mViewPager;
    @BindView(R.id.steps_list_tab)
    public TabLayout mTabLayout;
    private int currentStepId;
    @BindBool(R.bool.is_tablet)
    public boolean isTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_details);

        ButterKnife.bind(this);

        ActionBar supportActionBar = getSupportActionBar();

        List<Step> steps = getIntent().getExtras().getParcelableArrayList(Constants.STEPS_INTENT_EXTRA_TAG);
        Step currentStep = getIntent().getExtras().getParcelable(Constants.CURRENT_STEP_INTENT_EXTRA_TAG);

        RecipeStepsFragmentAdapter recipeStepsFragmentAdapter  = new RecipeStepsFragmentAdapter(getSupportFragmentManager());
        recipeStepsFragmentAdapter.setMSteps(steps);
        recipeStepsFragmentAdapter.notifyDataSetChanged();
        if (currentStep != null){
            for(int i = 0; i < steps.size(); i++) {
                Step step = steps.get(i);
                if (step.getId() == currentStep.getId()) {
                    currentStepId = i;
                    break;
                }
            }
        }

        mViewPager.setAdapter(recipeStepsFragmentAdapter);
        mViewPager.setCurrentItem(currentStepId,true);
        mTabLayout.setupWithViewPager(mViewPager);

        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.baking_steps);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            mTabLayout.setVisibility(View.GONE);
        }
    }
}
