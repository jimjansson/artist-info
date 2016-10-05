package com.jimjansson.artistinfo.musicbrainz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jim on 2016-10-04.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Relation {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
