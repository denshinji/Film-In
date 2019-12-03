package com.example.favmov;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.favmov.FavoriteContract.Favorites.COLUMN_BACKDROP;
import static com.example.favmov.FavoriteContract.Favorites.COLUMN_LANGUAGE;
import static com.example.favmov.FavoriteContract.Favorites.COLUMN_PLOT_SYNOPSIS;
import static com.example.favmov.FavoriteContract.Favorites.COLUMN_POPULARITY;
import static com.example.favmov.FavoriteContract.Favorites.COLUMN_POSTER_PATH;
import static com.example.favmov.FavoriteContract.Favorites.COLUMN_RATE;
import static com.example.favmov.FavoriteContract.Favorites.COLUMN_RELEASE_DATE;
import static com.example.favmov.FavoriteContract.Favorites.COLUMN_TITLE;
import static com.example.favmov.FavoriteContract.Favorites.COLUMN_VOTE_COUNT;
import static com.example.favmov.FavoriteContract.Favorites.CONTENT_URI;


public class MainActivity extends AppCompatActivity implements LoadMovieCallback {

    private FavoriteAdapter FavAdapter;
    private RecyclerView rvMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbars = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbars);
        TextView tittle = toolbars.findViewById(R.id.tittletoolbar);
        tittle.setText(R.string.app_name);

        rvMovie = findViewById(R.id.list_view_favorite);
        FavAdapter = new FavoriteAdapter(this);
        new getData(this, this).execute();
        recyclerMovie();
    }

    private void recyclerMovie() {
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(FavAdapter);
    }


    @Override
    public void postExecute(Cursor movie) {
        ArrayList<FavoriteData> listMovie = mapCursorToArrayList(movie);
        if (listMovie.size() > 0) {
            FavAdapter.setListFav(listMovie);
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            FavAdapter.setListFav(new ArrayList<FavoriteData>());
        }
    }

    private ArrayList<FavoriteData> mapCursorToArrayList(Cursor movie) {
        ArrayList<FavoriteData> lists = new ArrayList<>();
        while (movie.moveToNext()) {
            String poster_path = movie.getString(movie.getColumnIndexOrThrow(COLUMN_POSTER_PATH));
            String title = movie.getString(movie.getColumnIndexOrThrow(COLUMN_TITLE));
            String backdrop = movie.getString(movie.getColumnIndexOrThrow(COLUMN_BACKDROP));
            String vote = movie.getString(movie.getColumnIndexOrThrow(COLUMN_VOTE_COUNT));
            String popularity = movie.getString(movie.getColumnIndexOrThrow(COLUMN_POPULARITY));
            String desc = movie.getString(movie.getColumnIndexOrThrow(COLUMN_PLOT_SYNOPSIS));
            String release = movie.getString(movie.getColumnIndexOrThrow(COLUMN_RELEASE_DATE));
            String language = movie.getString(movie.getColumnIndexOrThrow(COLUMN_LANGUAGE));
            String rate = movie.getString(movie.getColumnIndexOrThrow(COLUMN_RATE));
            lists.add(new FavoriteData(desc, title, poster_path, release, vote, popularity, backdrop, language,rate));
        }
        return lists;
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private getData(Context context, LoadMovieCallback weakCallback) {
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(weakCallback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

}