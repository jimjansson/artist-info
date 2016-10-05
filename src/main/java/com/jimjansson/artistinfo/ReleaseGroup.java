package com.jimjansson.artistinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jim on 2016-10-04.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ReleaseGroup {

    private String type;
    private String title;
    private String id;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return title;
    }
}
