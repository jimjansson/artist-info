package com.jimjansson.artistinfo.external.musicbrainz.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jim on 2016-10-05.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Url {

    private String resource;

    String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
