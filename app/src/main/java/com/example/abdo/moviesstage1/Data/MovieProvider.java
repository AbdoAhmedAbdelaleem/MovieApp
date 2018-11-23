package com.example.abdo.moviesstage1.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Abdo on 10/2/2017.
 */

public class MovieProvider extends ContentProvider {
  public static UriMatcher matcher;
   static final int MovieEntry=100;
   static final int movieEntryId=101;
    MovieDBHelper dbhelper;
    static
   {
       matcher=new UriMatcher(UriMatcher.NO_MATCH);
       matcher.addURI(MovieContract.MovieEntry.ProviderAuthority,MovieContract.MovieEntry.MovieTable,MovieEntry);
       matcher.addURI(MovieContract.MovieEntry.ProviderAuthority,MovieContract.MovieEntry.MovieTable+"/#",movieEntryId);
   }
    @Override
    public boolean onCreate() {
        dbhelper=new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        int matchCode= matcher.match(uri);
        Cursor returnedCursor=null;
        switch(matchCode)
        {
            case MovieEntry:
               returnedCursor= dbhelper.Quey();
                break;
            case movieEntryId:
               long id= ContentUris.parseId(uri);
                returnedCursor= dbhelper.Quey(id);
                break;
        }
        return returnedCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int matchCode= matcher.match(uri);
        long returnedID=-1;
        switch(matchCode)
        {
            case MovieEntry:
                returnedID= dbhelper.Insert(contentValues);
                break;
        }
        if(returnedID!=-1)
        return ContentUris.withAppendedId(uri,returnedID);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int matchCode= matcher.match(uri);
        int numOfRowsDeleted=0;
        switch(matchCode)
        {
            case MovieEntry:
                numOfRowsDeleted= dbhelper.Delete();
                break;
            case movieEntryId:
                long id= ContentUris.parseId(uri);
                numOfRowsDeleted= dbhelper.Delete(id);
                break;
        }
        return numOfRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int matchCode= matcher.match(uri);
        int numOfRowsUpdated =0;
        switch(matchCode)
        {
            case movieEntryId:
                long id= ContentUris.parseId(uri);
                numOfRowsUpdated = dbhelper.Update(id,contentValues);
                break;
        }
        return numOfRowsUpdated;
    }
}
