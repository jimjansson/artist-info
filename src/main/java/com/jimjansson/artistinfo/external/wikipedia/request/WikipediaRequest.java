package com.jimjansson.artistinfo.external.wikipedia.request;

import com.github.kevinsawicki.http.HttpRequest;
import com.jimjansson.artistinfo.external.RestRequest;
import com.jimjansson.artistinfo.external.wikipedia.response.WikipediaResponse;

import java.io.IOException;

public class WikipediaRequest extends RestRequest {

    private static final String WIKIPEDIA_REQUEST_TEMPLATE = "https://en.wikipedia.org/w/api.php?" +
            "action=query&" +
            "format=json&" +
            "prop=extracts&exintro=true&redirects=true&titles=%s";

    private final String title;

    private WikipediaRequest(String title) {
        this.title = title;
    }

    public static WikipediaRequest createWikipediaRequest(String title) {
        return new WikipediaRequest(title);
    }

    public WikipediaResponse getWikipediaResponse() throws IOException {
        return unmarshall(HttpRequest.get(getWikipediaHttpRequest()), WikipediaResponse.class);
    }

    private String getWikipediaHttpRequest() {
        return String.format(WIKIPEDIA_REQUEST_TEMPLATE, title);
    }
}
