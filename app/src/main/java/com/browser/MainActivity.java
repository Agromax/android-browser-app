package com.browser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Deque;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final RandomShit randomShit = new RandomShit(System.currentTimeMillis());
    private final Deque<BrowsingState> browsingStates = new LinkedList<>();
    private final MainActivity self = this;
    private ViewGroup currentBrowsingView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.input);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected = textView.getText().toString();
                Button t = new Button(self);
                t.setText(selected);
                t.setEnabled(false);

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

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };
}
