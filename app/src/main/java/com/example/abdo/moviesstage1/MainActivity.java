package com.example.abdo.moviesstage1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abdo.moviesstage1.Data.MovieContract;
import com.example.abdo.moviesstage1.Data.MovieDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GridAdapter.ButtonLikeItemClickListener, MoviesDataFetcher.MoviesAsyncTaskOnFinish {

    public static final String API_KEY = BuildConfig.API_Key;
    public String mostPopularityUri = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=en-US&page=1";
    public String topRatedURi = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY + "&language=en-US&page=1%20Code%20Generation";
    public static String MoviesPrefix = "https://api.themoviedb.org/3/movie";
    public static String ImagePath = "images";
    public static String SimilarPath = "similar";
    public static String ReviewPath = "reviews";
    public static String TrailerPath = "videos";
    View mEmptyView;
    ProgressBar progressBar;
    public static List<Long> FavouriteMovies = new ArrayList<>();
    public String currentURi;
    GridView gridView = null;
    Menu sortMenu = null;
    Context context;
    private GridAdapter adapter;
    public static ArrayList<MovieEntry> entries = new ArrayList<>();
    public static ArrayList<MovieEntry> LikedMovies = new ArrayList<>();
    public static DataFetchercritiria CuurentFetcherCritiria = DataFetchercritiria.Popular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        currentURi = mostPopularityUri;
        gridView = (GridView) findViewById(R.id.gridView);

         mEmptyView = findViewById(R.id.emptyGridView);
         mEmptyView.setVisibility(View.INVISIBLE);
         progressBar= (ProgressBar) findViewById(R.id.idPrgreeBar);
      //  gridView.setEmptyView(emptyView);
        entries = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        new MoviesDataFetcher(this, this, DataFetchercritiria.Popular).execute(currentURi);
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.GeneralURi, null, null, null, null);
        cursor.moveToFirst();
        MainActivity.FavouriteMovies = new ArrayList<>();
        while (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(MovieContract.MovieEntry.MovieId);
            MainActivity.FavouriteMovies.add(cursor.getLong(indexId));
        }
        adapter = new GridAdapter(this, entries, this);
        gridView.setAdapter(adapter);
        SetGridViewClick();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("currentURI", currentURi);
        int value = 0;
        if (CuurentFetcherCritiria == DataFetchercritiria.TopRated)
            value = 1;
        else if (CuurentFetcherCritiria == DataFetchercritiria.Favourite)
            value = 2;

        outState.putInt("currentCritaria", value);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentURi = savedInstanceState.getString("currentURI");
        int value = savedInstanceState.getInt("currentCritaria");

        if (value == 0)
            CuurentFetcherCritiria = DataFetchercritiria.Popular;
        if (value == 1)
            CuurentFetcherCritiria = DataFetchercritiria.TopRated;
        if (value == 2)
            CuurentFetcherCritiria = DataFetchercritiria.Favourite;
    }

    public void OnClickRetryButton(View view) {
        new MoviesDataFetcher(this, this, DataFetchercritiria.Popular).execute(currentURi);
    }

    private void AdjustMenuItemsVisibility() {
        MenuItem sortByPopularityItem = sortMenu.findItem(R.id.sortByPopularity);
        MenuItem sortByTopRatedItem = sortMenu.findItem(R.id.sortByTopRated);
        sortByPopularityItem.setVisible(!sortByPopularityItem.isVisible());
        sortByTopRatedItem.setVisible(!sortByTopRatedItem.isVisible());

    }

    private void SetGridViewClick() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MovieEntry dataToSend = entries.get(position);
                Intent i = new Intent(MainActivity.this, MovieDeatails.class);
                i.putExtra("myDataKey", dataToSend); // using the (String name, Parcelable value) overload!
                startActivity(i); // dataToSend is now passed
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        sortMenu = menu;
        menu.findItem(R.id.sortByPopularity).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortByPopularity:
                currentURi = mostPopularityUri;
                new MoviesDataFetcher(this, this, DataFetchercritiria.Popular).execute(currentURi);
                CuurentFetcherCritiria = DataFetchercritiria.Popular;
                break;
            case R.id.sortByTopRated:
                currentURi = topRatedURi;
                new MoviesDataFetcher(this, this, DataFetchercritiria.Popular).execute(currentURi);
                CuurentFetcherCritiria = DataFetchercritiria.TopRated;
                break;
            case R.id.SortByFavourite:
                currentURi = topRatedURi;
                Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.GeneralURi, null, null, null, null);
                cursor.moveToFirst();
                MainActivity.FavouriteMovies = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int indexId = cursor.getColumnIndex(MovieContract.MovieEntry.MovieId);
                    MainActivity.FavouriteMovies.add(cursor.getLong(indexId));
                }
                new MoviesDataFetcher(this, this, DataFetchercritiria.Favourite).execute(mostPopularityUri, topRatedURi);
                CuurentFetcherCritiria = DataFetchercritiria.Favourite;
                break;
        }
        AdjustMenuItemsVisibility();
        return true;
    }


    @Override
    public void OnLikeClick(int position, View parentView) {
        GridAdapter.EntryHolder tagHolder = (GridAdapter.EntryHolder) parentView.getTag();
        boolean isFound = false;

        if (MainActivity.FavouriteMovies.contains(tagHolder.ItemId)) {
            // MainActivity.FavouriteMovies.remove(tagHolder.ItemId);

            Uri uri = ContentUris.withAppendedId(MovieContract.MovieEntry.GeneralURi, tagHolder.ItemId);
            getContentResolver().delete(uri, null, null);
            Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.GeneralURi, null, null, null, null);
            cursor.moveToFirst();
            MainActivity.FavouriteMovies = new ArrayList<>();
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(MovieContract.MovieEntry.MovieId);
                MainActivity.FavouriteMovies.add(cursor.getLong(indexId));
            }

            tagHolder.likeImageView.setBackgroundResource(R.drawable.dislike);
            if (CuurentFetcherCritiria == DataFetchercritiria.Favourite) {
                for (MovieEntry entry : MainActivity.entries) {
                    if (entry.getMovieID() == tagHolder.ItemId) {
                        MainActivity.entries.remove(entry);
                        break;
                    }
                }
            }
        } else {
            // MainActivity.FavouriteMovies.add(tagHolder.ItemId);
            tagHolder.likeImageView.setBackgroundResource(R.drawable.like);
            ContentValues values = new ContentValues();
            values.put(MovieContract.MovieEntry.MovieId, tagHolder.ItemId);
            values.put(MovieContract.MovieEntry.MovieTitle, tagHolder.TitleTextView.getText().toString());
            getContentResolver().insert(MovieContract.MovieEntry.GeneralURi, values);
            Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.GeneralURi, null, null, null, null);
            cursor.moveToFirst();
            MainActivity.FavouriteMovies = new ArrayList<>();
            while (cursor.moveToNext()) {
                int indexId = cursor.getColumnIndex(MovieContract.MovieEntry.MovieId);
                MainActivity.FavouriteMovies.add(cursor.getLong(indexId));
                cursor.getLong(indexId);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnFinishAsyncTask(List<MovieEntry> Entries) {
        mEmptyView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        adapter = new GridAdapter(getBaseContext(), (ArrayList<MovieEntry>) Entries, MainActivity.this);
        gridView.setAdapter(adapter);
        MainActivity.entries = (ArrayList<MovieEntry>) Entries;
    }

    @Override
    public void OnErrorOnLoadingData(Exception ex) {
       mEmptyView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }
}
