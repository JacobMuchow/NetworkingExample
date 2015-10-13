package com.quarkworks.networking.service;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author jacobamuchow@gmail.com (Jacob Muchow)
 */
public class NetworkRequest extends AsyncTask<Void, String, String> {
    private static final String TAG = NetworkRequest.class.getSimpleName();

    /*
        Data
     */
    private String url;
    private Callback callback;

    public NetworkRequest(String url, Callback callback) {
        this.url = url;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        OkHttpClient httpClient = new OkHttpClient();

        Request.Builder builder = new Request.Builder().url(url);

        try {
            Response response = httpClient.newCall(builder.build()).execute();
            return response.body().string();

        } catch (IOException e) {
            Log.e(TAG, "Error connecting to url: " + url, e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response);

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON from url: " + url, e);
        }

        callback.done(jsonObject);
    }

    public interface Callback {
        void done(@Nullable JSONObject jsonObject);
    }
}
