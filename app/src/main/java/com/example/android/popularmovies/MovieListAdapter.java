package com.example.android.popularmovies;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private final RecyclerViewClickListener recyclerViewClickListener;
    private final DisplayMetrics displayMetrics;
    private final Context context;
    private MovieInfo[] movieInfoList;

    public MovieListAdapter(Context context, RecyclerViewClickListener recyclerViewClickListener, DisplayMetrics displayMetrics) {

        this.movieInfoList = null;

        this.recyclerViewClickListener = recyclerViewClickListener;

        this.displayMetrics = displayMetrics;

        this.context = context;

    }

    @Override
    public MovieListAdapter.MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.movie_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new MovieListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {

        holder.bind(position);

    }

    @Override
    public int getItemCount() {

        if (movieInfoList == null) {

            return 0;

        }

        return movieInfoList.length;

    }

    public MovieInfo[] getMovieInfoList() {

        return movieInfoList;

    }

    public void setMovieInfoList(MovieInfo[] movieInfoList) {

        this.movieInfoList = movieInfoList;

    }

    public interface RecyclerViewClickListener {

        void onListItemClicked(int position);

    }

    class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView posterImage;

        MovieListViewHolder(View itemView) {

            super(itemView);

            posterImage = itemView.findViewById(R.id.iv_posterImage);

            itemView.setOnClickListener(this);

        }

        void bind(int position) {

            String posterImageUrl =
                    NetworkUtils.POSTER_BASIC_URL +
                            NetworkUtils.POSTER_SIZE +
                            movieInfoList[position].getPosterPath();

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                Picasso.with(itemView.getContext())
                        .load(posterImageUrl)
                        .resize(displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2)
                        .centerCrop()
                        .into(posterImage);

            } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                Picasso.with(itemView.getContext())
                        .load(posterImageUrl)
                        .resize(displayMetrics.widthPixels / 4, displayMetrics.heightPixels)
                        .centerCrop()
                        .into(posterImage);

            }

        }

        @Override
        public void onClick(View view) {

            recyclerViewClickListener.onListItemClicked(getAdapterPosition());

        }

    }

}
