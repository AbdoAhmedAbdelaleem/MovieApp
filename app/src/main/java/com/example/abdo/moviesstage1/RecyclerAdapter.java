package com.example.abdo.moviesstage1;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdo on 9/21/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MoviewViewHolder> {
    Context Context;
    MovieEntryDetails Movies;
    ListItemClickListener Listener;
    MovieDetailsTag Tag;
    List<String>ImagePaths;
    RecyclerAdapter(Context contex,MovieEntryDetails movies,ListItemClickListener listener,MovieDetailsTag tag )
    {
        super();
        this.Context=contex;
        this.Movies= movies;
        this.Listener=listener;
        this.Tag=tag;
        if(tag== MovieDetailsTag.Similar)
        {
            ImagePaths=new ArrayList<>();
            for (MovieEntry entry:movies.SimilarMovies)
            {
                ImagePaths.add(entry.getMoviePosterPath());
            }
        }
        if(tag == MovieDetailsTag.Gallary || tag== MovieDetailsTag.Trailer)
            ImagePaths=movies.ImagesPaths;
    }

    @Override
    public MoviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_details,parent,false);
       MoviewViewHolder holder=new MoviewViewHolder(convertView);
        return holder;
    }
public interface ListItemClickListener
{
    void OnListItemClickListener(int position,MovieDetailsTag tag);
    void OnListItemLongClickListener(int position,MovieDetailsTag tag);

}
    @Override
    public void onBindViewHolder(MoviewViewHolder holder, int position) {
       holder.Bind(ImagePaths.get(position));

    }

    @Override
    public int getItemCount()
    {
        if(this.Tag== MovieDetailsTag.Trailer)
        {
           return this.Movies.Trailers.size();
        }
        else if(this.Tag== MovieDetailsTag.Similar)
            return  this.Movies.SimilarMovies.size();
        return this.Movies.getImagesPaths().size();
    }
    class MoviewViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener,View.OnLongClickListener {
        ImageView imgview;
        public View Parent;
        public MoviewViewHolder(View itemView) {
            super(itemView);
            this.imgview=itemView.findViewById(R.id.gridImage);
//            itemView.findViewById(R.id.TitleItemMainGridview).setVisibility(View.GONE);
//            itemView.findViewById(R.id.RateItemMainGridview).setVisibility(View.GONE);
//            itemView.findViewById(R.id.likeImageViewItemGrid).setVisibility(View.GONE);
            this.Parent=itemView;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void Bind(String path)
        {
            String imgSURlString=path;
            Picasso.with(this.Parent.getContext()).load(imgSURlString).into(this.imgview);
        }


        @Override
        public void onClick(View view) {
         Listener.OnListItemClickListener(getAdapterPosition(),Tag);
        }

        @Override
        public boolean onLongClick(View view) {
            Listener.OnListItemLongClickListener(getAdapterPosition(),Tag);
            return false;
        }
    }
}

