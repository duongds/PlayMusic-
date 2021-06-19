package com.example.soc_macmini_15.musicplayer.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.soc_macmini_15.musicplayer.Model.SongsList;

import java.util.ArrayList;

public class FavoritesOperations {

    public static final String TAG = "Favorites Database";

    SQLiteOpenHelper dbHandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            FavoritesDadaBaseHandler.COLUMN_ID,
            FavoritesDadaBaseHandler.COLUMN_TITLE,
            FavoritesDadaBaseHandler.COLUMN_SUBTITLE,
            FavoritesDadaBaseHandler.COLUMN_PATH
    };

    public FavoritesOperations(Context context) {
        dbHandler = new FavoritesDadaBaseHandler(context);
    }

    public void open() {
        Log.i(TAG, " Database Opened");
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        Log.i(TAG, "Database Closed");
        dbHandler.close();
    }

    public void addSongFav(SongsList songsList) {
        open();
        ContentValues values = new ContentValues();
        values.put(FavoritesDadaBaseHandler.COLUMN_TITLE, songsList.getSongsTitle());
        values.put(FavoritesDadaBaseHandler.COLUMN_SUBTITLE, songsList.getArtistTitle());
        values.put(FavoritesDadaBaseHandler.COLUMN_PATH, songsList.getPath());

        database.insertWithOnConflict(FavoritesDadaBaseHandler.TABLE_SONGS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        close();
    }

    public void removeSong(String songPath) {
        open();
        String whereClause =
                FavoritesDadaBaseHandler.COLUMN_PATH + "=?";
        String[] whereArgs = new String[]{songPath};

        database.delete(FavoritesDadaBaseHandler.TABLE_SONGS, whereClause, whereArgs);
        close();
    }

    public ArrayList<SongsList> getAllFavorites() {
        open();
        Cursor cursor = database.query(FavoritesDadaBaseHandler.TABLE_SONGS, allColumns,
                null, null, null, null, null);
        ArrayList<SongsList> favSongs = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                SongsList songsList = new SongsList(cursor.getString(cursor.getColumnIndex(FavoritesDadaBaseHandler.COLUMN_TITLE))
                        , cursor.getString(cursor.getColumnIndex(FavoritesDadaBaseHandler.COLUMN_SUBTITLE))
                        , cursor.getString(cursor.getColumnIndex(FavoritesDadaBaseHandler.COLUMN_PATH)));
                favSongs.add(songsList);
            }
        }
        close();
        return favSongs;
    }

}






















