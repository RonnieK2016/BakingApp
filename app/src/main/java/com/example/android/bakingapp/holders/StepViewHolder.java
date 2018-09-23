package com.example.android.bakingapp.holders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by angelov on 9/22/2018.
 */

public class StepViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.step_card_view)
    public CardView stepCardView;
    @BindView(R.id.step_name)
    public TextView stepName;

    public StepViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
