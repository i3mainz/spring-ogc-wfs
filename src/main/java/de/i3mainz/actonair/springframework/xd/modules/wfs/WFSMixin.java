package de.i3mainz.actonair.springframework.xd.modules.wfs;

import javax.validation.constraints.NotNull;

import org.springframework.xd.module.options.spi.ModuleOption;

public class WFSMixin {

    private String serviceURL = "http://10.153.100.2/geoserver/ows";
    private String serviceVersion = "1.0.0";
    private String serviceRequest = "GetFeature";
    private String typeNames = "gebGIS:raeume_all";
    private String serviceOutputFormat = "application/json";
    private String propertyNames = "RaumNr,name,vorname,email,address,city,zip";
    private String sortBy = "RaumNr";
    private String cqlfilter = "email IS NOT NULL";
    private String addParam;

    @NotNull
    public String getServiceURL() {
        return serviceURL;
    }

    @ModuleOption("URL of the geoserver")
    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    @ModuleOption("WFS service version")
    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getServiceRequest() {
        return serviceRequest;
    }

    @ModuleOption("WFS request to call")
    public void setServiceRequest(String serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public String getTypeNames() {
        return typeNames;
    }

    @ModuleOption("names of requested feature types")
    public void setTypeNames(String typeNames) {
        this.typeNames = typeNames;
    }

    public String getServiceOutputFormat() {
        return serviceOutputFormat;
    }

    @ModuleOption("format of wfs response")
    public void setServiceOutputFormat(String serviceOutputFormat) {
        this.serviceOutputFormat = serviceOutputFormat;
    }

    public String getPropertyNames() {
        return propertyNames;
    }

    @ModuleOption("Properties which only should be returned")
    public void setPropertyNames(String propertyNames) {
        this.propertyNames = propertyNames;
    }

    public String getSortBy() {
        return sortBy;
    }

    @ModuleOption("Sort the response on this property")
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getCqlfilter() {
        return cqlfilter;
    }

    @ModuleOption("CQL-Filter to filter the result")
    public void setCqlfilter(String cqlfilter) {
        this.cqlfilter = cqlfilter;
    }

    public String getAddParam() {
        return addParam;
    }

    @ModuleOption("Additional query parameter")
    public void setAddParam(String addParam) {
        this.addParam = addParam;
    }

}
