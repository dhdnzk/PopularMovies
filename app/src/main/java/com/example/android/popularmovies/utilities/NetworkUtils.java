package com.example.android.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static final String POSTER_BASIC_URL = "http://image.tmdb.org/t/p/";

    public static final String POSTER_SIZE = "w185";

    public static String API_KEY;


    public static final String POPULAR_MOVIE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;

    public static URL buildPosterUrl(String posterPath) {

        Uri targetUri = Uri.parse(POSTER_BASIC_URL).buildUpon().appendPath(posterPath).build();

        URL targetUrl = null;

        try {
            targetUrl = new URL(targetUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return targetUrl;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);

            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {

                return scanner.next();

            } else {

                return null;

            }

        } finally {

            urlConnection.disconnect();

        }

    }

}
