package com.example.android.bakingapp.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.dataproviders.IngredientsDbContract;
import com.example.android.bakingapp.dataproviders.IngredientsDbContract.RecipeRecord;
import com.example.android.bakingapp.utils.ConverterUtils;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The configuration screen for the {@link BakingAppWidget BakingAppWidget} AppWidget.
 */
public class BakingAppWidgetConfigureActivity extends Activity {

    private static final String PREFS_INGREDIENTS_NAME = "com.example.android.bakingapp.widget.BakingAppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    @BindView(R.id.recipe_selection_list)
    RadioGroup mRecipeSelection;
    String[] ingredientsArray;

    public BakingAppWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveIngredientPref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_INGREDIENTS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }


    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadIngredientPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_INGREDIENTS_NAME, 0);
        String ingredients = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (!StringUtils.isEmpty(ingredients)) {
            return ingredients;
        } else {
            return context.getString(R.string.no_recipes_found);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_INGREDIENTS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.baking_app_widget_configure);
        ButterKnife.bind(this);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        Cursor result = getContentResolver().query(RecipeRecord.CONTENT_URI,
                IngredientsDbContract.INGREDIENTS_COLUMNS,
                null,
                null, null);

        if (result.getCount() > 0 && result.moveToFirst()) {
            ingredientsArray = new String[result.getCount()];
            int recipeIdx = 0;
            do {
                AppCompatRadioButton recipeEntry = new AppCompatRadioButton(this);
                recipeEntry.setText(result.getString(result.getColumnIndex(RecipeRecord.RECIPE_NAME)));
                ingredientsArray[recipeIdx] = result.getString(result.getColumnIndex(RecipeRecord.INGREDIENTS));
                recipeEntry.setId(recipeIdx++);
                mRecipeSelection.addView(recipeEntry);
            } while(result.moveToNext());
        }
        else {
            Toast.makeText(this, R.string.no_recipes_found, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick(R.id.add_button)
    public void onClick(View v) {
        final Context context = BakingAppWidgetConfigureActivity.this;

        int selectedRecipeId = mRecipeSelection.getCheckedRadioButtonId();

        saveIngredientPref(context, mAppWidgetId, ingredientsArray[selectedRecipeId]);

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        BakingAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}

