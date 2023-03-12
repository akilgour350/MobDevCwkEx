package com.adk.kilgour_alastair_s2221119;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ResultQuake implements Parcelable {
    private Earthquake quake;
    private Resemblance resemblance;

    public ResultQuake(Earthquake eq, Resemblance r) {
        quake = eq;
        resemblance = r;
    }

    public void setQuake(Earthquake eq) { quake = eq; }
    public void setResemblance(Resemblance r) { resemblance = r; }
    public Earthquake getQuake() { return quake; }
    public Resemblance getResemblance() { return resemblance; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        System.out.println("writeToParcel called");
    }
}

enum Resemblance {
    Exact, Near
}
