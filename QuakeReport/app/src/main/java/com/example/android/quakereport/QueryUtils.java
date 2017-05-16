package com.example.android.quakereport;

/**
 * Created by amatanat on 09.05.17.
 */

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private final String LOG_TAG = QueryUtils.class.getName();

   /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Get URL from String object
     * @param url - String object of url
     * @return - url
     */
    private static URL createUrl(String url){
        URL myUrl = null;
        try{
            myUrl = new URL(url);
        }catch (MalformedURLException e){
            Log.e("QueryUtils", "MalformedURLException" + e);
            return null;
        }
        return myUrl;
    }

     /*
     * Make hhtp request to the server and get response data
     */
     private static String makeHttpRequest(URL url) throws IOException{
        String response = "";

        if (url == null){
            return null;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            // open url connection
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(10000 /* milliseconds */);
            httpURLConnection.setConnectTimeout(15000 /* milliseconds */);

            // set http request method to 'GET'
            httpURLConnection.setRequestMethod("GET");

            // connect to server
            httpURLConnection.connect();

            // check http response code
            if (httpURLConnection.getResponseCode() == 200){

                // get data from server
                inputStream = httpURLConnection.getInputStream();

                // get response data from inputstream
                response = readInputStream(inputStream);
            } else {
                Log.e("QueryUtils", "Response code " + httpURLConnection.getResponseCode());
            }
        }catch (IOException exception) {
            Log.e("QueryUtils", "IOException " + exception);

        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }

        return response;
    }

    /*
    * Read input stream and convert it into String
    */
    private static String readInputStream(InputStream inputStream) throws IOException{
        String result;
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null){

            // convert inputstream (byte) to character with {@link InputStreamReader} one byte at a time
            // with {@link BufferedReader} convert more than one byte at a time
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            while((result = bufferedReader.readLine()) != null) {

                // append each line to the end of stringbuilder
                stringBuilder.append(result);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Earthquake> JSONParser(String input){

        if (TextUtils.isEmpty(input)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the input string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Parse the response given by the input string and
            // build up a list of Earthquake objects with the corresponding data.

            // get JSONObject from given string
            JSONObject data = new JSONObject(input);

            // get features array from JSONObject
            JSONArray features = data.getJSONArray("features");
            //iterate through all elements of the array
            // and get magnitude, place and time for each earthquake
            for (int i = 0; i < features.length(); i++){
                // get earthquake at current index i
                JSONObject element = features.getJSONObject(i);

                // get properties of current earthquake
                JSONObject properties = element.getJSONObject("properties");

                //get magnitude of current earthquake
                double magnitude = properties.getDouble("mag");

                //get location of current earthquake
                String location = properties.getString("place");

                // get time of current earthquake which is in milliseconds
                long timeInMilliseconds = properties.getLong("time");

                // get web page
                String url = properties.getString("url");

                // add current earthquake data to the list of earthquakes
                earthquakes.add(new Earthquake(magnitude,location, timeInMilliseconds, url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("QueryUtils", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Earthquake> earthquakes = JSONParser(jsonResponse);
        Log.i("QueryUtils ", "Task:" + "fetchEarthquakeData is called.....");
        // Return the list of {@link Earthquake}s
        return earthquakes;
    }
}
