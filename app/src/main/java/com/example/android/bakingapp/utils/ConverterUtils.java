package com.example.android.bakingapp.utils;

import android.content.ContentValues;

import com.example.android.bakingapp.dataproviders.IngredientsDbContract.RecipeRecord;
import com.example.android.bakingapp.domain.Ingredient;
import com.example.android.bakingapp.domain.Recipe;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * Created by angelov on 9/23/2018.
 */

public final class ConverterUtils {

    public static ContentValues recipeToContentValues(Recipe recipe) {
        ContentValues values = new ContentValues();

        values.put(RecipeRecord.ID, recipe.getId());
        values.put(RecipeRecord.RECIPE_NAME, recipe.getName());
        values.put(RecipeRecord.INGREDIENTS, ingredientsListToString(recipe.getIngredients()));
        return values;
    }

    public static String ingredientsListToString(List<Ingredient> ingredients) {
        StringBuilder stringBuilder = new StringBuilder();

        if (!CollectionUtils.isEmpty(ingredients)) {
            for (Ingredient ingredient : ingredients) {
                stringBuilder.append(ingredient.getIngredientName()).append(" - ")
                        .append(ingredient.getQuantity())
                        .append(" ")
                        .append(ingredient.getMeasure())
                        .append("\n");
            }
        }

        return stringBuilder.toString();
    }

}
