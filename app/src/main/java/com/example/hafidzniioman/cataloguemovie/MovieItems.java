package com.example.hafidzniioman.cataloguemovie;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieItems implements Serializable {
    private int id;
    private String movieName;
    private String movieDescription;
    private String moviePoster;
    private String movieDate;

    private final static String POSTER_URL = "http://image.tmdb.org/t/p/w185";

    public MovieItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String moviePoster = object.getString("poster_path");
            String movieName = object.getString("title");
            String movieDescription = object.getString("overview");
            String movieDate = object.getString("release_date");

            this.id = id;
            this.moviePoster = moviePoster;
            this.movieName = movieName;
            this.movieDescription = movieDescription;
            this.movieDate = movieDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getmoviePoster() {
        return moviePoster;
    }

    public void setmoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

}
