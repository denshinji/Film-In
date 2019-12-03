package com.example.filmin.Database;

import android.provider.BaseColumns;

public class FavoriteTvContract {
    public static final class Favorites implements BaseColumns {

        public static final String TABLE_NAME = "favoritetv";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "overview";
        public static final String COLUMN_VOTE_COUNT = "votecount";
        public static final String COLUMN_RELEASE_DATE = "releasedate";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_BACKDROP = "backdrop";
        public static final String COLUMN_RATE = "vote_avarage";


    }
}
