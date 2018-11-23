package com.example.abdo.moviesstage1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdo.moviesstage1.Data.MovieContract;
import com.example.abdo.moviesstage1.Data.MovieDBHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDeatails extends AppCompatActivity implements RecyclerAdapter.ListItemClickListener,MovieDetailsFetcher.MoviesDetailsAsyncTaskOnFinish {

    public MovieEntryDetails Details;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        MovieEntry object = (MovieEntry) getIntent().getParcelableExtra("myDataKey");
        MovieEntry movieEntry = object;
        if(movieEntry!=null)
        {
            DisplayDetails(movieEntry);
            new MovieDetailsFetcher(this,movieEntry.movieID,movieEntry.movieTitle,this).execute();
        }
    }
    public void ReadMoreStoryButton(View view)
    {
        Intent intent =new Intent(MovieDeatails.this,DetailsMovieStory.class);
        intent.putExtra("Details",Details);
        startActivity(intent);

    }
    public void ReadMoreReviews(View view)
    {
        Intent intent =new Intent(MovieDeatails.this,DetailsMovieReviews.class);
        intent.putExtra("Details",Details);
        startActivity(intent);

    }
private void DisplayDetails(MovieEntry movieEntry)
{
    TextView OvervieTextView= (TextView) findViewById(R.id.MovieOverview);
    TextView releaseDateTextview= (TextView) findViewById(R.id.RealeaseDate);
    TextView VoteAverageTextView= (TextView) findViewById(R.id.VoteAverage);
    OvervieTextView.setText(movieEntry.getMovieOverview());
    releaseDateTextview.setText(movieEntry.getMovieRelease_date());
    VoteAverageTextView.setText(movieEntry.getMovieVoteAverage()+"");
    ImageView backDropPathImgView= (ImageView) findViewById(R.id.BackDopPathImgView);
    ImageView posterImgView= (ImageView) findViewById(R.id.posterImgView);
    Picasso.with(this).load(movieEntry.moviePosterPath).into(posterImgView);
    Picasso.with(this).load(movieEntry.getMovieBackdropPath()).into(backDropPathImgView);
    Button likeButton= (Button) findViewById(R.id.detailsLikeButton);
    Cursor cursor= getContentResolver().query(MovieContract.MovieEntry.GeneralURi,null,null,null,null);
    cursor.moveToFirst();
    MainActivity.FavouriteMovies=new ArrayList<>();
    while (cursor.moveToNext())
    {
        int indexId= cursor.getColumnIndex(MovieContract.MovieEntry.MovieId);
        MainActivity.FavouriteMovies.add(cursor.getLong(indexId));
    }
    if( MainActivity.FavouriteMovies.contains(movieEntry.getMovieID())) {
        likeButton.setBackgroundResource(R.drawable.like);
    }
    else
    {
        likeButton.setBackgroundResource(R.drawable.dislike);
    }
   TextView titleTextView= (TextView) findViewById(R.id.titleTextView);
    titleTextView.setText(movieEntry.getMovieTitle());
}
    @Override
    public void OnListItemClickListener(int position,MovieDetailsTag Tag) {
        if(Tag==MovieDetailsTag.Trailer)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+Details.Trailers.get(position))));
        }
        if(Tag==MovieDetailsTag.Similar)
        {
            Intent currentIntent=getIntent();
            currentIntent.putExtra("myDataKey",this.Details.SimilarMovies.get(position));
            currentIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(currentIntent);
        }
    }
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public void AddToFavourite(View view)
{
    boolean isFound=false;
       if(MainActivity.FavouriteMovies.contains(Details.getMovieID()))
        {
           // MainActivity.FavouriteMovies.remove(Details.getMovieID());
            view.setBackgroundResource(R.drawable.dislike);
            Uri uri = ContentUris.withAppendedId(MovieContract.MovieEntry.GeneralURi, Details.getMovieID());
            getContentResolver().delete(uri,null,null);
           Cursor cursor= getContentResolver().query(MovieContract.MovieEntry.GeneralURi,null,null,null,null);
            cursor.moveToFirst();
            MainActivity.FavouriteMovies=new ArrayList<>();
            while (cursor.moveToNext())
            {
               int indexId= cursor.getColumnIndex(MovieContract.MovieEntry.MovieId);
                MainActivity.FavouriteMovies.add(cursor.getLong(indexId));
            }
        }

    else
    {
      //  MainActivity.FavouriteMovies.add(Details.getMovieID());
        view.setBackgroundResource(R.drawable.like);
        ContentValues values=new ContentValues();
        values.put(MovieContract.MovieEntry.MovieId,Details.getMovieID());
        values.put(MovieContract.MovieEntry.MovieTitle,Details.Title);
        getContentResolver().insert(MovieContract.MovieEntry.GeneralURi,values);
        Cursor cursor= getContentResolver().query(MovieContract.MovieEntry.GeneralURi,null,null,null,null);
        cursor.moveToFirst();
        MainActivity.FavouriteMovies=new ArrayList<>();
        while (cursor.moveToNext())
        {
            int indexId= cursor.getColumnIndex(MovieContract.MovieEntry.MovieId);
            MainActivity.FavouriteMovies.add(cursor.getLong(indexId));
        }
    }
}
    @Override
    public void OnListItemLongClickListener(int position,MovieDetailsTag Tag) {

    }

    @Override
    public void OnFinishAsyncTask(MovieEntryDetails Entries)

    {
        //traileer
       Details=Entries;
        RecyclerView recycleView= (RecyclerView) findViewById(R.id.MovieVideoxRecyclerView);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this,recycleView.HORIZONTAL,false);
        recycleView.setLayoutManager(layoutManager);

        RecyclerAdapter adapter=new RecyclerAdapter(this,this.Details,this,MovieDetailsTag.Trailer);
        recycleView.setAdapter(adapter);

        //Similars
        recycleView= (RecyclerView) findViewById(R.id.SimiliarRecycleView);
        layoutManager =new LinearLayoutManager(this,recycleView.HORIZONTAL,false);
        recycleView.setLayoutManager(layoutManager);

        adapter=new RecyclerAdapter(this,this.Details,this,MovieDetailsTag.Similar);
        recycleView.setAdapter(adapter);
        TextView taglineTextView= (TextView) findViewById(R.id.MovieTaagLine);
        taglineTextView.setText(Details.TagLine);
        if(Details.getReviews().size()>0) {
           Tuple firstReview= Details.getReviews().get(0);
            TextView MovieReviewsTextView = (TextView) findViewById(R.id.MovieReviews);
            MovieReviewsTextView.setText(firstReview.Item1+" "+firstReview.Item2);
        }
    }
}
