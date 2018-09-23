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
 * Created by angelov on 9/18/2018.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_card_view)
    public CardView recipeCardView;
    @BindView(R.id.recipe_image)
    public ImageView recipeImage;
    @BindView(R.id.recipe_name)
    public TextView recipeName;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
