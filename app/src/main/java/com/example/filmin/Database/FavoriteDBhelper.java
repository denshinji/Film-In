package com.example.filmin.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.filmin.Data.Favorite.FavoriteMovieData;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.filmin.Database.FavoriteContract.Favorites.COLUMN_TITLE;
import static com.example.filmin.Database.FavoriteContract.Favorites.TABLE_NAME;

public class FavoriteDBhelper extends SQLiteOpenHelper {
    public static final String LOGTAG = "FAVORITE";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favoritedb";
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static FavoriteDBhelper INSTANCE;
    SQLiteDatabase db;


    public FavoriteDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    public static FavoriteDBhelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteDBhelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Close");
        this.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.Favorites.TABLE_NAME + " (" +
                FavoriteContract.Favorites._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                FavoriteContract.Favorites.COLUMN_TITLE + " TEXT," +
                FavoriteContract.Favorites.COLUMN_POSTER_PATH + " TEXT," +
                FavoriteContract.Favorites.COLUMN_PLOT_SYNOPSIS + " TEXT," +
                FavoriteContract.Favorites.COLUMN_VOTE_COUNT + " TEXT," +
                FavoriteContract.Favorites.COLUMN_RELEASE_DATE + " TEXT," +
                FavoriteContract.Favorites.COLUMN_POPULARITY + " TEXT," +
                FavoriteContract.Favorites.COLUMN_LANGUAGE + " TEXT," +
                FavoriteContract.Favorites.COLUMN_BACKDROP + " TEXT," +
                FavoriteContract.Favorites.COLUMN_RATE + " TEXT" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.Favorites.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addFavorite(FavoriteMovieData favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteContract.Favorites.COLUMN_TITLE, favorite.getTitle());
        values.put(FavoriteContract.Favorites.COLUMN_POSTER_PATH, favorite.getImageurl());
        values.put(FavoriteContract.Favorites.COLUMN_PLOT_SYNOPSIS, favorite.getDesc());
        values.put(FavoriteContract.Favorites.COLUMN_VOTE_COUNT, favorite.getVote());
        values.put(FavoriteContract.Favorites.COLUMN_RELEASE_DATE, favorite.getDaterelease());
        values.put(FavoriteContract.Favorites.COLUMN_POPULARITY, favorite.getPopularity());
        values.put(FavoriteContract.Favorites.COLUMN_LANGUAGE, favorite.getLanguange());
        values.put(FavoriteContract.Favorites.COLUMN_BACKDROP, favorite.getBackdrop());
        values.put(FavoriteContract.Favorites.COLUMN_RATE, favorite.getRate());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_TITLE + "= '" + title + "'");
        db.close();

    }


    public List<FavoriteMovieData> getAllFavorite() {
        String[] columns = {
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
        String sortOrder =
                FavoriteContract.Favorites._ID + " ASC";
        List<FavoriteMovieData> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.Favorites.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                FavoriteMovieData favorite = new FavoriteMovieData();
                favorite.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorites.COLUMN_TITLE)));
                favorite.setImageurl(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorites.COLUMN_POSTER_PATH)));
                favorite.setDesc(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorites.COLUMN_PLOT_SYNOPSIS)));
                favorite.setLanguange(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorites.COLUMN_LANGUAGE)));
                favorite.setVote(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorites.COLUMN_VOTE_COUNT)));
                favorite.setBackdrop(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorites.COLUMN_BACKDROP)));
                favorite.setPopularity(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorites.COLUMN_POPULARITY)));
                favorite.setDaterelease(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorites.COLUMN_RELEASE_DATE)));
                favorite.setRate(cursor.getString(cursor.getColumnIndex(FavoriteContract.Favorites.COLUMN_RATE)));

                favoriteList.add(favorite);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }


    public Cursor queryAll() {
        return db.query(DATABASE_TABLE
                , null
                , null
                , null,
                null,
                null
                , COLUMN_TITLE + " ASC");
    }

    public Cursor queryById(String id) {
        return db.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertprovider(ContentValues contentValues) {
        return db.insert(DATABASE_TABLE, null, contentValues);
    }

    public int deleteprovider(String title) {
        return db.delete(DATABASE_TABLE, COLUMN_TITLE + "= ?", new String[]{title});
    }
}
