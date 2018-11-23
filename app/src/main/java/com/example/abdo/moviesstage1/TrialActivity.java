package com.example.abdo.moviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class TrialActivity extends AppCompatActivity implements RecyclerAdapter.ListItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);
        RecyclerView recycleView = (RecyclerView) findViewById(R.id.rc_view);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this,recycleView.HORIZONTAL,false);
        recycleView.setLayoutManager(layoutManager);
        Intent intent= getIntent();
        if(intent!=null)
        {
            Object obj= getIntent().getParcelableExtra("DataMovies");
            ArrayList<MovieEntry> movies = (ArrayList<MovieEntry>) getIntent().getParcelableExtra("DataMovies");
           if(MainActivity.entries!=null)
           {
               /*
               Toast.makeText(this,"kmnjkiuhuyg",Toast.LENGTH_SHORT).show();
               RecyclerAdapter adapter=new RecyclerAdapter(this,MainActivity.entries,this);
               recycleView.setAdapter(adapter);*/
           }
        }
    }


    @Override
    public void OnListItemClickListener(int position, MovieDetailsTag tag) {
        Intent inten=new Intent(TrialActivity.this,MovieDeatails.class);
        inten.putExtra("myDataKey",MainActivity.entries.get(position));
        startActivity(inten);
    }

    @Override
    public void OnListItemLongClickListener(int position, MovieDetailsTag tag) {

    }
}
