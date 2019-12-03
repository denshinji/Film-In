package com.example.favmov;

import android.database.Cursor;

interface LoadMovieCallback {
    void postExecute(Cursor movie);
}
