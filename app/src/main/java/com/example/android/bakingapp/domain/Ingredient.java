package com.example.android.bakingapp.domain;

import com.example.android.bakingapp.constants.JsonConstants;
import com.google.gson.annotations.SerializedName;

/**
 * Created by angelov on 9/17/2018.
 */

public class Ingredient {

    private float quantity;
    private String measure;
    @SerializedName(JsonConstants.JSON_INGREDIENT_NAME_FIELD)
    private String ingredientName;
}
