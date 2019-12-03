package com.example.filmin.Data.Favorite;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteTvData implements Parcelable {

    public static final Creator<FavoriteTvData> CREATOR = new Creator<FavoriteTvData>() {
        @Override
        public FavoriteTvData createFromParcel(Parcel in) {
            return new FavoriteTvData(in);
        }

        @Override
        public FavoriteTvData[] newArray(int size) {
            return new FavoriteTvData[size];
        }
    };
    private String vote;
    private String popularity;
    private String languange;
    private String rate;
    private String backdrop;
    private String title;
    private String desc;
    private String daterelease;
    private String imageurl;
    public FavoriteTvData() {
    }
    public FavoriteTvData(String desc, String title, String imageurl, String daterelease, String backdrop, String languange, String vote, String popularity, String rate) {
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

    protected FavoriteTvData(Parcel in) {
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