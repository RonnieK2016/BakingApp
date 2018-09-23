package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.domain.Step;
import com.example.android.bakingapp.holders.StepViewHolder;
import com.example.android.bakingapp.listeners.AdapterCallbacks;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by angelov on 9/20/2018.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Getter
    @Setter
    private AdapterCallbacks<Step> mCallbacks;
    private Context mContext;
    private List<Step> mSteps;

    /**
     * Constructor method
     * @param steps The list of recipes to display
     */
    public RecipeStepsAdapter(Context context, List<Step> steps) {
        mContext = context;
        mSteps = steps;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = View.inflate(parent.getContext(), R.layout.recipe_step_item, null);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof StepViewHolder) {
            final Step selectedStep = mSteps.get(position);

            final StepViewHolder stepViewHolder = (StepViewHolder) holder;

            stepViewHolder.stepName.setText(selectedStep.getShortDescription());

            stepViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCallbacks!=null) {
                        mCallbacks.onClick(selectedStep);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mSteps != null ? mSteps.size() : 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void updateSteps(List<Step> newList) {
        mSteps = newList;
    }

}
