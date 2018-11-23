package com.example.abdo.moviesstage1;

import android.app.Application;

/**
 * Created by Abdo on 9/20/2017.
 */

public class SendDataHelper extends Application
{
    private MovieEntry movieEntry;

    public MovieEntry getMovieEntry() {
        return movieEntry;
    }

    public void setMovieEntry(MovieEntry movieEntry) {
        this.movieEntry = movieEntry;
    }
}
