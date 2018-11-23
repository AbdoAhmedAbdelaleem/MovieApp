package com.example.abdo.moviesstage1.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Abdo on 10/2/2017.
 */

public class MovieContract
{
    public static class MovieEntry implements BaseColumns
    {
        public static final String ID=_ID;
        public static final String MovieId="MovieID";
        public static final String MovieTitle="MovieTitle";
        public static final String MovieTable="MovieEntry";
        public static final String ProviderAuthority="com.example.abdo.moviesstage1";
        public static final Uri GeneralURi = Uri.parse("content://" + ProviderAuthority
                + "/" + MovieTable);
    }
}
