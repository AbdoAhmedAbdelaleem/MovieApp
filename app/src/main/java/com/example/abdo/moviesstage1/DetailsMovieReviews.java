package com.example.abdo.moviesstage1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsMovieReviews extends AppCompatActivity {

    TextView ReviewTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie_reviews);
        ReviewTextView= (TextView) findViewById(R.id.ReviewSeeMore);
        Intent intent = getIntent();
        ReviewTextView.setText("");
        if(intent !=null)
        {
            Object parcelable= intent.getParcelableExtra("Details");
            if(parcelable!=null)
            {
                MovieEntryDetails details  = (MovieEntryDetails)parcelable;
                ArrayList<Tuple>reviews= details.getReviews();
                for(int i=0;i<reviews.size();i++)
                {
                    String first = reviews.get(i).Item1;
                    String next = "<font color='#EE0000'>"+first+"</font>";
                    ReviewTextView.append(Html.fromHtml(next));
                    ReviewTextView.append("\n");
                    ReviewTextView.append(reviews.get(i).Item2);
                    ReviewTextView.append("\n\n\n");
                }
            }
        }
    }
}
