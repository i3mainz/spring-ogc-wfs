/**
 * 
 */
package org.springframework.integration.ogc.wfs.inbound;

import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.ogc.wfs.core.WFSGetFeatureParameter;
import org.springframework.messaging.Message;

/**
 * @author Nikolai Bock
 * @param <T>
 *
 */
public abstract class WFSReceivingMessageSource<T>
        extends IntegrationObjectSupport implements MessageSource<T> {

    protected final String serviceURL;
    protected final WFSGetFeatureParameter param;

    protected WFSReceivingMessageSource(String serviceURL,
            WFSGetFeatureParameter param) {
        this.serviceURL = serviceURL;
        this.param = param;
    }

    /**
     * @param payload
     *            the payload
     * @return the message
     */
    protected Message<T> createMessage(T payload) {
        return this.getMessageBuilderFactory().withPayload(payload).build();
    }

}
