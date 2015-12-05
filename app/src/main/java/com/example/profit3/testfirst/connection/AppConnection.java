package com.example.profit3.testfirst.connection;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by Profit3 on 21/11/2015.
 */
public class AppConnection {
    private final String LOG_TAG = AppConnection.class.getSimpleName();
    // These two need to be declared outside the try/catch
    // so that they can be closed in the finally block.
    private static String APPID= "b6acf08c16531a1081f95956ad5698ed";
    public static final String BASE_URL="http://api.openweathermap.org/data/2.5/forecast/daily?";

    public static final String QUERY_PARAM="q";
    public static final String MODE_PARAM="mode";
    public static final String UNITS_PARAM="units";
    public static final String DAYS_PARAM="cnt";
    public static final String DEFAULT_MODE="json";
    public static final String DEFAULT_UNITS="metric";
    public String buildRequestURLByParameters(String q,String mode,String units,int days){

        Uri buildURL=Uri.parse(BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM,q).appendQueryParameter(MODE_PARAM,mode).appendQueryParameter(UNITS_PARAM,units).appendQueryParameter(DAYS_PARAM,Integer.toString(days)).appendQueryParameter("APPID",APPID).build();
        System.out.println("current url="+buildURL.toString()   );
        return  buildURL.toString();
    }
    public String getResponseByUrl(String baseUrl){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = "";

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast


            //    System.out.println("baseURL="+baseUrl);
            URL url = new URL(baseUrl);
            // URLConnection connection = url.openConnection();
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

// Send post request

            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return forecastJsonStr;
    }
}
