package com.example.filmin.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.filmin.Data.Favorite.FavoriteTvData;

import java.util.ArrayList;
import java.util.List;

import static com.example.filmin.Database.FavoriteTvContract.Favorites.COLUMN_TITLE;
import static com.example.filmin.Database.FavoriteTvContract.Favorites.TABLE_NAME;

public class FavoriteDBhelperTv extends SQLiteOpenHelper {

    public static final String LOGTAG = "FAVORITETV";
    private static final String DATABASE_NAME = "favoritetvdb";
    private static final int DATABASE_VERSION = 1;
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteDBhelperTv(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        Log.i(LOGTAG, "Database Open");
        db = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Close");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TV_TABLE = "CREATE TABLE " + FavoriteTvContract.Favorites.TABLE_NAME + " (" +
                FavoriteTvContract.Favorites._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                FavoriteTvContract.Favorites.COLUMN_TITLE + " TEXT," +
                FavoriteTvContract.Favorites.COLUMN_POSTER_PATH + " TEXT," +
                FavoriteTvContract.Favorites.COLUMN_PLOT_SYNOPSIS + " TEXT," +
                FavoriteTvContract.Favorites.COLUMN_VOTE_COUNT + " TEXT," +
                FavoriteTvContract.Favorites.COLUMN_RELEASE_DATE + " TEXT," +
                FavoriteTvContract.Favorites.COLUMN_POPULARITY + " TEXT," +
                FavoriteTvContract.Favorites.COLUMN_LANGUAGE + " TEXT," +
                FavoriteTvContract.Favorites.COLUMN_BACKDROP + " TEXT," +
                FavoriteContract.Favorites.COLUMN_RATE + " TEXT" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteTvContract.Favorites.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addFavorite(FavoriteTvData favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteTvContract.Favorites.COLUMN_TITLE, favorite.getTitle());
        values.put(FavoriteTvContract.Favorites.COLUMN_POSTER_PATH, favorite.getImageurl());
        values.put(FavoriteTvContract.Favorites.COLUMN_PLOT_SYNOPSIS, favorite.getDesc());
        values.put(FavoriteTvContract.Favorites.COLUMN_VOTE_COUNT, favorite.getVote());
        values.put(FavoriteTvContract.Favorites.COLUMN_RELEASE_DATE, favorite.getDaterelease());
        values.put(FavoriteTvContract.Favorites.COLUMN_POPULARITY, favorite.getPopularity());
        values.put(FavoriteTvContract.Favorites.COLUMN_LANGUAGE, favorite.getLanguange());
        values.put(FavoriteTvContract.Favorites.COLUMN_BACKDROP, favorite.getBackdrop());
        values.put(FavoriteContract.Favorites.COLUMN_RATE, favorite.getRate());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + "= '" + title + "'");
        db.close();

    }

    public List<FavoriteTvData> getAllFavorite() {
        String[] columns = {
                FavoriteTvContract.Favorites._ID,
                FavoriteTvContract.Favorites.COLUMN_TITLE,
                FavoriteTvContract.Favorites.COLUMN_PLOT_SYNOPSIS,
                FavoriteTvContract.Favorites.COLUMN_POSTER_PATH,
                FavoriteTvContract.Favorites.COLUMN_LANGUAGE,
                FavoriteTvContract.Favorites.COLUMN_VOTE_COUNT,
                FavoriteTvContract.Favorites.COLUMN_POPULARITY,
                FavoriteTvContract.Favorites.COLUMN_BACKDROP,
                FavoriteTvContract.Favorites.COLUMN_RELEASE_DATE,
                FavoriteContract.Favorites.COLUMN_RATE

        };
        String sortOrder =
                FavoriteTvContract.Favorites._ID + " ASC";
        List<FavoriteTvData> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteTvContract.Favorites.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                FavoriteTvData favorite = new FavoriteTvData();
                favorite.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteTvContract.Favorites.COLUMN_TITLE)));
                favorite.setImageurl(cursor.getString(cursor.getColumnIndex(FavoriteTvContract.Favorites.COLUMN_POSTER_PATH)));
                favorite.setDesc(cursor.getString(cursor.getColumnIndex(FavoriteTvContract.Favorites.COLUMN_PLOT_SYNOPSIS)));
                favorite.setLanguange(cursor.getString(cursor.getColumnIndex(FavoriteTvContract.Favorites.COLUMN_LANGUAGE)));
                favorite.setVote(cursor.getString(cursor.getColumnIndex(FavoriteTvContract.Favorites.COLUMN_VOTE_COUNT)));
                favorite.setBackdrop(cursor.getString(cursor.getColumnIndex(FavoriteTvContract.Favorites.COLUMN_BACKDROP)));
                favorite.setPopularity(cursor.getString(cursor.getColumnIndex(FavoriteTvContract.Favorites.COLUMN_POPULARITY)));
                favorite.setDaterelease(cursor.getString(cursor.getColumnIndex(FavoriteTvContract.Favorites.COLUMN_RELEASE_DATE)));
                favorite.setRate(cursor.getString(cursor.getColumnIndex(FavoriteTvContract.Favorites.COLUMN_RATE)));


                favoriteList.add(favorite);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }
}