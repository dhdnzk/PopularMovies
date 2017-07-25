package com.example.android.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.AsyncTaskClasses.MovieInfoRequestingTask;
import com.example.android.popularmovies.MovieListAdapter;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.RecyclerViewClickListener {

    private RecyclerView movieListRecyclerView;
    private MovieListAdapter movieListAdapter;

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

    private void loadPage(String requestionType) {

        movieListAdapter.setMovieInfoList(null);

        switch(requestionType) {

            case NetworkUtils.TOP_RATED_MOVIE_URL:
                new MovieInfoRequestingTask(movieListRecyclerView, movieListAdapter).execute(NetworkUtils.TOP_RATED_MOVIE_URL);
                break;

            case NetworkUtils.POPULAR_MOVIE_URL:
            default:
                new MovieInfoRequestingTask(movieListRecyclerView, movieListAdapter).execute(NetworkUtils.POPULAR_MOVIE_URL);
                break;

        }

    }

}

// polishing & bug fix
// TODO (1) 오프라인 상태일때 처리
// TODO (2) 그리드 레이아웃 4개씩 나오도록
// TODO (3) 그리드 레이아웃 그림 간 마진 없애기
// TODO (4) 위로 당기면 새로고침
// TODO (5) 고무줄 효과 적용해서 그림이 자동으로 페이지 중앙으로 오도록
// TODO (6) 메인 페이지 가로로 했을때 오른쪽으로 4개 나열되도록 레이아웃 수정
// TODO (7) 영화 디테일 페이지 가로로 했을때 영화 오버뷰 오른쪽으로 빠지도록 레이아웃 수정
// TODO (8) 리사이클러 뷰 중간에서 정렬 순서 바꿔서 로딩했을때 발생하는 런타임 에러 수정
