package com.example.filmin.Data.FILM;

import android.os.Parcel;
import android.os.Parcelable;

public class FilmPopular implements Parcelable {

    public static final Creator<FilmPopular> CREATOR = new Creator<FilmPopular>() {
        @Override
        public FilmPopular createFromParcel(Parcel in) {
            return new FilmPopular(in);
        }

        @Override
        public FilmPopular[] newArray(int size) {
            return new FilmPopular[size];
        }
    };
    private String vote;
    private String rate;
    private String popularity;
    private String languange;
    private String backdrop;
    private String title;
    private String desc;
    private String daterelease;
    private String imageurl;
    public FilmPopular(String desc, String title, String imageurl, String daterelease, String backdrop, String languange, String vote, String popularity, String rate) {
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
    protected FilmPopular(Parcel in) {
        title = in.readString();
        desc = in.readString();
        imageurl = in.readString();
        daterelease = in.readString();
        backdrop = in.readString();
        languange = in.readString();
        popularity = in.readString();
        vote = in.readString();
        rate = in.readString();

    }

    public FilmPopular() {
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(imageurl);
        dest.writeString(daterelease);
        dest.writeString(backdrop);
        dest.writeString(languange);
        dest.writeString(popularity);
        dest.writeString(vote);
        dest.writeString(rate);


    }
}

