package com.example.android.popularmovies;

import android.os.AsyncTask;

public class MovieInfoRequestTask extends AsyncTask<String, Void, MovieInfo[]>{

    private MovieListAdapter adapter;

    MovieInfoRequestTask(MovieListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected MovieInfo[] doInBackground(String... strings) {

        return MocMovieInfoList.movieInfos;

    }

    @Override
    protected void onPostExecute(MovieInfo[] movieInfos) {

        adapter.setMovieInfoList(movieInfos);

    }
}
