package com.udacity.ak.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String PARCEL_DATA = "parcel_data";
    private static final String MOVIE_POSTER_BASE_URL =
            "http://image.tmdb.org/t/p/w185";

    @BindView(R.id.poster_iv) ImageView mPosterImageView;
    @BindView(R.id.original_title_tv) TextView mTitleText;
    @BindView(R.id.synopsis_tv) TextView mSynopsisText;
    @BindView(R.id.rating_tv) TextView mRatingText;
    @BindView(R.id.release_date_tv) TextView mReleaseDateText;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getParcelableExtra(PARCEL_DATA);
        populateUI();
    }

    private void populateUI() {
        Picasso.with(this).load(MOVIE_POSTER_BASE_URL+movie.getPosterImagePath()).into(mPosterImageView);

        mTitleText.setText(replaceEmptyValue(movie.getOriginalTitle()));
        mSynopsisText.setText(replaceEmptyValue(movie.getSynopsis()));
        mRatingText.setText(replaceEmptyValue(movie.getUserRating()));
        mReleaseDateText.setText(replaceEmptyValue(movie.getReleaseDate()));
    }

    private String replaceEmptyValue(String value)
    {
        if(value==null || value.trim().equals(""))
            return getResources().getString(R.string.blank_movie_detail);

        return value;
    }
}
