package com.browser.rdfservice;

import android.content.Context;

import com.browser.R;
import com.browser.rdfservice.spi.IRequest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Dell on 10-06-2016.
 */
public class RDFRequest implements IRequest {

    private final String constraint;
    private final Context context;

    public RDFRequest(Context context, CharSequence constraint) {
        this.constraint = constraint.toString();
        this.context = context;
    }

    @Override
    public String getConstraint() {
        return constraint;
    }

    @Override
    public URI getURI() {
        String url = context.getString(R.string.rdf_service_url);
        URI uri = null;
        try {
            uri = new URI(url + "?q=" + getConstraint());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    @Override
    public okhttp3.Request getHttpRequest() {
        URI uri = getURI();
        okhttp3.Request request = null;
        if (uri != null) {
            try {
                request = new okhttp3.Request.Builder()
                        .url(uri.toURL())
                        .build();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return request;
    }
}
