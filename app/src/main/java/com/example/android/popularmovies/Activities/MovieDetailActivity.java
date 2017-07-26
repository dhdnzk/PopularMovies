package com.example.android.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.MovieInfo;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView titleTextview;
    private TextView releaseDayTextview;
    private TextView ratingTextview;
    private TextView overViewTextview;
    private ImageView posterImageView;
    private Button bookmarkCheckingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        viewIdMapping();
        loadPage();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.it_movie_detail_back:

                finish();
        }

        return super.onOptionsItemSelected(item);

    }

    private void loadPage() {

        MovieInfo movieInfo = getIntent().getParcelableExtra("movieInfo");

        titleTextview.setText(movieInfo.getTitle());
        releaseDayTextview.setText(movieInfo.getReleaseDate());
        // TODO: set running time
        ratingTextview.setText(String.valueOf(movieInfo.getVoteAverage()) + " / 10");
        overViewTextview.setText(movieInfo.getOverview());
        // TODO: set poster image
        bookmarkCheckingButton.setText("Mark as favorite");

        String posterImageUrl = NetworkUtils.POSTER_BASIC_URL +
                NetworkUtils.POSTER_SIZE +
                movieInfo.getPosterPath();

        Picasso.with(this).load(posterImageUrl).into(posterImageView);

    }

    private void viewIdMapping() {

        titleTextview = (TextView) findViewById(R.id.tv_movie_title);
        releaseDayTextview = (TextView) findViewById(R.id.tv_release_day);
        ratingTextview = (TextView) findViewById(R.id.tv_rating);
        overViewTextview = (TextView) findViewById(R.id.tv_overview);
        posterImageView = (ImageView) findViewById(R.id.iv_posterImage);
        bookmarkCheckingButton = (Button) findViewById(R.id.bt_mark_as_favorite);

    }

}
