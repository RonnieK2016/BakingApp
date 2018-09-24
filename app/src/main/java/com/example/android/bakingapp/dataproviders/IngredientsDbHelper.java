package com.example.android.bakingapp.dataproviders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.bakingapp.dataproviders.IngredientsDbContract.RecipeRecord;

/**
 * Created by angelov on 9/23/2018.
 */

public class IngredientsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ingredients.db";

    private static final int VERSION = 1;

    IngredientsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "  + RecipeRecord.INGREDIENTS + " (" +
                RecipeRecord.ID                  + " INTEGER PRIMARY KEY, " +
                RecipeRecord.RECIPE_NAME               + " TEXT NOT NULL, " +
                RecipeRecord.INGREDIENTS         + " TEXT);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecipeRecord.INGREDIENTS_TABLE_NAME);
        onCreate(db);
    }
}
