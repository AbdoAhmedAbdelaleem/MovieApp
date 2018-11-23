package com.example.abdo.moviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abdo on 9/25/2017.
 */

public class Tuple implements Parcelable
{
    public String Item1;
    public String Item2;
    public  Tuple(String item1,String item2)
    {
        this.Item1=item1;
        this.Item2=item2;
    }

    protected Tuple(Parcel in) {
        Item1 = in.readString();
        Item2 = in.readString();
    }


    public static final Creator<Tuple> CREATOR = new Creator<Tuple>() {
        @Override
        public Tuple createFromParcel(Parcel in) {
            return new Tuple(in);
        }

        @Override
        public Tuple[] newArray(int size) {
            return new Tuple[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Item1);
        parcel.writeString(Item2);
    }
}
