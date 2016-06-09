package com.browser;

import java.util.ArrayList;

/**
 * Represents a particular state of browsing
 *
 * @author Anurag Gautam
 */
public class BrowsingState {
    private final long createdAt;             // Timestamp at which this browsing state was created
    private final ArrayList<String> nav;      // List of navigation

    public BrowsingState(BrowsingState prevState, String currentTerm) {
        this.nav = new ArrayList<>();
        if (prevState != null) {
            for (String t : prevState.getNavigation()) {
                this.nav.add(t);
            }
        }
        this.nav.add(currentTerm);

        this.createdAt = System.currentTimeMillis();
    }

    public ArrayList<String> getNavigation() {
        return nav;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return nav.toString();
    }
}
