package com.browser.rdfservice.spi;

import org.json.JSONObject;

/**
 * Created by Dell on 10-06-2016.
 */
public interface ITriple {
    String sub();

    String pre();

    String obj();

    String asCSV();

    String asTSV();

    JSONObject asJSON();
}
