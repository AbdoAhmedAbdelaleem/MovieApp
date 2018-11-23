package com.example.abdo.moviesstage1;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abdo on 9/19/2017.
 */

public class GridAdapter extends ArrayAdapter<MovieEntry> {
    ArrayList<MovieEntry> Data;
    Context Context;
    ButtonLikeItemClickListener Listener;

    public GridAdapter(Context context, ArrayList<MovieEntry> data, ButtonLikeItemClickListener listener) {
        super(context, R.layout.grid_item, data);
        this.Context = context;
        this.Data = data;
        this.Listener=listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.Context).inflate(R.layout.grid_item, parent, false);
            EntryHolder holder = new EntryHolder(convertView, this.Listener);
            holder.ItemPositionInAdapter=position;
            convertView.setTag(holder);
           }
         EntryHolder hold= (EntryHolder) convertView.getTag();
        hold.ItemId=Data.get(position).movieID;
        boolean isFound=false;
        View likeView= convertView.findViewById(R.id.likeImageViewItemGrid);
        for (Long str: MainActivity.FavouriteMovies)
        {
            if(MainActivity.FavouriteMovies.contains(Data.get(position).getMovieID()))
            {
                likeView.setBackgroundResource(R.drawable.like);
                isFound=true;
                break;
            }
        }
        if(!isFound)
        {
            likeView.setBackgroundResource(R.drawable.dislike);
        }
        EntryHolder tagHolder = (EntryHolder) convertView.getTag();
        MovieEntry movieEntry = this.Data.get(position);
        Bitmap posterImage = movieEntry.moviePosterIMG;
        String imgSURlString = movieEntry.moviePosterPath;
        tagHolder.RateTextView.setText(movieEntry.getMovieVoteAverage() + "");
        tagHolder.TitleTextView.setText(movieEntry.getMovieTitle());
        Picasso.with(convertView.getContext()).load(imgSURlString).placeholder(tagHolder.posterImageView.getDrawable()).into(tagHolder.posterImageView);
        //imgView.setImageResource(R.drawable.wonderfilm);
        //imgView.setImageBitmap(posterImage);
        return convertView;
    }

    public class EntryHolder implements View.OnClickListener {
        public int ItemPositionInAdapter;
        public ImageView posterImageView;
        public TextView TitleTextView;
        public TextView RateTextView;
        public Button likeImageView;
        private View ParentView;
        public Long ItemId;
        ButtonLikeItemClickListener Listener;

        public EntryHolder(View convertView, ButtonLikeItemClickListener listener) {
            posterImageView = convertView.findViewById(R.id.gridImage);
            RateTextView = convertView.findViewById(R.id.RateItemMainGridview);
            TitleTextView = convertView.findViewById(R.id.TitleItemMainGridview);
            likeImageView = convertView.findViewById(R.id.likeImageViewItemGrid);
            likeImageView.setOnClickListener(this);
            this.Listener = listener;
            this.ParentView = convertView;
        }

        @Override
        public void onClick(View view) {
            this.Listener.OnLikeClick(this.ItemPositionInAdapter, this.ParentView);

        }
    }

    public interface ButtonLikeItemClickListener {
        public void OnLikeClick(int position, View parentView);
    }
}

