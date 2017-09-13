package com.dongttang.popularmovies.activities;

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

import com.dongttang.popularmovies.datas.MovieInfo;
import com.dongttang.popularmovies.datas.MovieListAdapter;
import com.dongttang.popularmovies.R;
import com.dongttang.popularmovies.utilities.MovieDBJsonUtils;
import com.dongttang.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements MovieListAdapter.RecyclerViewClickListener, LoaderManager.LoaderCallbacks<MovieInfo[]> {

    private static final String TARGET_URL_BUNDLE_KEY = "targetUrl";
    private static final int QUERY_REQUESTING_LOADER_ID = 1001;
    @BindView(R.id.rv_movie_list)
    RecyclerView movieListRecyclerView;
    @BindView(R.id.cl_error_page)
    ConstraintLayout errorPageLayout;
    @BindView(R.id.pb_reloading)
    ProgressBar reloadingProgressBar;
    @BindView(R.id.iv_refresh)
    ImageView refreshButton;
    private MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        } else {

            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        }

        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        movieListAdapter = new MovieListAdapter(this, this, metrics);

        movieListRecyclerView.setLayoutManager(layoutManager);

        movieListRecyclerView.setHasFixedSize(true);

        movieListRecyclerView.setItemViewCacheSize(20);

        getSupportLoaderManager().initLoader(QUERY_REQUESTING_LOADER_ID, null, this);

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

        LoaderManager loaderManager = getSupportLoaderManager();

        Loader loader = loaderManager.getLoader(QUERY_REQUESTING_LOADER_ID);

        if (loader == null) {

            loaderManager.initLoader(QUERY_REQUESTING_LOADER_ID, bundle, this);

        } else {

            loaderManager.restartLoader(QUERY_REQUESTING_LOADER_ID, bundle, this);

        }

    }

    @Override
    public Loader<MovieInfo[]> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<MovieInfo[]>(this) {

            MovieInfo[] movieInfoList;

            @Override
            protected void onStartLoading() {

                if (movieInfoList != null) {

                    deliverResult(movieInfoList);

                } else {

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

        if (data == null) {

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

/*
 * 이 단계에서는 1 단계에서 작성한 앱에 추가 기능을 추가합니다.
 * 동영상 세부 정보보기에 정보를 추가합니다.
 *      TODO : 사용자가 예고편을 (YouTube 앱 또는 웹 브라우저에서)보고 시청할 수 있습니다.
 *      TODO : 사용자는 선택한 영화에 대한 리뷰를 읽을 수 있습니다.
 *      TODO : 또한 사용자는 단추 (별표)를 눌러 세부 정보보기에서 동영상을 즐겨 찾기로 표시 할 수 있습니다. 이것은  유지 관리되며 API 요청 *이 필요하지 않은 지역 영화 컬렉션을위한 것입니다.
 *      TODO : 기본보기의 기존 정렬 기준을 수정하여 즐겨 찾기 컬렉션을 표시하는 추가 피벗을 포함시킵니다.
 *
 */

