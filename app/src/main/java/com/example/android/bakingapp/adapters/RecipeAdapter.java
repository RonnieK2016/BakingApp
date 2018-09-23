/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.holders.RecipeViewHolder;
import com.example.android.bakingapp.listeners.AdapterCallbacks;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by angelov on 9/17/2018.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Getter
    @Setter
    private AdapterCallbacks<Recipe> mCallbacks;
    private Context mContext;
    private List<Recipe> mRecipes;

    /**
     * Constructor method
     * @param recipes The list of recipes to display
     */
    public RecipeAdapter(Context context, List<Recipe> recipes) {
        mContext = context;
        mRecipes = recipes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = View.inflate(parent.getContext(), R.layout.recipe_item, null);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecipeViewHolder) {
            final Recipe selectedRecipe = mRecipes.get(position);

            final RecipeViewHolder recipeViewHolder = (RecipeViewHolder) holder;

            recipeViewHolder.recipeName.setText(selectedRecipe.getName());

            if (!StringUtils.isEmpty(selectedRecipe.getImage())) {
                Picasso.with(mContext)
                        .load(selectedRecipe.getImage())
                        .placeholder(R.drawable.ic_cupcake_bw)
                        .into(recipeViewHolder.recipeImage);
            }

            recipeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCallbacks!=null) {
                        mCallbacks.onClick(selectedRecipe);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mRecipes != null ? mRecipes.size() : 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void updateRecipes(List<Recipe> newList) {
        mRecipes = newList;
    }

}
