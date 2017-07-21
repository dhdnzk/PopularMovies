package com.example.android.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.popularmovies.R;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        textView = (TextView) findViewById(R.id.tv_movie_detail);

        textView.setText(getIntent().getStringExtra("movieInfo"));

    }
}
