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

package com.example.android.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.constants.Constants;
import com.example.android.bakingapp.data.RecipeParser;
import com.example.android.bakingapp.dataproviders.IngredientsDbContract;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.listeners.AdapterCallbacks;
import com.example.android.bakingapp.utils.ConverterUtils;
import com.example.android.bakingapp.utils.NetworkUtils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>>, AdapterCallbacks<Recipe> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RECIPE_LOADER_ID = 0;
    @BindView(R.id.recipe_items_rv)
    public RecyclerView mRecipeItemsRv;
    private RecipeAdapter mRecipeAdapter;
    @BindBool(R.bool.is_tablet)
    public boolean sTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, sTwoPane ? 3 : 1);
        mRecipeItemsRv.setLayoutManager(gridLayoutManager);
        mRecipeItemsRv.setHasFixedSize(true);

        initAdapter();

        getSupportLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);
    }

    private void initAdapter() {
        mRecipeAdapter = new RecipeAdapter(this, new ArrayList<Recipe>());
        mRecipeAdapter.setMCallbacks(this);
        mRecipeItemsRv.setAdapter(mRecipeAdapter);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(this) {

            private List<Recipe> weatherDataCached;

            @Override
            protected void onStartLoading() {
                //mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public List<Recipe> loadInBackground() {
                try {
                    return RecipeParser.parseStringJsonToRecipes(
                            NetworkUtils.loadRecipesFromHttpUrl(NetworkUtils.getRecipesURL()));
                }
                catch (Exception e) {
                    Log.e(TAG, "Error when loading data from recipes endpoint ", e);
                    return null;
                }
            }

            @Override
            public void deliverResult(List<Recipe> data) {
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        if(CollectionUtils.isEmpty(data)) {
            return;
        }

        getContentResolver().delete(IngredientsDbContract.RecipeRecord.CONTENT_URI, null, null);

        for(Recipe recipe : data) {
            getContentResolver().insert(IngredientsDbContract.RecipeRecord.CONTENT_URI,
                    ConverterUtils.recipeToContentValues(recipe));

        }

        mRecipeAdapter.updateRecipes(data);
        mRecipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }

    @Override
    public void onClick(Recipe item) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Constants.RECIPE_INTENT_EXTRA_TAG, item);
        startActivity(intent);
    }
}
