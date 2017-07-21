package com.example.android.popularmovies;

import android.os.AsyncTask;

import com.example.android.popularmovies.utilities.MovieDBJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MovieInfoRequestionTask extends AsyncTask<String, Void, MovieInfo[]>{

    private MovieListAdapter adapter;

    public MovieInfoRequestionTask(MovieListAdapter adapter) {

        this.adapter = adapter;

    }

    @Override
    protected MovieInfo[] doInBackground(String... strings) {

        String responsedJsonString = null;

        try {

            responsedJsonString = NetworkUtils.getResponseFromHttpUrl(new URL(strings[0]));

        } catch (IOException e) {

            e.printStackTrace();

        }

        return MovieDBJsonUtils.getSimpleWeatherStringsFromJson(responsedJsonString);

    }

    @Override
    protected void onPostExecute(MovieInfo[] movieInfoList) {

        adapter.setMovieInfoList(movieInfoList);

    }

}
