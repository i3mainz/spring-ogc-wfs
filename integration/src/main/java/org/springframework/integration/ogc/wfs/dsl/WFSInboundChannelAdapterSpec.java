package org.springframework.integration.ogc.wfs.dsl;

import org.springframework.integration.dsl.core.MessageSourceSpec;
import org.springframework.integration.ogc.wfs.core.WFSGetFeatureParameter;
import org.springframework.integration.ogc.wfs.inbound.WFSGeoToolsReceivingMessageSource;
import org.springframework.integration.ogc.wfs.inbound.WFSHttpReceivingMessageSource;
import org.springframework.integration.ogc.wfs.inbound.WFSReceivingMessageSource;

public class WFSInboundChannelAdapterSpec extends
        MessageSourceSpec<WFSInboundChannelAdapterSpec, WFSReceivingMessageSource<?>> {

    private final String serviceURL;
    private final WFSGetFeatureParameter param;

    public WFSInboundChannelAdapterSpec(String serviceURL,
            WFSGetFeatureParameter param) {
        this.serviceURL = serviceURL;
        this.param = param;
    }

    @Override
    protected WFSReceivingMessageSource<?> doGet() {
        WFSReceivingMessageSource<?> msgSource;
        if (this.param.getOutput().equalsIgnoreCase("geotools")) {
            msgSource = new WFSGeoToolsReceivingMessageSource(serviceURL,
                    param);
        } else {
            msgSource = new WFSHttpReceivingMessageSource(serviceURL, param);
        }
        return msgSource;
    }
}
