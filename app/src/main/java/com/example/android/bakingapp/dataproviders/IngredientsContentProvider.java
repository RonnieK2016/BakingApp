package com.example.android.bakingapp.dataproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.dataproviders.IngredientsDbContract.RecipeRecord;

/**
 * Created by angelov on 9/23/2018.
 */

public class IngredientsContentProvider extends ContentProvider {

    private static final int RECIPE = 100;
    private static final int RECIPE_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private IngredientsDbHelper ingredientsDbHelper;


    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(IngredientsDbContract.CONTENT_AUTHORITY, RecipeRecord.INGREDIENTS_TABLE_NAME, RECIPE);
        uriMatcher.addURI(IngredientsDbContract.CONTENT_AUTHORITY, RecipeRecord.INGREDIENTS_TABLE_NAME
                + "/#", RECIPE_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        ingredientsDbHelper = new IngredientsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase ingredientsDb = ingredientsDbHelper.getReadableDatabase();

        Cursor resultCursor;
        switch(sUriMatcher.match(uri)){
            case RECIPE:{
                resultCursor = ingredientsDb.query(
                        RecipeRecord.INGREDIENTS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case RECIPE_WITH_ID:{
                resultCursor = ingredientsDb.query(
                        RecipeRecord.INGREDIENTS_TABLE_NAME,
                        projection,
                        RecipeRecord.ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:{
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }


        return resultCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)){
            case RECIPE:{
                return RecipeRecord.CONTENT_DIR_TYPE;
            }
            case RECIPE_WITH_ID:{
                return RecipeRecord.CONTENT_ITEM_TYPE;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase ingredientsDb = ingredientsDbHelper.getWritableDatabase();

        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case RECIPE: {
                long id = ingredientsDb.insert(RecipeRecord.INGREDIENTS_TABLE_NAME, null, values);
                // insert unless it is already contained in the database
                if (id > 0) {
                    returnUri = RecipeRecord.buildUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert record: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase ingredientsDb = ingredientsDbHelper.getWritableDatabase();
        int deletedRecords;
        switch(sUriMatcher.match(uri)){
            case RECIPE:
                deletedRecords = ingredientsDb.delete(
                        RecipeRecord.INGREDIENTS_TABLE_NAME, selection, selectionArgs);
                break;
            case RECIPE_WITH_ID:
                deletedRecords = ingredientsDb.delete(RecipeRecord.INGREDIENTS_TABLE_NAME,
                        RecipeRecord.ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return deletedRecords;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase ingredientsDb = ingredientsDbHelper.getWritableDatabase();

        return 0;
    }
}
