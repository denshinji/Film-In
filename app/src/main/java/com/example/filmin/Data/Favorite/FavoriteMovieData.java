package com.example.filmin.Data.Favorite;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.filmin.Database.FavoriteContract.Favorites.COLUMN_BACKDROP;
import static com.example.filmin.Database.FavoriteContract.Favorites.COLUMN_LANGUAGE;
import static com.example.filmin.Database.FavoriteContract.Favorites.COLUMN_PLOT_SYNOPSIS;
import static com.example.filmin.Database.FavoriteContract.Favorites.COLUMN_POPULARITY;
import static com.example.filmin.Database.FavoriteContract.Favorites.COLUMN_POSTER_PATH;
import static com.example.filmin.Database.FavoriteContract.Favorites.COLUMN_RATE;
import static com.example.filmin.Database.FavoriteContract.Favorites.COLUMN_VOTE_COUNT;
import static com.example.filmin.Database.FavoriteTvContract.Favorites.COLUMN_RELEASE_DATE;

public class FavoriteMovieData implements Parcelable {
    public static final Creator<FavoriteMovieData> CREATOR = new Creator<FavoriteMovieData>() {
        @Override
        public FavoriteMovieData createFromParcel(Parcel in) {
            return new FavoriteMovieData(in);
        }

        @Override
        public FavoriteMovieData[] newArray(int size) {
            return new FavoriteMovieData[size];
        }
    };
    private static final String COLUMN_TITLE = "title";
    private String vote;
    private String popularity;
    private String languange;
    private String backdrop;
    private String title;
    private String desc;
    private String rate;
    private String daterelease;
    private String imageurl;
    public FavoriteMovieData() {
    }
    public FavoriteMovieData(String desc, String title, String imageurl, String daterelease, String backdrop, String languange, String vote, String popularity, String rate) {
        this.desc = desc;
        this.title = title;
        this.daterelease = daterelease;
        this.imageurl = imageurl;
        this.backdrop = backdrop;
        this.languange = languange;
        this.popularity = popularity;
        this.vote = vote;
        this.rate = rate;
    }

    public FavoriteMovieData(Parcel in) {
        vote = in.readString();
        popularity = in.readString();
        languange = in.readString();
        backdrop = in.readString();
        title = in.readString();
        desc = in.readString();
        daterelease = in.readString();
        imageurl = in.readString();
        rate = in.readString();
    }

    public FavoriteMovieData(Cursor cursor) {
        this.title = getColumnString(cursor, COLUMN_TITLE);
        this.imageurl = getColumnString(cursor, COLUMN_POSTER_PATH);
        this.desc = getColumnString(cursor, COLUMN_PLOT_SYNOPSIS);
        this.daterelease = getColumnString(cursor, COLUMN_RELEASE_DATE);
        this.languange = getColumnString(cursor, COLUMN_LANGUAGE);
        this.vote = getColumnString(cursor, COLUMN_VOTE_COUNT);
        this.backdrop = getColumnString(cursor, COLUMN_BACKDROP);
        this.popularity = getColumnString(cursor, COLUMN_POPULARITY);
        this.rate = getColumnString(cursor, COLUMN_RATE);

    }

    private static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    private static String getColumnLong(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(FavoriteMovieData.COLUMN_TITLE));
    }

    public static FavoriteMovieData fromContentValues(ContentValues values) {
        final FavoriteMovieData movieFavorite = new FavoriteMovieData();
        if (values.containsKey(COLUMN_TITLE)) {
            movieFavorite.title = values.getAsString(COLUMN_TITLE);
        }

        return movieFavorite;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getLanguange() {
        return languange;
    }

    public void setLanguange(String languange) {
        this.languange = languange;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDaterelease() {
        return daterelease;
    }

    public void setDaterelease(String daterelease) {
        this.daterelease = daterelease;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vote);
        dest.writeString(popularity);
        dest.writeString(languange);
        dest.writeString(backdrop);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(daterelease);
        dest.writeString(imageurl);
        dest.writeString(rate);

    }
}
