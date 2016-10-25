/**
 * 
 */
package org.springframework.integration.ogc.wfs.core;

import java.util.HashMap;

import org.geotools.feature.NameImpl;
import org.opengis.feature.type.Name;

/**
 * @author Nikolai Bock
 *
 */
public class WFSGetFeatureParameter extends HashMap<String, Object> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String OUTPUT_KEY = "outputFormat";
    public static final String LAYER_KEY = "typeNames";
    public static final String VERSION_KEY = "version";

    /**
     * @param output
     */
    public WFSGetFeatureParameter(String version, String output,
            String layers) {
        super();
        this.put(VERSION_KEY, version);
        this.put(OUTPUT_KEY, output);
        this.put(LAYER_KEY, layers);
    }

    /**
     * @return the output
     */
    public String getOutput() {
        return this.get(OUTPUT_KEY).toString();
    }

    public String getVersion() {
        return this.getOrDefault(VERSION_KEY, "1.0.0").toString();
    }

    public Name getLayerName() {
        String[] name = this.get(LAYER_KEY).toString().split(":");
        if (name.length == 2) {
            return new NameImpl(name[0], name[1]);
        }
        return new NameImpl(name[0]);
    }

}
