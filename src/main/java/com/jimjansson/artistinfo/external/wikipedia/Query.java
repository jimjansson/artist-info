package com.jimjansson.artistinfo.external.wikipedia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * Created by Jim on 2016-10-05.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Query {

    private Map<String, Page> pages;

    public void setPages(Map<String, Page> pages) {
        this.pages = pages;
    }

    Map<String, Page> getPages() {
        return pages;
    }
}
