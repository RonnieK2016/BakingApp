package com.example.android.bakingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.bakingapp.constants.JsonConstants;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by angelov on 9/17/2018.
 */

public class Ingredient implements Parcelable {

    @Getter
    @Setter
    private float quantity;
    @Getter
    @Setter
    private String measure;
    @SerializedName(JsonConstants.JSON_INGREDIENT_NAME_FIELD)
    @Getter
    @Setter
    private String ingredientName;

    public static final Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };

    private Ingredient(Parcel source) {
        quantity = source.readFloat();
        measure = source.readString();
        ingredientName = source.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(quantity);
        dest.writeString(measure);
        dest.writeString(ingredientName);
    }
}
