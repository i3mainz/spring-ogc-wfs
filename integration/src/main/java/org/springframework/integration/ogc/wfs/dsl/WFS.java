/**
 * 
 */
package org.springframework.integration.ogc.wfs.dsl;

import org.springframework.integration.ogc.wfs.core.WFSGetFeatureParameter;

/**
 * @author Nikolai Bock
 *
 */
public interface WFS {

    public static WFSInboundChannelAdapterSpec inboundAdapter(String serviceURL,
            WFSGetFeatureParameter param) {
        return new WFSInboundChannelAdapterSpec(serviceURL, param);
    }

}
