package com.jimjansson.artistinfo.external.wikipedia.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jimjansson.artistinfo.external.wikipedia.response.WikipediaResponse;

import java.util.Map;

/**
 * Created by Jim on 2016-10-05.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Query {

    private Map<String, WikipediaResponse.Page> pages;

    public void setPages(Map<String, WikipediaResponse.Page> pages) {
        this.pages = pages;
    }

    Map<String, WikipediaResponse.Page> getPages() {
        return pages;
    }
}
