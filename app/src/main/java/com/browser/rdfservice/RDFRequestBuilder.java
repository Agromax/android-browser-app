package com.browser.rdfservice;

import android.content.Context;

import com.browser.rdfservice.spi.IRequest;

/**
 * @author Stana
 */
public class RDFRequestBuilder {
    public IRequest buildRequest(Context context, CharSequence constraint) {
        return new RDFRequest(context, constraint);
    }
}
