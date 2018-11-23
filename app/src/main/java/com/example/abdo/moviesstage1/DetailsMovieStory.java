package com.example.abdo.moviesstage1;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class DetailsMovieStory extends AppCompatActivity implements RecyclerAdapter.ListItemClickListener {

    TextView TagLineTextView;
    TextView DetailsStoryTextView;
    RecyclerView StoryRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_movie_story);
        // intent.putExtra("Details",Details);
        TagLineTextView= (TextView) findViewById(R.id.StoryTagLine);
        DetailsStoryTextView= (TextView) findViewById(R.id.StoryDetails);
        StoryRecyclerView= (RecyclerView) findViewById(R.id.StoryRecyclerview);
        Intent intent = getIntent();
        if(intent !=null)
        {
           Object parcelable= intent.getParcelableExtra("Details");
            if(parcelable!=null)
            {
              MovieEntryDetails details  = (MovieEntryDetails)parcelable;
                RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this,StoryRecyclerView.HORIZONTAL,false);
                StoryRecyclerView.setLayoutManager(layoutManager);
                RecyclerAdapter adapter=new RecyclerAdapter(this,details,this,MovieDetailsTag.Gallary);
                StoryRecyclerView.setAdapter(adapter);
                TagLineTextView.setText(details.TagLine);
                DetailsStoryTextView.setText(details.Overview);
            }
        }
    }


    @Override
    public void OnListItemClickListener(int position, MovieDetailsTag tag) {

    }

    @Override
    public void OnListItemLongClickListener(int position, MovieDetailsTag tag) {

    }
}
