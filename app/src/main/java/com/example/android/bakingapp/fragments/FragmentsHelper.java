package com.example.android.bakingapp.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by angelov on 9/20/2018.
 */

public final class FragmentsHelper {

    public static void addFragmentToView(FragmentManager fragmentManager, int resId, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(resId, fragment);
        transaction.commit();
    }

    public static void replaceFragmentInView(FragmentManager fragmentManager, int resId, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(resId, fragment);
        transaction.commit();
    }
}
