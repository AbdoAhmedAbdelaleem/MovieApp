package com.example.abdo.moviesstage1;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Abdo on 9/26/2017.
 */

public class MovieDetailsFetcher extends AsyncTask<String,Void,MovieEntryDetails> implements MoviesDataFetcher.MoviesAsyncTaskOnFinish {
   long MovieId;
    Context Context;
    String Title;
    MoviesDetailsAsyncTaskOnFinish Listener;

    @Override
    public void OnFinishAsyncTask(List<MovieEntry> Entries) {

    }

    @Override
    public void OnErrorOnLoadingData(Exception ex) {

    }

    public interface MoviesDetailsAsyncTaskOnFinish
    {
        public void OnFinishAsyncTask(MovieEntryDetails Entries);
    }
    public MovieDetailsFetcher(Context context,long MovieId,String title,MoviesDetailsAsyncTaskOnFinish listener)
   {
       this.MovieId=MovieId;
       this.Context=context;
       this.Listener=listener;
       this.Title=title;
   }
    @Override
    protected MovieEntryDetails doInBackground(String... strings) {
        ArrayList<String> listImages= GetMovieImages(MovieId+"");
        ArrayList<Tuple> movieReviews = GetMovieReviews(MovieId + "");
        String tagLine=GetTagLine(this.MovieId+"");
        String overview=GetMainOverview(this.MovieId+"");
        ArrayList<String>trailers= GetMovieTrailer(this.MovieId+"");
        ArrayList<MovieEntry> similarsMovies = GetSimilarsMovies(this.MovieId + "");
        MovieEntryDetails details=new MovieEntryDetails(MovieId,Title,movieReviews,listImages,trailers,tagLine,overview,similarsMovies);
        return details;
    }
    @Override
    protected void onPostExecute(MovieEntryDetails movieEntries) {
        super.onPostExecute(movieEntries);
        this.Listener.OnFinishAsyncTask(movieEntries);

    }
    private ArrayList<String> GetMovieImages(String id) {
        ArrayList<String> returnedImagesPaths = new ArrayList<>();
        try {
            String urlImage = "http://image.tmdb.org/t/p/w500/";
            URL moviesURL = null;
            moviesURL = NetworkUtils.getURL(MainActivity.MoviesPrefix + "/" + id + "/" +  MainActivity.ImagePath + "?api_key=" + MainActivity.API_KEY);
            String jsonFileDataString = NetworkUtils.ReadDataFromURL(moviesURL);
            JSONObject movieImagesJson = new JSONObject(jsonFileDataString);
            JSONArray backdrops = movieImagesJson.getJSONArray("backdrops");
            for (int i = 0; i < backdrops.length(); i++) {
                JSONObject currenJsonObject = backdrops.getJSONObject(i);
                String file_path = currenJsonObject.getString("file_path");
                String str=urlImage+file_path;
                returnedImagesPaths.add(str);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedImagesPaths;
    }

    private ArrayList<String> GetMovieTrailer(String id) {
        ArrayList<String> returnedTrailerPaths = new ArrayList<>();
        try {
            URL moviesURL = null;
            moviesURL = NetworkUtils.getURL(MainActivity.MoviesPrefix + "/" + id + "/" +  MainActivity.TrailerPath + "?api_key=" + MainActivity.API_KEY);
            String jsonFileDataString = NetworkUtils.ReadDataFromURL(moviesURL);
            JSONObject movieImagesJson = new JSONObject(jsonFileDataString);
            JSONArray trailer = movieImagesJson.getJSONArray("results");
            for (int i = 0; i < trailer.length(); i++) {
                JSONObject currenJsonObject = trailer.getJSONObject(i);
                String file_path = currenJsonObject.getString("key");
                returnedTrailerPaths.add(file_path);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedTrailerPaths;
    }
    private ArrayList<Tuple> GetMovieReviews(String id) {
        ArrayList<Tuple> returnedtuples = new ArrayList<>();
        try {
            URL moviesURL = null;
            moviesURL = NetworkUtils.getURL(MainActivity.MoviesPrefix + "/" + id + "/" +  MainActivity.ReviewPath + "?api_key=" + MainActivity.API_KEY);
            String jsonFileDataString = NetworkUtils.ReadDataFromURL(moviesURL);
            JSONObject movieImagesJson = new JSONObject(jsonFileDataString);
            JSONArray reviews = movieImagesJson.getJSONArray("results");
            for (int i = 0; i < reviews.length(); i++) {
                JSONObject currenJsonObject = reviews.getJSONObject(i);
                String author = currenJsonObject.getString("author");
                String content = currenJsonObject.getString("content");
                Tuple review=new Tuple(author,content);
                returnedtuples.add(review);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedtuples;
    }
    private String GetTagLine(String id) {
        ArrayList<Tuple> returnedtuples = new ArrayList<>();
        try {
            URL moviesURL = null;
            moviesURL = NetworkUtils.getURL(MainActivity.MoviesPrefix + "/" + id  + "?api_key=" + MainActivity.API_KEY);
            String jsonFileDataString = NetworkUtils.ReadDataFromURL(moviesURL);
            JSONObject movieImagesJson = new JSONObject(jsonFileDataString);
           return movieImagesJson.getString("tagline");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    private String GetMainOverview(String id) {
        ArrayList<Tuple> returnedtuples = new ArrayList<>();
        try {
            URL moviesURL = null;
            moviesURL = NetworkUtils.getURL(MainActivity.MoviesPrefix + "/" + id  + "?api_key=" + MainActivity.API_KEY);
            String jsonFileDataString = NetworkUtils.ReadDataFromURL(moviesURL);
            JSONObject movieImagesJson = new JSONObject(jsonFileDataString);
            return movieImagesJson.getString("overview");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
    private ArrayList<MovieEntry>GetSimilarsMovies(String id)
    {
        List<MovieEntry> moviesEntries = new ArrayList<>();
        if (!NetworkUtils.IsOnline(this.Context)) {
            return (ArrayList<MovieEntry>) moviesEntries;
        }
        try {
            URL moviesURL = null;
            moviesURL = NetworkUtils.getURL(MainActivity.MoviesPrefix + "/" + id + "/" +  MainActivity.SimilarPath + "?api_key=" + MainActivity.API_KEY);
            String urlImage = "http://image.tmdb.org/t/p/w500/";
            String urlBackground = "http://image.tmdb.org/t/p/w1000/";
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
                moviesEntries.add(movieEntry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return (ArrayList<MovieEntry>) moviesEntries;
    }
}
