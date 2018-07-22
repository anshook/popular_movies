package com.udacity.ak.popularmovies.utilities;

import com.udacity.ak.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to handle TheMovieDb JSON data.
 */

public final class MovieJsonUtils {

    private static String N_RESULTS = "results";
    private static String N_ID = "id";
    private static String N_TITLE = "original_title";
    private static String N_VOTE_AVG = "vote_average";
    private static String N_POSTER_PATH = "poster_path";
    private static String N_OVERVIEW = "overview";
    private static String N_RELEASE_DT = "release_date";

    /**
     * Parse the JSON and convert it into Movie objects.
     *
     * @param moviesJsonStr The JSON to parse into Movie ArrayList.
     *
     * @return A List of Movie objects parsed from the JSON.
     */
    public static List<Movie> getMovieListFromJson(String moviesJsonStr) throws JSONException{
        DecimalFormat df = new DecimalFormat("0.0#");
        if (moviesJsonStr != null) {
            List<Movie> movieList = new ArrayList<Movie>();
            JSONObject jsonObj = new JSONObject(moviesJsonStr);

            // Getting JSON Array node
            JSONArray movies = jsonObj.getJSONArray(N_RESULTS);

            // looping through All Movies
            for (int i = 0; i < movies.length(); i++) {
                JSONObject m = movies.getJSONObject(i);
                Movie movieObj = new Movie(m.getLong(N_ID));
                movieObj.setUserRating(df.format(m.getDouble(N_VOTE_AVG)));
                movieObj.setPosterImagePath(m.getString(N_POSTER_PATH));
                movieObj.setOriginalTitle(m.getString(N_TITLE));
                movieObj.setSynopsis(m.getString(N_OVERVIEW));
                movieObj.setReleaseDate(m.getString(N_RELEASE_DT));

                movieList.add(movieObj);;
            }
            return movieList;
        }
        return null;
    }
}
