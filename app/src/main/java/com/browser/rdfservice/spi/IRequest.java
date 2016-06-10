package com.browser.rdfservice.spi;

import java.net.URI;

/**
 * @author Anurag
 */
public interface IRequest {
    String getConstraint();

    URI getURI();

    okhttp3.Request getHttpRequest();
}
