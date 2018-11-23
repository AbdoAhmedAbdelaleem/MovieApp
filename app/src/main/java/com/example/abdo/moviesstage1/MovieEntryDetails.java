package com.example.abdo.moviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdo on 9/26/2017.
 */

public class MovieEntryDetails implements Parcelable
{
    public long movieID;
    public ArrayList<Tuple> Reviews;
    public ArrayList<String>ImagesPaths;
    public ArrayList<String>Trailers;
    public String TagLine;
    public String Overview;
    public String Title;
    public ArrayList<MovieEntry>SimilarMovies;
    public MovieEntryDetails(long movieID,String title, ArrayList<Tuple> reviews, ArrayList<String> imagesPaths, ArrayList<String> trailers
    , String tagline, String overview, ArrayList<MovieEntry>similars) {
        this.movieID = movieID;
        Reviews = reviews;
        ImagesPaths = imagesPaths;
        Trailers = trailers;
        this.TagLine=tagline;
        this.Overview=overview;
        this.SimilarMovies=similars;
        this.Title=title;
    }

    protected MovieEntryDetails(Parcel in) {
        movieID = in.readLong();
        Reviews = in.createTypedArrayList(Tuple.CREATOR);
        ImagesPaths = in.createStringArrayList();
        Trailers = in.createStringArrayList();
        this.TagLine=in.readString();
        this.Overview=in.readString();
        this.SimilarMovies=in.createTypedArrayList(MovieEntry.CREATOR);
        this.Title=in.readString();
    }

    public static final Creator<MovieEntryDetails> CREATOR = new Creator<MovieEntryDetails>() {
        @Override
        public MovieEntryDetails createFromParcel(Parcel in) {
            return new MovieEntryDetails(in);
        }

        @Override
        public MovieEntryDetails[] newArray(int size) {
            return new MovieEntryDetails[size];
        }
    };

    public long getMovieID() {
        return movieID;
    }

    public void setMovieID(long movieID) {
        this.movieID = movieID;
    }

    public ArrayList<Tuple> getReviews() {
        return Reviews;
    }

    public void setReviews(ArrayList<Tuple> reviews) {
        Reviews = reviews;
    }

    public ArrayList<String> getImagesPaths() {
        return ImagesPaths;
    }

    public void setImagesPaths(ArrayList<String> imagesPaths) {
        ImagesPaths = imagesPaths;
    }

    public ArrayList<String> getTrailers() {
        return Trailers;
    }

    public void setTrailers(ArrayList<String> trailers) {
        Trailers = trailers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(movieID);
        parcel.writeTypedList(Reviews);
        parcel.writeStringList(ImagesPaths);
        parcel.writeStringList(Trailers);
        parcel.writeString(this.TagLine);
        parcel.writeString(this.Overview);
        parcel.writeTypedList(this.SimilarMovies);
        parcel.writeString(this.Title);
    }
}
