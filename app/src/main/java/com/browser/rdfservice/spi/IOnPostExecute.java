package com.browser.rdfservice.spi;

import java.util.List;

/**
 * Created by Dell on 10-06-2016.
 */
public interface IOnPostExecute {
    void onPostExecute(List<ITriple> t);
}
