package com.example.android.popularmovies.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

public class MainActivity extends AppCompatActivity implements MovieListAdapter.RecyclerViewClickListener, LoaderManager.LoaderCallbacks<MovieInfo[]> {

    private RecyclerView movieListRecyclerView;
    private MovieListAdapter movieListAdapter;
    private ConstraintLayout errorPageLayout;
    private ProgressBar reloadingProgressBar;

    private static final String TARGET_URL_BUNDLE_KEY = "targetUrl";
    private static final int QUERY_REQUESTING_LOADER_ID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager layoutManager;

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        }
        else {

//            layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        }

        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        movieListAdapter = new MovieListAdapter(this, this, metrics);

        movieListRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);

        movieListRecyclerView.setLayoutManager(layoutManager);

        movieListRecyclerView.setHasFixedSize(true);

        errorPageLayout = (ConstraintLayout) findViewById(R.id.cl_error_page);

        ImageView refreshButton = (ImageView) findViewById(R.id.iv_refresh);

        getSupportLoaderManager().initLoader(QUERY_REQUESTING_LOADER_ID, null, this);

        reloadingProgressBar = (ProgressBar) findViewById(R.id.pb_reloading);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadPage(NetworkUtils.LAST_REQUESTED_URL);

            }
        });

        loadPage(NetworkUtils.LAST_REQUESTED_URL);

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

        showProgressBar();

        movieListAdapter.setMovieInfoList(null);

        switch (requestingUrlString) {
            case NetworkUtils.TOP_RATED_MOVIE_URL:
                NetworkUtils.LAST_REQUESTED_URL = NetworkUtils.TOP_RATED_MOVIE_URL;
                break;

            case NetworkUtils.POPULAR_MOVIE_URL:
                NetworkUtils.LAST_REQUESTED_URL = NetworkUtils.POPULAR_MOVIE_URL;
                break;

            default:
                break;

        }

        Bundle bundle = new Bundle();
        bundle.putString(TARGET_URL_BUNDLE_KEY, NetworkUtils.LAST_REQUESTED_URL);

        LoaderManager loaderManager= getSupportLoaderManager();
        Loader loader = loaderManager.getLoader(QUERY_REQUESTING_LOADER_ID);

        if(loader == null) {
            loaderManager.initLoader(QUERY_REQUESTING_LOADER_ID, bundle, this);
        }
        else {
            loaderManager.restartLoader(QUERY_REQUESTING_LOADER_ID, bundle, this);
        }

    }

    @Override
    public Loader<MovieInfo[]> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<MovieInfo[]>(this) {

            MovieInfo[] movieInfoList;

            @Override
            protected void onStartLoading() {

                if(movieInfoList != null) {

                    deliverResult(movieInfoList);

                }

                else {

                    movieListRecyclerView.setVisibility(View.INVISIBLE);
                    forceLoad();

                }

            }

            @Override
            public MovieInfo[] loadInBackground() {

                String targetUrl = args.getString(TARGET_URL_BUNDLE_KEY);
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
            public void deliverResult(MovieInfo[] data) {

                movieInfoList = data;

                super.deliverResult(data);

            }

        };

    }

    @Override
    public void onLoadFinished(Loader<MovieInfo[]> loader, MovieInfo[] data) {

        if(data == null) {

            showErrorMessage();

        } else {

            movieListAdapter.setMovieInfoList(data);
            movieListRecyclerView.setAdapter(movieListAdapter);
            showLoadedPage();

        }

    }

    private void showErrorMessage() {

        movieListRecyclerView.setVisibility(View.INVISIBLE);
        reloadingProgressBar.setVisibility(View.INVISIBLE);
        errorPageLayout.setVisibility(View.VISIBLE);

    }

    private void showLoadedPage() {

        movieListRecyclerView.setVisibility(View.VISIBLE);
        reloadingProgressBar.setVisibility(View.INVISIBLE);
        errorPageLayout.setVisibility(View.INVISIBLE);

    }

    private void showProgressBar() {

        movieListRecyclerView.setVisibility(View.INVISIBLE);
        errorPageLayout.setVisibility(View.INVISIBLE);
        reloadingProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<MovieInfo[]> loader) {
    }

}

// polishing & bug fix
// TODO: 리사이클러 뷰 드래그 빠르게 했을때 버벅거림 수정
// TODO: 메인 페이지 가로 모드에서 리스트 뷰 아이템들 사이에 흰색 공백 없애기
// TODO: 메인 페이지 가로 모드에서 로딩 했을때 런타임 에러 수정
