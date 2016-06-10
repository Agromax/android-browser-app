package com.browser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final Deque<BrowsingState> browsingStates = new LinkedList<>();
    private final MainActivity self = this;
    private ViewGroup currentBrowsingView = null;
    private ArrayAdapter<String> mAutoCompletionAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateVocabulary();

        mAutoCompletionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.input);
        textView.setAdapter(mAutoCompletionAdapter);
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                new VocabAutoCompletion().execute(s.toString());
            }
        });

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = textView.getText().toString();
                Button t = new Button(self);
                t.setText(selected);
//                t.setEnabled(false);

                LinearLayout horizontalNavigationBar = (LinearLayout) findViewById(R.id.navigation);
                horizontalNavigationBar.addView(t);

                textView.setText("");

                BrowsingState previousState = null;
                if (browsingStates.size() > 0) {
                    previousState = browsingStates.peekFirst();
                }
                BrowsingState currentState = new BrowsingState(previousState, selected);
                browsingStates.push(currentState);
                renderState(currentState);
            }
        });

    }

    private class VocabAutoCompletion extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            Vocabulary vocab = Vocabulary.getInstance(self);
            BrowsingState currentBrowsingState = browsingStates.peekFirst();
            String path = "/";
            if (currentBrowsingState != null) {
                List<String> tags = new LinkedList<>();
                for (String node : currentBrowsingState.getNavigation()) {
                    tags.add(XMLUtil.asXMLTag(node));
                }
                path = ListUtil.join(tags, "/") + "/";
            }
            return vocab.query(path, params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            mAutoCompletionAdapter.clear();
            for (String s : strings) {
                mAutoCompletionAdapter.add(s);
            }
        }
    }

    private void transitBack() {
        if (browsingStates.isEmpty()) {
            super.onBackPressed();
        } else {
            BrowsingState prevState = browsingStates.pop();
            renderState(prevState);
        }
    }

    private void renderState(BrowsingState state) {
        RelativeLayout browsingStateLayout = (RelativeLayout) findViewById(R.id.browsing_state);

        // Remove the previous browsing state view
        if (currentBrowsingView != null) {
            browsingStateLayout.removeView(currentBrowsingView);
        }

        // Update the new browsing state view
        currentBrowsingView = new BrowsingStateView(this, state).getView();
        browsingStateLayout.addView(currentBrowsingView);
    }

    @Override
    public void onBackPressed() {
        transitBack();
    }

    private void updateVocabulary() {
        new VocabularyManager(this).execute(getString(R.string.vocabulary_update_url));
    }

}