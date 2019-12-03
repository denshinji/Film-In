package com.example.favmov;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {
    public static final String AUTHORITY = "com.example.filmin";
    private static final String SCHEME = "content";


    public static final class Favorites implements BaseColumns {

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
        public static final String COLUMN_VOTE_COUNT = "votecount";
        public static final String COLUMN_RELEASE_DATE = "releasedate";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_BACKDROP = "backdrop";
        public static final String COLUMN_RATE = "vote_avarage";

        static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

    }


}
