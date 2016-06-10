package com.browser.rdfservice;

import android.content.Context;
import android.os.AsyncTask;

import com.browser.rdfservice.spi.IOnPostExecute;
import com.browser.rdfservice.spi.IRequest;
import com.browser.rdfservice.spi.ITriple;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @author shadow_lord
 */
public class RDFService {
    private final Context context;
    private final OkHttpClient okHttpClient = new OkHttpClient();

    public RDFService(Context context) {
        this.context = context;
    }

    public void execute(IRequest request, final IOnPostExecute onPostExecute) {
        new AsyncTask<IRequest, Void, String>() {
            @Override
            protected String doInBackground(IRequest... params) {
                try {
                    Response res = okHttpClient.newCall(params[0].getHttpRequest()).execute();
                    return res.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s != null) {
                    ArrayList<ITriple> triples = new ArrayList<>();
                    try {
                        JSONObject json = new JSONObject(s);
                        JSONArray triplets = json.getJSONArray("triplets");
                        for (int i = 0; i < triplets.length(); i++) {
                            triples.add(new RDFTriple(triplets.getJSONObject(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (onPostExecute != null) {
                        onPostExecute.onPostExecute(triples);
                    }
                }
            }
        }.execute(request);
    }
}
