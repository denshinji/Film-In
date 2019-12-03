package com.example.filmin.Widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.filmin.Data.Favorite.FavoriteMovieData;
import com.example.filmin.R;

import java.util.concurrent.ExecutionException;

import static com.example.filmin.Database.FavoriteContract.Favorites.CONTENT_URI;
import static com.example.filmin.Widget.FavWidget.EXTRA_ITEM;

public class StackRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;

    StackRemoteFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long idtoken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(CONTENT_URI,
                null,
                null,
                null,
                null
        );
        Binder.restoreCallingIdentity(idtoken);
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        FavoriteMovieData favoriteMovie = getMovie(position);
        try {
            String poster = "https://image.tmdb.org/t/p/w500" + favoriteMovie.getImageurl();
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(poster)
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.imageView, bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ITEM, position);
        Intent intents = new Intent();
        intents.putExtras(bundle);
        rv.setOnClickFillInIntent(R.id.imageView, intents);
        return rv;
    }

    private FavoriteMovieData getMovie(int position) {
        if (!cursor.moveToPosition(position)) {
            return null;
        } else {
            return new FavoriteMovieData(cursor);
        }

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
