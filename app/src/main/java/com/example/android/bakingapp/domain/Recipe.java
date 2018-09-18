package com.example.android.bakingapp.domain;

import java.util.List;

/**
 * Created by angelov on 9/17/2018.
 */

public class Recipe {
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    List<Step> steps;
    private int servings;
    private String image;
}
