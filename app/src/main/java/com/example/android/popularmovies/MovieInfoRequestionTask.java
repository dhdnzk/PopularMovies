package com.example.android.popularmovies;

import android.os.AsyncTask;

class MovieInfoRequestionTask extends AsyncTask<String, Void, MovieInfo[]>{

    private MovieListAdapter adapter;

    MovieInfoRequestionTask(MovieListAdapter adapter) {

        this.adapter = adapter;

    }

    @Override
    protected MovieInfo[] doInBackground(String... strings) {

        return MockMovieInfoList.movieInfos;

    }

    @Override
    protected void onPostExecute(MovieInfo[] movieInfoList) {

        adapter.setMovieInfoList(movieInfoList);

    }

}
