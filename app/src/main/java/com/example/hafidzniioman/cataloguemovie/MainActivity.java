package com.example.hafidzniioman.cataloguemovie;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    ListView listView;
    MovieAdapter adapter;
    EditText edtMovieName;
    Button btnSearch;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.lv_movie);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItems movieItems = (MovieItems) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, MovieDetail.class);

                intent.putExtra(MovieDetail.EXTRA_TITLE, movieItems.getMovieName());
                intent.putExtra(MovieDetail.EXTRA_DESC, movieItems.getMovieDescription());
                intent.putExtra(MovieDetail.EXTRA_POSTER, movieItems.getmoviePoster());
                intent.putExtra(MovieDetail.EXTRA_DATE, movieItems.getMovieDate());

                startActivity(intent);
            }
        });

        edtMovieName = (EditText) findViewById(R.id.edt_search_movie);
        btnSearch = (Button) findViewById(R.id.btn_search_movie);
        btnSearch.setOnClickListener(movieListener);

        String movie = edtMovieName.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, movie);

        getSupportLoaderManager().initLoader(0, bundle, this);
    }
//    run MovieLoader

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String movieCollection = "";
        if (args != null) {
            movieCollection = args.getString(EXTRAS_MOVIE);
        }
        return new MovieLoader(this, movieCollection);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener movieListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String movie = edtMovieName.getText().toString();
            if (TextUtils.isEmpty(movie))
                return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, movie);
            getSupportLoaderManager().restartLoader(0, bundle, MainActivity.this);
        }
    };
}
