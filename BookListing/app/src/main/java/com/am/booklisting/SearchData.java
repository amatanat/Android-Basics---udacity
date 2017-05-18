package com.am.booklisting;

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
 * Created by amatanat on 17.05.17.
 */

public class SearchData {

    private SearchData(){}

    /**
     * convert string url into URL and return
     */
    private static URL createUrl(String stringUrl){
        URL url = null;

        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e("SearchData", "Malformed url exception " + e);
            return null;
        }

        return url;
    }

    /**
     * make http request to the server and return response
     */
    private static String makeHttpRequest(URL url)throws IOException {
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
                Log.e("SearchData", "Response code " + httpURLConnection.getResponseCode());
            }
        }catch (IOException exception) {
            Log.e("SearchData", "IOException " + exception);

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
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Book> JSONParser(String input){

        if (TextUtils.isEmpty(input)){
            return null;
        }

        // Create an empty ArrayList that we can start adding boooks to
        List<Book> books = new ArrayList<>();

        // Try to parse the input string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Parse the response given by the input string and
            // build up a list of Book objects with the corresponding data.

            // get JSONObject from given string
            JSONObject data = new JSONObject(input);

            // get items array from JSONObject
            JSONArray items = data.getJSONArray("items");

            // iterate through item array
            for (int i = 0 ; i < items.length(); i++){

                // get JSONObject of elements
                JSONObject element = items.getJSONObject(i);

                // get JSONObject of 'volumeinfo'
                JSONObject volumeInfo = element.getJSONObject("volumeInfo");

                // get book name
                String bookName = volumeInfo.getString("title");

                // get published date
                String publishedDate = volumeInfo.getString("publishedDate");

                // list of authors
                ArrayList<String> authors = new ArrayList<>();

                //check if 'volumeInfo' contains 'authors'
                if (volumeInfo.has("authors")){

                    // get JSONArray of authors
                    JSONArray authorsList = volumeInfo.getJSONArray("authors");

                    // iterate through the authors JSONArray and add them to the ArrayList of String
                    for(int j = 0; j < authorsList.length(); j++){
                        authors.add(authorsList.getString(j));
                    }

                } else {

                    // if there is no 'authors' add empty space to 'authors'
                   authors.add(" ");
                }

                books.add(new Book(bookName, authors, publishedDate));
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("SearchData", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of books
        return books;
    }

    /**
     * Query the url and return a list of {@link Book} objects.
     */
    public static List<Book> fetchBookData(String requestUrl) {

        try{
            Thread.sleep(2000);
        }catch (InterruptedException ie){
            Log.e("SearchData", "Exception " + ie);
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("SearchData", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Book}
        List<Book> bookList = JSONParser(jsonResponse);
        Log.i("SearchData ", "Task:" + "fetchBookData is called.....");

        // Return the list of {@link Book}
        return bookList;
    }



}
