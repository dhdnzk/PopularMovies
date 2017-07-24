package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.android.popularmovies.utilities.MovieDBJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MovieInfoRequestingTask extends AsyncTask<String, Void, MovieInfo[]>{

    private RecyclerView recyclerView;
    private MovieListAdapter adapter;

    public MovieInfoRequestingTask(RecyclerView recyclerView, MovieListAdapter adapter) {

        this.recyclerView = recyclerView;
        this.adapter = adapter;

    }

    @Override
    protected MovieInfo[] doInBackground(String... strings) {

        String targetUrl = strings[0];
        String respondedJsonString;
        MovieInfo[] movieInfoList = null;

        try {

            respondedJsonString = NetworkUtils.getResponseFromHttpUrl(new URL(targetUrl));
            movieInfoList = MovieDBJsonUtils.getMovieInfoArrayFromJsonString(respondedJsonString);

        } catch (IOException | JSONException e) {

            e.printStackTrace();

        }

        return movieInfoList;

    }

    @Override
    protected void onPostExecute(MovieInfo[] movieInfoList) {

        adapter.setMovieInfoList(movieInfoList);
        recyclerView.setAdapter(adapter);

    }

}
