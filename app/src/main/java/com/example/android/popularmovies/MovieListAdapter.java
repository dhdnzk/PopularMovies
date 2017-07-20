package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private MovieInfo[] movieInfoList;
    private final RecyclerViewClickListener recyclerViewClickListener;

    MovieListAdapter(RecyclerViewClickListener recyclerViewClickListener) {

        this.recyclerViewClickListener = recyclerViewClickListener;
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

        if(movieInfoList == null) {
            return 0;
        }

        return movieInfoList.length;
    }

    void setMovieInfoList(MovieInfo[] movieInfoList) {

        this.movieInfoList = movieInfoList;

    }

    MovieInfo[] getMovieInfoList() {

        return movieInfoList;

    }

    class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView imagePosterUrl;

        MovieListViewHolder(View itemView) {

            super(itemView);

            imagePosterUrl = itemView.findViewById(R.id.tv_tmp_poster_url);

        }

        void bind(int position) {

            imagePosterUrl.setText(movieInfoList[position].getPosterImageUrl());

        }

        @Override
        public void onClick(View view) {

            recyclerViewClickListener.onListClick(getAdapterPosition());

        }
    }

    interface RecyclerViewClickListener {

        void onListClick(int position);

    }

}
