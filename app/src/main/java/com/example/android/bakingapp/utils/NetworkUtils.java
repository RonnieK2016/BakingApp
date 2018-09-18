package com.example.android.bakingapp.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static com.example.android.bakingapp.constants.JsonConstants.RECIPE_ACCESS_URL_STRING;

/**
 * Created by angelov on 9/17/2018.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private NetworkUtils() { }

    public static String loadRecipesFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : null;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL getRecipesURL() {
        URL recipeAccessUrl = null;
        try {
            recipeAccessUrl = new URL(Uri.parse(RECIPE_ACCESS_URL_STRING).toString());
        }
        catch (Exception exc) {
            Log.e(TAG, "Error when building recipes URL ", exc);
        }
        return recipeAccessUrl;
    }

}
