package com.jimjansson.artistinfo.wikipedia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jim on 2016-10-05.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class WikipediaResponse {

    private Query query;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
