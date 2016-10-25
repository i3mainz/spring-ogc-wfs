/**
 * 
 */
package org.springframework.integration.ogc.wfs.inbound;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.Query;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.Name;
import org.springframework.integration.ogc.wfs.core.WFSGetFeatureParameter;
import org.springframework.messaging.Message;

/**
 * @author Nikolai Bock
 *
 */
public class WFSGeoToolsReceivingMessageSource extends
        WFSReceivingMessageSource<FeatureCollection<SimpleFeatureType, SimpleFeature>> {

    private FeatureSource<SimpleFeatureType, SimpleFeature> source;
    private Query query;

    public WFSGeoToolsReceivingMessageSource(String serviceURL,
            WFSGetFeatureParameter param) {
        super(serviceURL, param);
        String getCapabilities = this.serviceURL + "?REQUEST=GetCapabilities";

        Map<String, String> connectionParameters = new HashMap<String, String>();
        connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL",
                getCapabilities);

        DataStore data;

        try {
            data = DataStoreFinder.getDataStore(connectionParameters);

            Name layer = param.getLayerName();
            if (Arrays.asList(data.getTypeNames()).contains(layer.toString())) {
                // SimpleFeatureType schema = data.getSchema(layer);
                source = data.getFeatureSource(layer.toString());

                // // Step 5 - query
                // String geomName =
                // schema.getGeometryDescriptor().getLocalName();
                // Envelope bbox = new Envelope(-100.0, -70, 25, 40);
                //
                // FilterFactory2 ff = CommonFactoryFinder
                // .getFilterFactory2(GeoTools.getDefaultHints());
                // Object polygon = JTS.toGeometry(bbox);
                // Intersects filter = ff.intersects(ff.property(geomName),
                // ff.literal(polygon));
                //
                // Query query = new Query(layer.getLocalPart(), filter,
                // new String[] { geomName });

                query = null;
            }
        } catch (IOException e) {
            source = null;
            query = null;
        }

    }

    @Override
    public Message<FeatureCollection<SimpleFeatureType, SimpleFeature>> receive() {
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = null;
        try {
            if (source != null) {
                if (query != null) {
                    features = source.getFeatures(query);
                } else {
                    features = source.getFeatures();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return createMessage(features);
    }

}
