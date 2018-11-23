package com.example.abdo.moviesstage1;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Abdo on 9/25/2017.
 */

public class MoviesDataFetcher extends AsyncTask<String, Void, List<MovieEntry>> {

    private MoviesAsyncTaskOnFinish Listener;
    public interface MoviesAsyncTaskOnFinish
    {
        public void OnFinishAsyncTask(List<MovieEntry>Entries);
        public void OnErrorOnLoadingData(Exception ex);
    }
    Context Context;
    DataFetchercritiria Tag;
    public MoviesDataFetcher(Context context,MoviesAsyncTaskOnFinish movieAsyncInterface,DataFetchercritiria tag)
    {
        this.Context=context;
        this.Listener=movieAsyncInterface;
        this.Tag=tag;
    }
    @Override
    protected List<MovieEntry> doInBackground(String... strings) {
        List<MovieEntry> moviesEntries = new ArrayList<>();
        if (strings.length <= 0 || !NetworkUtils.IsOnline(this.Context)) {
         this.Listener.OnErrorOnLoadingData(new Exception("No Internet Connection"));
            return moviesEntries;
        }
        try {
            String urlImage = "http://image.tmdb.org/t/p/w500/";
            String urlBackground = "http://image.tmdb.org/t/p/w500/";
            for(int pathCounter=0;pathCounter<strings.length;pathCounter++) {
                URL moviesURL = NetworkUtils.getURL(strings[pathCounter]);
                String jsonFileDataString = NetworkUtils.ReadDataFromURL(moviesURL);
                JSONObject moviesJson = new JSONObject(jsonFileDataString);
                JSONArray moviesArray = moviesJson.getJSONArray("results");
                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject currentArrayObject = moviesArray.getJSONObject(i);
                    long movieID = currentArrayObject.getLong("id");
                    String title = currentArrayObject.getString("title");
                    String movieOverview = currentArrayObject.getString("overview");
                    String releaseDate = currentArrayObject.getString("release_date");
                    String moviePosterPath = currentArrayObject.getString("poster_path");
                    double voteAverage = currentArrayObject.getDouble("vote_average");
                    String backdrop_path = currentArrayObject.getString("backdrop_path");
                    backdrop_path = urlBackground + backdrop_path;
                    if (moviePosterPath == null || moviePosterPath == "" || moviePosterPath.equalsIgnoreCase("null"))
                        continue;
                    moviePosterPath = urlImage + moviePosterPath;
                    if (title == null || title == "")
                        title = "";
                    if (releaseDate == null || releaseDate == "")
                        releaseDate = "";
                    if (movieOverview == null || movieOverview == "")
                        movieOverview = "";
                    MovieEntry movieEntry = new MovieEntry(movieID, title, moviePosterPath, movieOverview, releaseDate, voteAverage, backdrop_path);
                    if(Tag!= DataFetchercritiria.Favourite || (Tag== DataFetchercritiria.Favourite
                    && MainActivity.FavouriteMovies.contains(movieID)))
                    moviesEntries.add(movieEntry);
                }
            }
        } catch (JSONException e) {
            this.Listener.OnErrorOnLoadingData(e);
        } catch (MalformedURLException e) {
            this.Listener.OnErrorOnLoadingData(e);
        }
        return moviesEntries;
    }
   ArrayList<MovieEntry>RemoveDuplicate(List<MovieEntry>entries)
   {
       Map<Long, MovieEntry> map = new HashMap<>();
       for (MovieEntry movie : entries) {
           Long key = movie.getMovieID();
           if (!map.containsKey(key)) {
               map.put(key, movie);
           }
       }
       return new ArrayList<>(map.values());
   }
    @Override
    protected void onPostExecute(List<MovieEntry> movieEntries) {
        if(this.Tag== DataFetchercritiria.Favourite)
        {
            movieEntries=RemoveDuplicate(movieEntries);
        }
        super.onPostExecute(movieEntries);
        if(movieEntries.size()>0)
        this.Listener.OnFinishAsyncTask(movieEntries);
    }
}
