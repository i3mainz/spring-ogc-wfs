package org.springframework.cloud.stream.app.ogc.wfs.source;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wfs.service")
public class WFSSourceProperties {

    private String url;
    private String version;
    private String output;
    private String typeNames;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the output
     */
    public String getOutput() {
        return output;
    }

    /**
     * @param output
     *            the output to set
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * @return the typeNames
     */
    public String getTypeNames() {
        return typeNames;
    }

    /**
     * @param typeNames
     *            the typeNames to set
     */
    public void setTypeNames(String typeNames) {
        this.typeNames = typeNames;
    }

}
