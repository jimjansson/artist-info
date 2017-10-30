package com.jimjansson.artistinfo.external.wikipedia.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Optional;

/**
 * Created by Jim on 2016-10-05.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class WikipediaResponse {

    private Query query;

    private Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @JsonIgnore
    public String getDescription() {
        if(getQuery() != null && getQuery().getPages() != null) {
            Optional<Page> pageOptional = getQuery().getPages().values().stream().findFirst();
            if(pageOptional.isPresent()) {
                return pageOptional.get().getExtract();
            }
        }
        return null;
    }

    /**
     * Created by Jim on 2016-10-05.
     */
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class Page {

        private String extract;

        String getExtract() {
            return extract;
        }

        public void setExtract(String extract) {
            this.extract = extract;
        }
    }
}
