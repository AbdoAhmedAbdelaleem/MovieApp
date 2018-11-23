package com.example.abdo.moviesstage1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Created by Abdo on 9/19/2017.
 */

public class NetworkUtils {
    public static boolean IsOnline(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internetInfo = connectivityManager.getActiveNetworkInfo();
        return internetInfo != null && internetInfo.isConnectedOrConnecting();
    }
    public static URL getURL(String urlString) throws MalformedURLException {
        URL returnedUrl = null;
        returnedUrl = new URL(urlString);
        return returnedUrl;
    }
    public static String ReadDataFromURL(URL url)
    {
        String returnedSring="";
        try
        {
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            returnedSring= GetStringFromInputStram(inputStream);
        }
        catch (IOException e)
        {
            Log.e("Error NetworkUtils",e.getMessage());
        }
        return returnedSring;
    }
    public static String GetStringFromInputStram(InputStream is)
    {
        StringBuilder total = new StringBuilder();
        try
        {
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                total.append(line).append('\n');
            }
        }
        catch (Exception ex)
        {
            Log.e("Error NetworkUtils",ex.getMessage());
        }
        return  total.toString();
    }
}
