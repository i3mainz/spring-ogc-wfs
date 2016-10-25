/**
 * 
 */
package org.springframework.integration.ogc.wfs.inbound;

import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.integration.ogc.wfs.core.WFSGetFeatureParameter;
import org.springframework.integration.ogc.wfs.support.WFSAdapter;
import org.springframework.messaging.Message;

/**
 * @author Nikolai Bock
 *
 */
public class WFSHttpReceivingMessageSource
        extends WFSReceivingMessageSource<String> {

    public WFSHttpReceivingMessageSource(String serviceURL,
            WFSGetFeatureParameter param) {
        super(serviceURL, param);
    }

    private final WFSAdapter template = new WFSAdapter();

    @Override
    public Message<String> receive() {
        String wfsurl = this.serviceURL + "?service=WFS&request=GetFeature&"
                + param.entrySet().stream().collect(Collectors.mapping(
                        this::createParamString, Collectors.joining("&")));

        System.out.println(wfsurl);

        return createMessage(template.getFeatureRequest(wfsurl, String.class));

    }

    /**
     * @return
     */
    private String createParamString(Entry<String, Object> e) {
        return e.getKey() + "=" + e.getValue();
    }

}
