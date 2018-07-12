package com.example.hafidzniioman.cataloguemovie;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    Context context;
    ListView listView;
    MovieAdapter adapter;
    EditText edtMovieName;
    Button btnSearch;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

//    https://api.themoviedb.org/3/movie/now_playing?api_key=<APIKEY ANDA>&language=en-US
//private static final String API_KEY = "5243f886f5b6702c4b3483e3b188c340";


    public NowPlayingFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieItems movieItems = (MovieItems) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), MovieDetail.class);

                intent.putExtra(MovieDetail.EXTRA_TITLE, movieItems.getMovieName());
                intent.putExtra(MovieDetail.EXTRA_DESC, movieItems.getMovieDescription());
                intent.putExtra(MovieDetail.EXTRA_POSTER, movieItems.getmoviePoster());
                intent.putExtra(MovieDetail.EXTRA_DATE, movieItems.getMovieDate());

                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(movieListener);

        String movie = edtMovieName.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, movie);

        getActivity().getSupportLoaderManager().initLoader(0, bundle, this);
    }

//    private static final String API_KEY = "5243f886f5b6702c4b3483e3b188c340";
//
//    @Nullable
//    @Override
//    private String movieCollection;
//    public ArrayList<MovieItems> loadInBackground() {
//        SyncHttpClient client = new SyncHttpClient();
//
//        final ArrayList<MovieItems> movieItemses = new ArrayList<>();
//        String url = "https://api.themoviedb.org/3/search/movie?api_key=" +
//                API_KEY + "&language=en-US&query=" + movieCollection;
//
//
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                setUseSynchronousMode(true);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, byte[] responseBody) {
//                try {
//                    String result = new String(responseBody);
//                    JSONObject responseObject = new JSONObject(result);
//                    JSONArray results = responseObject.getJSONArray("results");
//
//                    for (int i = 0; i < results.length(); i++) {
//                        JSONObject movie = results.getJSONObject(i);
//                        MovieItems movieItems = new MovieItems(movie);
//                        Log.d("LIST", "on success :" + movieItems.getmoviePoster());
//                        Log.d("LIST", "on success :" + movieItems.getMovieDescription());
//                        movieItemses.add(movieItems);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, byte[] responseBody, Throwable error) {
//
//            }
//        });
//        return movieItemses;
//    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String movieCollection = "";
        if (args != null) {
            movieCollection = args.getString(EXTRAS_MOVIE);
        }
        return new MovieLoader(context, movieCollection);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {

        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<MovieItems>> loader) {

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
            getActivity().getSupportLoaderManager().restartLoader(0, bundle, NowPlayingFragment.this);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.now_playing_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.lv_movie);
        edtMovieName = (EditText) view.findViewById(R.id.edt_search_movie);
        btnSearch = (Button) view.findViewById(R.id.btn_search_movie);

        return view;
    }


}
