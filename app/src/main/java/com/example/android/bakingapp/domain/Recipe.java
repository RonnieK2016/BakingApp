package com.example.android.bakingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by angelov on 9/17/2018.
 */

public class Recipe implements Parcelable {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private List<Ingredient> ingredients;
    @Getter
    @Setter
    List<Step> steps;
    @Getter
    @Setter
    private int servings;
    @Getter
    @Setter
    private String image;

    public static final Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };


    private Recipe(Parcel source) {
        id = source.readInt();
        name = source.readString();
        ingredients = new ArrayList<>();
        source.readTypedList(ingredients,Ingredient.CREATOR);
        steps = new ArrayList<>();
        source.readTypedList(steps, Step.CREATOR);
        servings = source.readInt();
        image = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

}
