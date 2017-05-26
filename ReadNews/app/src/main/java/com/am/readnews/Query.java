package com.am.readnews;

/**
 * Created by amatanat on 26.05.17.
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
Helper methods for requesting and receiving data from 'the guargian'.
*/
public class Query {

    /**
     * Create and return URL from given string url
     */
    private static URL createUrl(String stringUrl){
        URL url = null;

        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e("Query", "MalformedURLException: ", e);
        }
        return  url;
    }

    /*
    * Make hhtp request to the server and get response data
    */
    private static String makeHttpRequest(URL url) throws IOException {
        String response = "";

        if (url == null){
            return null;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            // open url connection
            httpURLConnection = (HttpURLConnection) url.openConnection();

            // milliseconds
            httpURLConnection.setReadTimeout(10000);
            // milliseconds
            httpURLConnection.setConnectTimeout(15000);

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
                Log.e("Query", "Response code " + httpURLConnection.getResponseCode());
            }
        }catch (IOException exception) {
            Log.e("Query", "IOException: " + exception);

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
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<News> JSONParser(String input){

        // check if input string is empty
        if (TextUtils.isEmpty(input)){
            return null;
        }

        // Create an empty ArrayList that we can start adding news to
        List<News> newsList = new ArrayList<>();

        // Try to parse the input string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Parse the response given by the input string and
            // build up a list of News objects with the corresponding data.

            // get JSONObject from given string
            JSONObject data = new JSONObject(input);

            // get results array from JSONObject
            JSONArray results = data.getJSONArray("results");

            //iterate through all elements of the array
            // and get title, url and publication date for each news
            for (int i = 0; i < results.length(); i++){

                // get news at current index i
                JSONObject element = results.getJSONObject(i);

                // get title of current news
                String title = element.getString("webTitle");

                // get web page of current news
                String url = element.getString("webUrl");

                //get publication data of current news
                String publicationDate = element.getString("webPublicationDate");

                // get section name
                String sectionName = element.getString("sectionName");

                // add current news data to the news list
                newsList.add(new News(title, publicationDate, url, sectionName));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("Query", "Problem parsing the news JSON results: ", e);
        }

        // Return the list of news
        return newsList;
    }

    /**
     * Query the guardians dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchData(String requestUrl) {

        try{
            Thread.sleep(2000);
        }catch (InterruptedException ie){
            Log.e("Query", "InterruptedException: " + ie);
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Query", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of news
        List<News> newsList = JSONParser(jsonResponse);

        // Return the list of news
        return newsList;
    }

}
