package com.example.android.popularmovies;

import android.os.AsyncTask;

import com.example.android.popularmovies.utilities.MovieDBJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MovieInfoRequestionTask extends AsyncTask<String, Void, MovieInfo[]>{

    private MovieListAdapter adapter;

    public MovieInfoRequestionTask(MovieListAdapter adapter) {

        this.adapter = adapter;

    }

    @Override
    protected MovieInfo[] doInBackground(String... strings) {

        String respondedJsonString = null;
        MovieInfo[] movieInfoList = new MovieInfo[0];

        try {

            respondedJsonString = NetworkUtils.getResponseFromHttpUrl(new URL(strings[0]));
            movieInfoList = MovieDBJsonUtils.getMovieInfoArrayFromJsonString(respondedJsonString);

        } catch (IOException | JSONException e) {

            e.printStackTrace();

        }

        return movieInfoList;

    }

    @Override
    protected void onPostExecute(MovieInfo[] movieInfoList) {

        adapter.setMovieInfoList(movieInfoList);

    }

}
