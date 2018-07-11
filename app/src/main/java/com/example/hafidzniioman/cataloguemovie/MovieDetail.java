package com.example.hafidzniioman.cataloguemovie;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetail extends AppCompatActivity {
    private TextView movieTitle, movieReleaseDate, movieDesc;
    private ImageView image;

    public static String EXTRA_TITLE = "EXTRA_TITLE";
    public static String EXTRA_DESC = "EXTRA_DESC";
    public static String EXTRA_DATE = "EXTRA_DATE";
    public static String EXTRA_POSTER = "EXTRA_POSTER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        movieTitle = (TextView) findViewById(R.id.tv_name_movie);
        movieReleaseDate = (TextView) findViewById(R.id.tv_date_movie);
        movieDesc = (TextView) findViewById(R.id.tv_description_movie);
        image = (ImageView) findViewById(R.id.img_poster_movie);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String desc = getIntent().getStringExtra(EXTRA_DESC);
        String poster = getIntent().getStringExtra(EXTRA_POSTER);
        String release = getIntent().getStringExtra(EXTRA_DATE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(release);
            SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            String date_release = newDateFormat.format(date);
            movieReleaseDate.setText(date_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        movieTitle.setText(title);
        movieDesc.setText(desc);
        movieReleaseDate.setText(release);

        Glide
                .with(MovieDetail.this)
                .load("http://image.tmdb.org/t/p/w185/" + poster)
                .override(350, 350)
                .into(image);
    }
}
