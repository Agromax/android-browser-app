package com.browser;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        textView.setText(browsingState.getNavigation().toString());

        relativeLayout.addView(textView);
        return relativeLayout;
    }

    public void renderView(ViewGroup parent) {
        RelativeLayout relativeLayout = new RelativeLayout(context);

        TextView textView = new TextView(context);
        textView.setText(browsingState.getNavigation().toString());

        relativeLayout.addView(textView);
        parent.addView(relativeLayout);
    }
}
