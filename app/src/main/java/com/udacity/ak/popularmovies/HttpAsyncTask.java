package com.udacity.ak.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import com.udacity.ak.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

// Class to perform network requests
public class HttpAsyncTask extends AsyncTask<URL, Void, String> {
    private static final String TAG = HttpAsyncTask.class.getSimpleName();
    private TaskCompletionHandler taskCompletionHandler;

    public HttpAsyncTask(TaskCompletionHandler activityContext) {
        taskCompletionHandler = activityContext;
    }

    @Override
    protected String doInBackground(URL... urls) {
        String httpResponse = null;
        try {
            httpResponse = NetworkUtils.getResponseFromHttpUrl(urls[0]);
        } catch (IOException e) {
            Log.e(TAG, "Error reading from server:", e);
        }
        return httpResponse;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "Json Response: " + s);
        if (s != null && !s.isEmpty()) {
            taskCompletionHandler.onTaskCompleted(s);
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}
