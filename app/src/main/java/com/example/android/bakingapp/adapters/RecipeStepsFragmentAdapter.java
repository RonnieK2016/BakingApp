package com.example.android.bakingapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.bakingapp.domain.Step;
import com.example.android.bakingapp.fragments.FragmentsHelper;
import com.example.android.bakingapp.fragments.RecipeStepDetailsFragment;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by angelov on 9/20/2018.
 */

public class RecipeStepsFragmentAdapter extends FragmentStatePagerAdapter {

    @Setter
    @Getter
    private List<Step> mSteps;

    public RecipeStepsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
        recipeStepDetailsFragment.setStep(mSteps.get(position));
        return recipeStepDetailsFragment;
    }

    @Override
    public int getCount() {
        return !CollectionUtils.isEmpty(mSteps) ? mSteps.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Step " + position;
    }

}
