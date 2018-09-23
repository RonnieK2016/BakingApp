package com.example.android.bakingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by angelov on 9/17/2018.
 */

public class Step implements Parcelable {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String shortDescription;
    @Getter
    @Setter
    private String videoURL;
    @Getter
    @Setter
    private String thumbnailURL;

    public static final Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };

    private Step(Parcel source) {
        id = source.readInt();
        description = source.readString();
        shortDescription = source.readString();
        videoURL = source.readString();
        thumbnailURL = source.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(shortDescription);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

}
