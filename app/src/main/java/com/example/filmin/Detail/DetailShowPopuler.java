package com.example.filmin.Detail;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.filmin.Data.Favorite.FavoriteTvData;
import com.example.filmin.Data.TVSHOW.TVPopuler;
import com.example.filmin.Database.FavoriteDBhelperTv;
import com.example.filmin.Database.FavoriteTvContract;
import com.example.filmin.Fragment.TvPopular;
import com.example.filmin.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class DetailShowPopuler extends AppCompatActivity {

    public static String TV_POPULER_KEY = "TV_key";
    String desc;
    String judul;
    String rilis;
    String popularity;
    String languange;
    String vote;
    String poster;
    String backdrop;
    String rates;

    private FavoriteDBhelperTv favoriteDBhelpertv;
    private SQLiteDatabase mdb;
    private TvPopular tvPopular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.det_tool);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button fb = findViewById(R.id.btn_trailer);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trailer();
            }
        });
        TextView t_judul = findViewById(R.id.d_judul);
        TextView t_release = findViewById(R.id.d_d_release);
        TextView t_desc = findViewById(R.id.d_desc);
        TextView t_languange = findViewById(R.id.d_languange);
        TextView t_popularity = findViewById(R.id.d_popularity);
        TextView t_vote = findViewById(R.id.d_vote);
        TextView t_rates = findViewById(R.id.d_rates);

        ImageView imphoto = findViewById(R.id.d_photo);
        ImageView backdroph = findViewById(R.id.bc_grey);
        Intent intent = getIntent();
        TVPopuler tvShow = intent.getParcelableExtra(TV_POPULER_KEY);
        judul = tvShow.getTitle();
        t_judul.setText(judul);
        rates = tvShow.getRate();
        t_rates.setText(rates);
        poster = tvShow.getImageurl();
        backdrop = tvShow.getBackdrop();
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w342/" + tvShow.getImageurl())
                .into(imphoto);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w500/" + tvShow.getBackdrop())
                .into(backdroph);
        desc = tvShow.getDesc();
        t_desc.setText(desc);
        rilis = tvShow.getDaterelease();
        t_release.setText(rilis);
        languange = tvShow.getLanguange();
        t_languange.setText(languange);
        popularity = tvShow.getPopularity();
        t_popularity.setText(popularity);
        vote = tvShow.getVote();
        t_vote.setText(vote);
        setActionBarTitle(judul);
        FavoriteDBhelperTv favoritehelper = new FavoriteDBhelperTv(this);
        mdb = favoritehelper.getWritableDatabase();
        MaterialFavoriteButton favoriteButton = findViewById(R.id.favorite_button);
        if (Exists(judul)) {
            favoriteButton.setFavorite(true);
            favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite == true) {
                        saveFavorite();
                        Snackbar.make(buttonView, getApplicationContext().getString(R.string.succes_fav), Snackbar.LENGTH_SHORT).show();
                    } else {
                        favoriteDBhelpertv = new FavoriteDBhelperTv(getApplicationContext());
                        favoriteDBhelpertv.deleteFavorite(judul);
                        Snackbar.make(buttonView, getApplicationContext().getString(R.string.remove_succes), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite == true) {
                        saveFavorite();
                        Snackbar.make(buttonView, getApplicationContext().getString(R.string.succes_fav), Snackbar.LENGTH_SHORT).show();
                    } else {
                        favoriteDBhelpertv = new FavoriteDBhelperTv(getApplicationContext());
                        favoriteDBhelpertv.deleteFavorite(judul);
                        Snackbar.make(buttonView, getApplicationContext().getString(R.string.remove_succes), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void saveFavorite() {
        favoriteDBhelpertv = new FavoriteDBhelperTv(getApplicationContext());
        FavoriteTvData favorite = new FavoriteTvData();
        tvPopular = new TvPopular();
        favorite.setTitle(judul);
        favorite.setImageurl(poster);
        favorite.setDesc(desc);
        favorite.setVote(vote);
        favorite.setDaterelease(rilis);
        favorite.setPopularity(popularity);
        favorite.setLanguange(languange);
        favorite.setBackdrop(backdrop);
        favorite.setRate(rates);
        favoriteDBhelpertv.addFavorite(favorite);
    }

    public boolean Exists(String searchItem) {

        String[] projection = {
                FavoriteTvContract.Favorites._ID,
                FavoriteTvContract.Favorites.COLUMN_TITLE,
                FavoriteTvContract.Favorites.COLUMN_PLOT_SYNOPSIS,
                FavoriteTvContract.Favorites.COLUMN_POSTER_PATH,
                FavoriteTvContract.Favorites.COLUMN_LANGUAGE,
                FavoriteTvContract.Favorites.COLUMN_VOTE_COUNT,
                FavoriteTvContract.Favorites.COLUMN_POPULARITY,
                FavoriteTvContract.Favorites.COLUMN_BACKDROP,
                FavoriteTvContract.Favorites.COLUMN_RELEASE_DATE,
                FavoriteTvContract.Favorites.COLUMN_RATE

        };
        String selection = FavoriteTvContract.Favorites.COLUMN_TITLE + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";

        Cursor cursor = mdb.query(FavoriteTvContract.Favorites.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            setShare();
        }
        return super.onOptionsItemSelected(item);
    }

    private void trailer() {
        String title = "https://www.youtube.com/results?search_query=trailer" + judul;
        Intent open = new Intent(Intent.ACTION_VIEW);
        open.setData(Uri.parse(title));
        startActivity(Intent.createChooser(open, "Open Via"));
    }

    private void setShare() {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("text/plain");

        share.putExtra(Intent.EXTRA_TEXT, poster + "\n\nHai Film " + "\n" + judul + "\nFILM IN");
        startActivity(Intent.createChooser(share, "Share Via"));
    }
}