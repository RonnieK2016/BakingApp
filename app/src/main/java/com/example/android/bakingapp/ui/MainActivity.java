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

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeParser;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.constants.JsonConstants.RECIPE_ACCESS_URL_STRING;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>>{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RECIPE_LOADER_ID = 0;

    // Variables to store the values for the list index of the selected images
    // The default value will be index = 0
    private FragmentManager fragmentManager;

    // COMPLETED Create a variable to track whether to display a two-pane or single-pane UI
        // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean sTwoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        // COMPLETED If you are making a two-pane display, add new BodyPartFragments to create an initial Android-Me image
        // Also, for the two-pane display, get rid of the "Next" button in the master list fragment
        if (findViewById(R.id.android_me_linear_layout) != null) {
            sTwoPane = true;
            findViewById(R.id.next_button).setVisibility(View.GONE);
            ((GridView) findViewById(R.id.images_grid_view)).setNumColumns(2);

        }
        getSupportLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);
    }

    // Define the behavior for onImageSelected
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        if (sTwoPane) {
        }
        else {
        }

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

    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }
}
