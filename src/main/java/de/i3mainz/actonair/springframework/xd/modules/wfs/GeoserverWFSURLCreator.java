package de.i3mainz.actonair.springframework.xd.modules.wfs;

import org.springframework.beans.factory.InitializingBean;

public class GeoserverWFSURLCreator implements InitializingBean {

    private boolean outputformat;
    private boolean properties;
    private boolean sortby;
    private boolean filter;
    private boolean param;

    private String result;

    public void setOutputformat(String outputformat) {
        this.outputformat = !isNullOrEmpty(outputformat);
    }

    public void setProperties(String properties) {
        this.properties = !isNullOrEmpty(properties);
    }

    public void setSortby(String sortby) {
        this.sortby = !isNullOrEmpty(sortby);
    }

    public void setFilter(String filter) {
        this.filter = !isNullOrEmpty(filter);
    }

    public void setParam(String param) {
        this.param = !isNullOrEmpty(param);
    }
    
    public String getURL(){
        return this.result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("{url}");
        builder.append("?");
        builder.append("service=WFS");
        builder.append("&");
        builder.append("version={version}");
        builder.append("&");
        builder.append("request={request}");
        builder.append("&");
        builder.append("typeNames={typenames}");
        if (this.outputformat) {
            builder.append("&");
            builder.append("outputFormat={outputformat}");
        }
        if (this.properties) {
            builder.append("&");
            builder.append("propertyName={properties}");
        }
        if (this.sortby) {
            builder.append("&");
            builder.append("sortBy={sortby}");
        }
        if (this.filter) {
            builder.append("&");
            builder.append("cql_filter={filter}");
        }
        if (this.param) {
            builder.append("&");
            builder.append("{param}");
        }
        
        this.result = builder.toString();
    }

    private boolean isNullOrEmpty(String entry) {
        return entry == null || entry.isEmpty();
    }

}
