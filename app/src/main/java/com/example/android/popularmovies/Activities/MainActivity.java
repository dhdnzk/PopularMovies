package com.example.android.popularmovies.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.android.popularmovies.MovieInfo;
import com.example.android.popularmovies.MovieListAdapter;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utilities.MovieDBJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.RecyclerViewClickListener {

    private RecyclerView movieListRecyclerView;
    private MovieListAdapter movieListAdapter;
    private ConstraintLayout errorPageLayout;
    private ProgressBar reloadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        movieListAdapter = new MovieListAdapter(this, metrics);

        movieListRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        movieListRecyclerView.setLayoutManager(layoutManager);
        movieListRecyclerView.setHasFixedSize(true);

        errorPageLayout = (ConstraintLayout) findViewById(R.id.cl_error_page);
        ImageView refreshButton = (ImageView) findViewById(R.id.iv_refresh);

        reloadingProgressBar = (ProgressBar) findViewById(R.id.pb_reloading);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadPage(NetworkUtils.LAST_REQUESTED_URL);

            }
        });

        loadPage(NetworkUtils.POPULAR_MOVIE_URL);

    }

    @Override
    public void onListItemClicked(int clickedItemIdx) {

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movieInfo", movieListAdapter.getMovieInfoList()[clickedItemIdx]);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.it_main_set_as_rate:
                loadPage(NetworkUtils.TOP_RATED_MOVIE_URL);
                break;

            case R.id.it_main_set_as_popularity:
                loadPage(NetworkUtils.POPULAR_MOVIE_URL);
                break;

            default:
                break;
        }

        return true;
    }

    private void loadPage(String requestingUrlString) {

        movieListAdapter.setMovieInfoList(null);

        switch (requestingUrlString) {

            case NetworkUtils.TOP_RATED_MOVIE_URL:
                NetworkUtils.LAST_REQUESTED_URL = NetworkUtils.TOP_RATED_MOVIE_URL;
                new MovieInfoRequestingTask().execute(NetworkUtils.TOP_RATED_MOVIE_URL);
                break;

            case NetworkUtils.POPULAR_MOVIE_URL:
                NetworkUtils.LAST_REQUESTED_URL = NetworkUtils.POPULAR_MOVIE_URL;
                new MovieInfoRequestingTask().execute(NetworkUtils.POPULAR_MOVIE_URL);
                break;

            default:
                new MovieInfoRequestingTask().execute(NetworkUtils.LAST_REQUESTED_URL);
                break;

        }

    }

    private class MovieInfoRequestingTask extends AsyncTask<String, Void, MovieInfo[]> {

        @Override
        protected void onPreExecute() {

            movieListRecyclerView.setVisibility(View.INVISIBLE);
            errorPageLayout.setVisibility(View.INVISIBLE);
            reloadingProgressBar.setVisibility(View.VISIBLE);

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

            if(movieInfoList == null) {

                movieListRecyclerView.setVisibility(View.INVISIBLE);
                reloadingProgressBar.setVisibility(View.INVISIBLE);
                errorPageLayout.setVisibility(View.VISIBLE);

                return;

            }

            movieListRecyclerView.setVisibility(View.VISIBLE);
            reloadingProgressBar.setVisibility(View.INVISIBLE);
            errorPageLayout.setVisibility(View.INVISIBLE);

            movieListAdapter.setMovieInfoList(movieInfoList);
            movieListRecyclerView.setAdapter(movieListAdapter);



        }

    }

}

// polishing & bug fix
// TODO: 액티비티별로 타이틀 이름 바꾸기
// TODO: 영화 상세 페이지에서 back 텍스트 화살표로 바꾸고 위치 수정
// TODO: 위로 당기면 새로고침
// TODO: 고무줄 효과 적용해서 그림이 자동으로 페이지 중앙으로 오도록
// TODO: 메인 페이지 가로로 했을때 오른쪽으로 4개 나열되도록 레이아웃 수정
// TODO: 영화 디테일 페이지 가로로 했을때 영화 오버뷰 오른쪽으로 빠지도록 레이아웃 수정
// TODO: 리사이클러 뷰 드래그 빠르게 했을때 버벅거림 수정