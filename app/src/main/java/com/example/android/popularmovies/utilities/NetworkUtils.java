package com.example.android.popularmovies.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String API_KEY = ApiKey.API_KEY;
    public static final String POSTER_BASIC_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_SIZE = "w185";
    public static final String POPULAR_MOVIE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
    public static final String TOP_RATED_MOVIE_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;
    public static String CURRENT_REQUESTED_URL = POPULAR_MOVIE_URL;

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

        }
        finally {

            urlConnection.disconnect();

        }

    }

}
