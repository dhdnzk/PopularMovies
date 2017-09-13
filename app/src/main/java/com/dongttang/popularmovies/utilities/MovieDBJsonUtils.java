package com.dongttang.popularmovies.utilities;

import com.dongttang.popularmovies.datas.MovieInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDBJsonUtils {

    public static MovieInfo[] getMovieInfoArrayFromJsonString(String movieInfoJsonStr) throws JSONException {

        JSONObject jsonObject = new JSONObject(movieInfoJsonStr);
        JSONArray movieInfoArray = jsonObject.getJSONArray("results");
        MovieInfo[] resultMovieInfoList = new MovieInfo[movieInfoArray.length()];

        for (int i = 0; i < movieInfoArray.length(); i++) {

            JSONObject movieInfoJsonObject = movieInfoArray.getJSONObject(i);

            int voteCount = movieInfoJsonObject.getInt("vote_count");
            int id = movieInfoJsonObject.getInt("id");
            boolean video = movieInfoJsonObject.getBoolean("video");
            double voteAverage = movieInfoJsonObject.getDouble("vote_average");
            String title = movieInfoJsonObject.getString("title");
            double popularity = movieInfoJsonObject.getDouble("popularity");
            String posterPath = movieInfoJsonObject.getString("poster_path");
            String originalLanguage = movieInfoJsonObject.getString("original_language");
            String originalTitle = movieInfoJsonObject.getString("original_title");

            JSONArray genreIdJsonArray = movieInfoJsonObject.getJSONArray("genre_ids");
            int[] genreIds = new int[genreIdJsonArray.length()];
            for (int j = 0; j < genreIdJsonArray.length(); j++) {
                genreIds[j] = genreIdJsonArray.getInt(j);
            }

            String backdropPath = movieInfoJsonObject.getString("backdrop_path");
            boolean adult = movieInfoJsonObject.getBoolean("adult");
            String overview = movieInfoJsonObject.getString("overview");
            String releaseDate = movieInfoJsonObject.getString("release_date");

            resultMovieInfoList[i] = new MovieInfo(
                    voteCount,
                    id,
                    video,
                    voteAverage,
                    title,
                    popularity,
                    posterPath,
                    originalLanguage,
                    originalTitle,
                    genreIds,
                    backdropPath,
                    adult,
                    overview,
                    releaseDate);

        }

        return resultMovieInfoList;

    }

}
