package com.jimjansson.artistinfo.external.musicbrainz;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jim on 2016-10-04.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Relation {

    @JsonIgnore
    private static final String RESOURCE = "resource";

    private String type;
    private Url url;

    String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    Url getUrl() {
        return url;
    }
}
