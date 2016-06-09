package com.browser;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 */
public class BrowsingStateView {
    private final BrowsingState browsingState;
    private final Context context;

    public BrowsingStateView(Context context, BrowsingState state) {
        this.context = context;
        this.browsingState = state;
    }

    public RelativeLayout getView() {
        RelativeLayout relativeLayout = new RelativeLayout(context);

        TextView textView = new TextView(context);

        textView.setText(readFile());

        relativeLayout.addView(textView);
        return relativeLayout;
    }

    private String readFile() {
        String fileName = context.getString(R.string.vocabulary_file_name);
        StringBuilder s = new StringBuilder();
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}
