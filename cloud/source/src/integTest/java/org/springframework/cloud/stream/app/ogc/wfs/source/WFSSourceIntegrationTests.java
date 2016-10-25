/**
 * 
 */
package org.springframework.cloud.stream.app.ogc.wfs.source;

import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.stream.annotation.Bindings;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.xml.transform.StringSource;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Some nice integration tests UBA processor
 * 
 * @author Nikolai Bock
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WFSSourceIntegrationTests.Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class WFSSourceIntegrationTests {

    @Autowired
    @Bindings(WFSSourceConfiguration.class)
    protected Source source;

    @Autowired
    protected MessageCollector messageCollector;

    @SpringBootTest(properties = "wfs.service.output=GML2")
    public static class TestXML extends WFSSourceIntegrationTests {

        @Test
        public void test() throws JsonProcessingException, InterruptedException,
                TransformerException {
            // BlockingQueue<Message<?>> messages = messageCollector
            // .forChannel(source.output());
            Message<?> received = messageCollector
                    .forChannel(this.source.output())
                    .poll(10, TimeUnit.SECONDS);
            System.out.println(
                    createPrintableXML(received.getPayload().toString()));
            // messages.stream().map(Message::getPayload)
            // .forEach(System.out::println);
        }

        private String createPrintableXML(String input)
                throws TransformerException {
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                    "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");

            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);

            transformer.transform(new StringSource(input), streamResult);
            return stringWriter.toString();
        }
    }

    @SpringBootTest(properties = "wfs.service.output=application/json")
    public static class TestJson extends WFSSourceIntegrationTests {

        @Test
        public void test() throws JsonProcessingException, InterruptedException,
                TransformerException {
            // BlockingQueue<Message<?>> messages = messageCollector
            // .forChannel(source.output());
            Message<?> received = messageCollector
                    .forChannel(this.source.output())
                    .poll(10, TimeUnit.SECONDS);
            System.out.println(received.getPayload().toString());
            // messages.stream().map(Message::getPayload)
            // .forEach(System.out::println);
        }

    }

    @SpringBootTest(properties = "wfs.service.output=geotools")
    public static class TestGeoTools extends WFSSourceIntegrationTests {

        @Test
        public void test() throws JsonProcessingException, InterruptedException,
                TransformerException {
            Message<?> received = messageCollector
                    .forChannel(this.source.output())
                    .poll(10, TimeUnit.SECONDS);
            FeatureCollection<SimpleFeatureType, SimpleFeature> result = ((FeatureCollection<SimpleFeatureType, SimpleFeature>) received
                    .getPayload());
            FeatureIterator i = result.features();
            while (i.hasNext()) {
                Feature tmp = i.next();
                System.out.println(tmp.getValue());
            }

        }

    }

    @SpringBootApplication
    public static class Application {

    }
}
