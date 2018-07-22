package com.udacity.ak.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    Long id;
    String posterImagePath;
    String originalTitle;
    String userRating;
    String synopsis;
    String releaseDate;

    //Constructor - read variables from Parcel
    public Movie(Parcel in) {
        id = in.readLong();
        posterImagePath = in.readString();
        originalTitle = in.readString();
        userRating = in.readString();
        synopsis = in.readString();
        releaseDate = in.readString();
    }

    public Movie(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(posterImagePath);
        dest.writeString(originalTitle);
        dest.writeString(userRating);
        dest.writeString(synopsis);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
