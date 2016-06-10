package com.browser;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.browser.rdfservice.RDFRequestBuilder;
import com.browser.rdfservice.RDFService;
import com.browser.rdfservice.spi.IOnPostExecute;
import com.browser.rdfservice.spi.ITriple;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 */
public class BrowsingStateView {
    private final BrowsingState browsingState;
    private final Context context;

    public BrowsingStateView(Context context, BrowsingState state) {
        this.context = context;
        this.browsingState = state;
    }

    public void getView(final TableLayout parent) {

        new RDFService(context)
                .execute(new RDFRequestBuilder().buildRequest(context, ""), new IOnPostExecute() {
                    @Override
                    public void onPostExecute(List<ITriple> triples) {
                        parent.removeAllViews();                        // Clear the table

                        for (ITriple triple : triples) {
                            TableRow row = new TableRow(context);

                            TextView subjectView = new TextView(context); // Subject
                            subjectView.setText(triple.sub());

                            TextView predicateView = new TextView(context);
                            predicateView.setText(triple.pre());

                            TextView objectView = new TextView(context);
                            objectView.setText(triple.obj());

                            row.addView(subjectView);
                            row.addView(predicateView);
                            row.addView(objectView);

                            parent.addView(row);
                        }
                    }
                });

/*
        RelativeLayout relativeLayout = new RelativeLayout(context);

        TextView textView = new TextView(context);

        textView.setText(browsingState.getNavigation().toString());

        relativeLayout.addView(textView);
        return relativeLayout;*/
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
