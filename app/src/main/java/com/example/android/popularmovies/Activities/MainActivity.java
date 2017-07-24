package com.example.android.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);

        movieListAdapter = new MovieListAdapter(this);

        movieListRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        movieListRecyclerView.setLayoutManager(layoutManager);
        movieListRecyclerView.setHasFixedSize(true);

        loadPage();

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
                // TODO: 평점에 따른 정렬
                break;

            case R.id.it_main_set_as_popularity:
                // TODO: 인기순에 따른 정렬
                break;

            default:

                break;

        }

        return true;
    }

    private void loadPage() {

        movieListAdapter.setMovieInfoList(null);
        new MovieInfoRequestingTask(movieListRecyclerView, movieListAdapter).execute(NetworkUtils.POPULAR_MOVIE_URL);

    }

}

// MainActivity
// TODO (1) 메뉴바 달고 정렬 순서 고르는 버튼 추가 + 구현

// MovieDetailActivity
// TODO (1) 레이아웃 꾸미기
// TODO (2) 가져온 가져온 데이터 배치

// polishing
// TODO (1) 오프라인 상태일때 처리
// TODO (2) 그리드 레이아웃 4개씩 나오도록
// TODO (3) 그리드 레이아웃 그림 간 마진 없애기
// TODO (4) 위로 당기면 새로고침
// TODO (5) 고무줄 효과 적용해서 그림이 자동으로 페이지 중앙으로 오도록
// TODO (6) 메인 페이지 가로로 했을때 오른쪽으로 4개 나열되도록 레이아웃 수정
// TODO (7) 영화 디테일 페이지 가로로 했을때 영화 오버뷰 오른쪽으로 빠지도록 레이아웃 수정
