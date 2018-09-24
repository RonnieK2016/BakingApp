package com.example.android.bakingapp.dataproviders;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by angelov on 9/23/2018.
 */

public class IngredientsDbContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.bakingapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String[] INGREDIENTS_COLUMNS = new String[] {
            RecipeRecord.ID,
            RecipeRecord.RECIPE_NAME,
            RecipeRecord.INGREDIENTS
    };

    public static final String[] GET_INGREDIENTS_BY_ID_COLUMNS = new String[] {
            RecipeRecord.ID
    };

    public static final class RecipeRecord implements BaseColumns {

        // table name
        public static final String INGREDIENTS_TABLE_NAME = "INGREDIENTS";

        // columns
        public static final String ID="ID";
        public static final String RECIPE_NAME = "RECIPE_NAME";
        public static final String INGREDIENTS = "INGREDIENTS";

        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(INGREDIENTS_TABLE_NAME).build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + INGREDIENTS_TABLE_NAME;

        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + INGREDIENTS_TABLE_NAME;

        public static Uri buildUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
