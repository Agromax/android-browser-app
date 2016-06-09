package com.browser;

import android.content.Context;
import android.os.AsyncTask;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Anurag Gautam
 */
public class VocabularyManager extends AsyncTask<String, Void, Void> {

    private final Context context;

    public VocabularyManager(Context context) {
        this.context = context;

    }

    @Override
    protected Void doInBackground(String... urls) {
        String xmlData = Downloader.download(urls[0]);
        try {
            FileOutputStream fout = context.openFileOutput(context.getString(R.string.vocabulary_file_name), 0);
            fout.write(xmlData.getBytes());
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
