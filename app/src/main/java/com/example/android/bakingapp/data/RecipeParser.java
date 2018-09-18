package com.example.android.bakingapp.data;

import com.example.android.bakingapp.domain.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by angelov on 9/17/2018.
 */

public final class RecipeParser {

    private static final Type RECIPE_TYPE = new TypeToken<List<Recipe>>(){}.getType();
    private static final GsonBuilder GSON_BUILDER = new GsonBuilder();

    private RecipeParser(){}

    public static List<Recipe> parseStringJsonToRecipes(String stringData) {

        if(StringUtils.isEmpty(stringData)) {
            return null;
        }
        Gson gSonParser = GSON_BUILDER.create();
        return gSonParser.fromJson(stringData, RECIPE_TYPE);
    }

}
