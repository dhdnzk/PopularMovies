package com.example.android.popularmovies.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.MovieInfo;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_movie_title)
    TextView titleTextview;
    @BindView(R.id.tv_release_day)
    TextView releaseDayTextview;
    @BindView(R.id.tv_rating)
    TextView ratingTextview;
    @BindView(R.id.tv_overview)
    TextView overViewTextview;
    @BindView(R.id.iv_posterImage)
    ImageView posterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        }

        loadPage();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.home:

                NavUtils.navigateUpFromSameTask(this);

                break;

        }

        return super.onOptionsItemSelected(item);

    }

    private void loadPage() {

        MovieInfo movieInfo = getIntent().getParcelableExtra("movieInfo");

        if (movieInfo == null) {

            Log.d("getExtra error", "movie info is null");

            finish();

            return;

        }

        titleTextview.setText(movieInfo.getTitle());

        releaseDayTextview.setText(movieInfo.getReleaseDate());

        ratingTextview.setText(String.valueOf(movieInfo.getVoteAverage()) + " / 10");

        overViewTextview.setText(movieInfo.getOverview());

        String posterImageUrl = NetworkUtils.POSTER_BASIC_URL +
                NetworkUtils.POSTER_SIZE +
                movieInfo.getPosterPath();

        Picasso.with(this).load(posterImageUrl).into(posterImageView);

    }

}
