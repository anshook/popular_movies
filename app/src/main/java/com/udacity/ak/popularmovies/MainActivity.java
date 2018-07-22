package com.udacity.ak.popularmovies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

import com.udacity.ak.popularmovies.utilities.MovieJsonUtils;
import com.udacity.ak.popularmovies.utilities.NetworkUtils;
import com.udacity.ak.popularmovies.utilities.InternetCheck;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.gv_movies) GridView mMoviesGridView;
    @BindView(R.id.spinner_movies_sort) Spinner mSortOptionsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMoviesGridView = (GridView) findViewById(R.id.gv_movies);
        mSortOptionsSpinner = (Spinner) findViewById(R.id.spinner_movies_sort);
        mSortOptionsSpinner.setOnItemSelectedListener(this);
    }

    public void loadMoviesData(String sortBy) {
        new InternetCheck(internet -> {
            if(internet) {
                URL url = NetworkUtils.buildUrl(sortBy);
                new MoviesHttpRequest().execute(url);
            }
            else {
                buildDialog(MainActivity.this).show();
            }
        });
    }

    private AlertDialog.Builder buildDialog(Context ctx) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("No Internet Connection. Make sure Cellular Data or WiFi is turned on, then try again.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });
        return builder;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedSortOption = parent.getItemAtPosition(position).toString();
        String sortBy = "";
        if(selectedSortOption.equals(getString(R.string.sort_option_popular)))
        {
            sortBy = NetworkUtils.SORT_BY_POPULAR;
        }
        else if(selectedSortOption.equals(getString(R.string.sort_option_top_rated)))
        {
            sortBy = NetworkUtils.SORT_BY_TOP_RATED;
        }
        loadMoviesData(sortBy);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }


    // Class to perform network requests
    public class MoviesHttpRequest extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String httpResponse = null;
            try {
                httpResponse = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                Log.e(TAG, "Error reading from server:", e);
            }
            return httpResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "Json Response: " + s);
            if(s!=null && !s.isEmpty()) {
                List<Movie> movieList;
                try {
                    movieList = MovieJsonUtils.getMovieListFromJson(s);
                    MovieAdapter movieAdapter=new MovieAdapter(MainActivity.this, movieList);
                    mMoviesGridView.setAdapter(movieAdapter);

                    mMoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Movie selectedMovie = (Movie)parent.getItemAtPosition(position);
                            launchMovieDetailActivity(selectedMovie);
                        }
                    });

                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error", e);
                }
            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
            }
        }

        private void launchMovieDetailActivity(Movie selectedMovie) {
            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.PARCEL_DATA, selectedMovie);
            startActivity(intent);
        }
    }


}
