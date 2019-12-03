package com.example.filmin.Data.FILM;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
    private String rate;
    private Integer id;
    private String vote;
    private String popularity;
    private String languange;
    private String imagecrew;
    private String backdrop;
    private String title;
    private String desc;
    private String crewname;
    private String nameactor;
    private String daterelease;
    private String imageurl;

    public Film() {

    }

    public Film(String desc, String title, String imageurl, String daterelease, String backdrop, String languange, String vote, String popularity, Integer id, String rate) {
        this.desc = desc;
        this.title = title;
        this.daterelease = daterelease;
        this.imageurl = imageurl;
        this.backdrop = backdrop;
        this.languange = languange;
        this.popularity = popularity;
        this.vote = vote;
        this.id = id;
        this.rate = rate;

    }


    public Film(Parcel in) {
        title = in.readString();
        desc = in.readString();
        imageurl = in.readString();
        daterelease = in.readString();
        backdrop = in.readString();
        languange = in.readString();
        popularity = in.readString();
        vote = in.readString();
        id = in.readInt();
        rate = in.readString();

    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
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
        dest.writeInt(id);
        dest.writeString(rate);


    }
}
