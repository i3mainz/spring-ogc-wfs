/**
 * 
 */
package org.springframework.integration.ogc.wfs.support;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Nikolai Bock
 *
 */
public class WFSAdapter extends RestTemplate {

    private MediaType defaultResponseContentType;

    public WFSAdapter() {
        setDefaultResponseContentType("text/xml; subtype=\"gml/2.1.2\"");
    }

    public void setDefaultResponseContentType(
            String defaultResponseContentType) {
        this.defaultResponseContentType = MediaType
                .parseMediaType(defaultResponseContentType);
    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method,
            RequestCallback requestCallback,
            final ResponseExtractor<T> responseExtractor)
            throws RestClientException {

        return super.doExecute(url, method, requestCallback,
                new ResponseExtractor<T>() {
                    public T extractData(ClientHttpResponse response)
                            throws IOException {
                        try {
                            if (response.getHeaders().getContentType() == null

                                    && defaultResponseContentType != null) {
                                response.getHeaders().setContentType(
                                        defaultResponseContentType);
                            }
                        } catch (InvalidMediaTypeException e) {
                            response.getHeaders()
                                    .setContentType(defaultResponseContentType);
                        }

                        if (responseExtractor != null)
                            return responseExtractor.extractData(response);
                        else
                            return null;
                    }
                });
    }

    public <T> T getFeatureRequest(String path, Class<T> type) {

        T result = getForObject(path, type);
        return result;
    }

}
