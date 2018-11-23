package com.example.abdo.moviesstage1.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abdo on 10/2/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    Context context;
    public static final String MovieTableCreation = "create table " + MovieContract.MovieEntry.MovieTable + " (" +
            MovieContract.MovieEntry.ID + " Integer Primary key AutoIncrement ," + MovieContract.MovieEntry.MovieId + " integer not null ," +
            MovieContract.MovieEntry.MovieTitle + " Text);";
    public static final String DBName = "Movies";
    public static final int version = 1;

    public MovieDBHelper(Context context) {
        super(context, DBName, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MovieTableCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + MovieContract.MovieEntry.MovieTable);
        onCreate(sqLiteDatabase);
    }

    public long Insert(long id, String title) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.MovieId, id);
        values.put(MovieContract.MovieEntry.MovieTitle, title);
        return writableDatabase.insert(MovieContract.MovieEntry.MovieTable, null, values);
    }
    public long Insert(ContentValues values) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return writableDatabase.insert(MovieContract.MovieEntry.MovieTable, null, values);
    }
    public long Update(long id, String title) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.MovieId, id);
        values.put(MovieContract.MovieEntry.MovieTitle, title);
        String selection = MovieContract.MovieEntry.MovieId + "=?";
        String[] selectionsArgs = new String[]{id + ""};
        return writableDatabase.update(MovieContract.MovieEntry.MovieTable, values, selection, selectionsArgs);
    }
    public int Update(long id, ContentValues values) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String selection = MovieContract.MovieEntry.MovieId + "=?";
        String[] selectionsArgs = new String[]{id + ""};
        return writableDatabase.update(MovieContract.MovieEntry.MovieTable, values, selection, selectionsArgs);
    }

    public Cursor Quey(long id) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String[] projections = new String[]{MovieContract.MovieEntry.MovieId, MovieContract.MovieEntry.MovieTitle};
        String selection = MovieContract.MovieEntry.MovieId + "=?";
        String[] selectionsArgs = new String[]{id + ""};
        return readableDatabase.query(MovieContract.MovieEntry.MovieTable, projections, selection, selectionsArgs, null, null, null);
    }

    public int Delete(long id) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String selection = MovieContract.MovieEntry.MovieId + "=?";
        String[] selectionsArgs = new String[]{id + ""};
        return writableDatabase.delete(MovieContract.MovieEntry.MovieTable, selection, selectionsArgs);
    }
    public int Delete() {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        return writableDatabase.delete(MovieContract.MovieEntry.MovieTable, null, null);
    }

    public Cursor Quey() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String[] projections = new String[]{MovieContract.MovieEntry.MovieId, MovieContract.MovieEntry.MovieTitle};
        return readableDatabase.query(MovieContract.MovieEntry.MovieTable, projections, null, null, null, null, null);
    }
}
