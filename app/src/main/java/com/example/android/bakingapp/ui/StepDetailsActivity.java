package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeStepsFragmentAdapter;
import com.example.android.bakingapp.constants.Constants;
import com.example.android.bakingapp.domain.Step;
import java.util.List;

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
        mViewPager.setAdapter(recipeStepsFragmentAdapter);
        if (currentStep != null){
            mViewPager.setCurrentItem(steps.indexOf(currentStep));
        }
        mTabLayout.setupWithViewPager(mViewPager);

        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.baking_steps);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
