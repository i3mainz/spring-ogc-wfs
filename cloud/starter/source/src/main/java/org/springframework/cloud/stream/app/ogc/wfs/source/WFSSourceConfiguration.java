/**
 * 
 */
package org.springframework.cloud.stream.app.ogc.wfs.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.app.trigger.TriggerConfiguration;
import org.springframework.cloud.stream.app.trigger.TriggerPropertiesMaxMessagesDefaultUnlimited;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ogc.wfs.core.WFSGetFeatureParameter;
import org.springframework.integration.ogc.wfs.dsl.WFS;
import org.springframework.integration.ogc.wfs.dsl.WFSInboundChannelAdapterSpec;
import org.springframework.integration.scheduling.PollerMetadata;

/**
 * @author Nikolai Bock
 *
 */
@Configuration
@EnableConfigurationProperties(value = { WFSSourceProperties.class,
        TriggerPropertiesMaxMessagesDefaultUnlimited.class })
@EnableBinding(Source.class)
@Import(TriggerConfiguration.class)
public class WFSSourceConfiguration {

    @Autowired
    private WFSSourceProperties properties;

    @Autowired
    @Qualifier("defaultPoller")
    PollerMetadata defaultPoller;

    @Bean
    public WFSGetFeatureParameter parameter() {
        WFSGetFeatureParameter param = new WFSGetFeatureParameter(
                properties.getVersion(), properties.getOutput(),
                properties.getTypeNames());
        return param;
    }

    @Bean
    public IntegrationFlow flow(WFSGetFeatureParameter param) {
        WFSInboundChannelAdapterSpec inboundAdapter = WFS
                .inboundAdapter(properties.getUrl(), param);
        return IntegrationFlows
                .from(inboundAdapter, spec -> spec.poller(defaultPoller))
                .channel(Source.OUTPUT).get();
    }
}
