package com.example.myapplication.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static String API_BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1/objects/";

    public static URL generateUrl (String OBJECT_ID) {
        Uri buildUri = Uri.parse(API_BASE_URL + OBJECT_ID)
                .buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromUrl (URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); //разделитель, по дефолту - пробел, тут - не делим строку на подстроки

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        }
        finally { //в любом случае
            urlConnection.disconnect();
        }
    }
}
