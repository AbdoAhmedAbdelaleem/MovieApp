package com.example.abdo.moviesstage1;

import android.app.backup.BackupDataInput;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.PluginStub;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdo on 9/19/2017.
 */

public class MovieEntry  implements Parcelable{
    public long movieID;
    public String movieTitle;
    public String moviePosterPath;
    public String movieOverview;
    public String movieRelease_date;
    public double MovieVoteAverage;
    public String MovieBackdropPath;
    public Bitmap MovieBackdropIMG;
    public Bitmap moviePosterIMG;

    public MovieEntry(long movieID, String movieTitle, String moviePosterPath, String movieOverview, String movieRelease_date
    , double voteAverage, String movieBackdropPath) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.moviePosterPath = moviePosterPath;
        this.movieOverview = movieOverview;
        this.movieRelease_date = movieRelease_date;
        this.MovieBackdropPath=movieBackdropPath;
        this.MovieVoteAverage=voteAverage;
    }

    protected MovieEntry(Parcel in) {
        movieID = in.readLong();
        movieTitle = in.readString();
        moviePosterPath = in.readString();
        movieOverview = in.readString();
        movieRelease_date = in.readString();
        MovieVoteAverage = in.readDouble();
        MovieBackdropPath = in.readString();
    }

    public static final Creator<MovieEntry> CREATOR = new Creator<MovieEntry>() {
        @Override
        public MovieEntry createFromParcel(Parcel in) {
            return new MovieEntry(in);
        }

        @Override
        public MovieEntry[] newArray(int size) {
            return new MovieEntry[size];
        }
    };

    public double getMovieVoteAverage() {
        return MovieVoteAverage;
    }

    public void setMovieVoteAverage(double movieVoteAverage) {
        MovieVoteAverage = movieVoteAverage;
    }

    public String getMovieBackdropPath() {
        return MovieBackdropPath;
    }

    public void setMovieBackdropPath(String movieBackdropPath) {
        MovieBackdropPath = movieBackdropPath;
    }

    public void setMovieID(long movieID) {
        this.movieID = movieID;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public void setMovieRelease_date(String movieRelease_date) {
        this.movieRelease_date = movieRelease_date;
    }

    public long getMovieID() {
        return movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieRelease_date() {
        return movieRelease_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(movieID);
        parcel.writeString(movieTitle);
        parcel.writeString(moviePosterPath);
        parcel.writeString(movieOverview);
        parcel.writeString(movieRelease_date);
        parcel.writeDouble(MovieVoteAverage);
        parcel.writeString(MovieBackdropPath);
    }
}
