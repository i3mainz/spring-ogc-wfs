package org.springframework.integration.ogc.wfs.config;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractPollingInboundChannelAdapterParser;
import org.springframework.integration.ogc.wfs.inbound.WFSGeoToolsReceivingMessageSource;
import org.w3c.dom.Element;

public class WFSInboundChannelAdapterParser extends AbstractPollingInboundChannelAdapterParser {

    @Override
    protected BeanMetadataElement parseSource(Element element,
            ParserContext parserContext) {
        Class<?> clazz = determineClass(element, parserContext);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .rootBeanDefinition(clazz);
        builder.addConstructorArgValue(element.getAttribute(ID_ATTRIBUTE));

        return builder.getBeanDefinition();
    }

    private static Class<?> determineClass(Element element,
            ParserContext parserContext) {
        Class<?> clazz = null;
        String elementName = element.getLocalName().trim();
        if ("inbound-channel-adapter".equals(elementName)) {
            clazz = WFSGeoToolsReceivingMessageSource.class;
        } else {
            parserContext.getReaderContext().error("element '" + elementName
                    + "' is not supported by this parser.", element);
        }
        return clazz;
    }

}
