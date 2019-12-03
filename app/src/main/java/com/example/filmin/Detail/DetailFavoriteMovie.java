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
import com.example.filmin.Data.FILM.Film;
import com.example.filmin.Data.Favorite.FavoriteMovieData;
import com.example.filmin.Database.FavoriteContract;
import com.example.filmin.Database.FavoriteDBhelper;
import com.example.filmin.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;

public class DetailFavoriteMovie extends AppCompatActivity {
    public static String FAVORITE_MOVIE_KEY = "Favorite_movie_key";
    String desc;
    String judul;
    String rilis;
    String popularity;
    String languange;
    String vote;
    String poster;
    String backdrop;
    String rates;

    private String title = "Item Name";
    private AppCompatActivity activity = DetailFavoriteMovie.this;
    private FavoriteDBhelper favoriteDBhelper;
    private SQLiteDatabase mdb;
    private Film film;

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
        FavoriteMovieData film = intent.getParcelableExtra(FAVORITE_MOVIE_KEY);
        judul = film.getTitle();
        t_judul.setText(judul);
        rates = film.getRate();
        t_rates.setText(rates);
        desc = film.getDesc();
        t_desc.setText(desc);
        rilis = film.getDaterelease();
        t_release.setText(rilis);
        poster = film.getImageurl();
        backdrop = film.getBackdrop();
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w342/" + film.getImageurl())
                .into(imphoto);
        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w500/" + film.getBackdrop())
                .into(backdroph);
        languange = film.getLanguange();
        t_languange.setText(languange);
        popularity = film.getPopularity();
        t_popularity.setText(popularity);
        vote = film.getVote();
        t_vote.setText(vote);
        setActionBarTitle(judul);
        Button fb = findViewById(R.id.btn_trailer);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trailer();
            }
        });
        FavoriteDBhelper favoritehelper = new FavoriteDBhelper(this);
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
                        favoriteDBhelper = new FavoriteDBhelper(getApplicationContext());
                        favoriteDBhelper.deleteFavorite(judul);
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
                        favoriteDBhelper = new FavoriteDBhelper(getApplicationContext());
                        favoriteDBhelper.deleteFavorite(judul);
                        Snackbar.make(buttonView, getApplicationContext().getString(R.string.remove_succes), Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void saveFavorite() {
        favoriteDBhelper = new FavoriteDBhelper(activity);
        FavoriteMovieData favorite = new FavoriteMovieData();
        film = new Film();
        favorite.setTitle(judul);
        favorite.setImageurl(poster);
        favorite.setDesc(desc);
        favorite.setVote(vote);
        favorite.setDaterelease(rilis);
        favorite.setPopularity(popularity);
        favorite.setLanguange(languange);
        favorite.setBackdrop(backdrop);
        favorite.setRate(rates);
        favoriteDBhelper.addFavorite(favorite);
    }

    public boolean Exists(String searchItem) {

        String[] projection = {
                FavoriteContract.Favorites._ID,
                FavoriteContract.Favorites.COLUMN_TITLE,
                FavoriteContract.Favorites.COLUMN_PLOT_SYNOPSIS,
                FavoriteContract.Favorites.COLUMN_POSTER_PATH,
                FavoriteContract.Favorites.COLUMN_LANGUAGE,
                FavoriteContract.Favorites.COLUMN_VOTE_COUNT,
                FavoriteContract.Favorites.COLUMN_POPULARITY,
                FavoriteContract.Favorites.COLUMN_BACKDROP,
                FavoriteContract.Favorites.COLUMN_RELEASE_DATE,
                FavoriteContract.Favorites.COLUMN_RATE

        };
        String selection = FavoriteContract.Favorites.COLUMN_TITLE + " =?";
        String[] selectionArgs = {searchItem};
        String limit = "1";

        Cursor cursor = mdb.query(FavoriteContract.Favorites.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
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
