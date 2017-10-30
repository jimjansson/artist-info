package com.jimjansson.artistinfo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.jimjansson.artistinfo.external.coverartarchive.ReleaseGroupResponse;
import com.jimjansson.artistinfo.external.musicbrainz.MusicBrainzResponse;
import com.jimjansson.artistinfo.external.wikipedia.WikipediaResponse;

import java.io.IOException;

/**
 * Created by Jim on 2016-10-05.
 */
public class HttpRequestUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String MUSIC_BRAINZ_REQUEST_TEMPLATE = "http://musicbrainz.org/ws/2/artist/%s?" +
            "&fmt=json" +
            "&inc=url-rels+release-groups";

    public static MusicBrainzResponse getMusicBrainzResponse(String mbid) throws IOException {
        return unmarshall(HttpRequest.get(getMusicBrainzHttpRequestUrl(mbid)), MusicBrainzResponse.class);
    }

    private static String getMusicBrainzHttpRequestUrl(String mbid) {
        return String.format(MUSIC_BRAINZ_REQUEST_TEMPLATE, mbid);
    }


    private static final String WIKIPEDIA_REQUEST_TEMPLATE = "https://en.wikipedia.org/w/api.php?" +
            "action=query&" +
            "format=json&" +
            "prop=extracts&exintro=true&redirects=true&titles=%s";

    public static WikipediaResponse getWikipediaResponse(String title) throws IOException {
        return unmarshall(HttpRequest.get(getWikipediaHttpRequest(title)), WikipediaResponse.class);
    }

    private static String getWikipediaHttpRequest(String title) {
        return String.format(WIKIPEDIA_REQUEST_TEMPLATE, title);
    }

    private static <T> T unmarshall(HttpRequest httpRequest, Class<T> valueType) throws IOException {
        if(httpRequest.ok()) {
            return MAPPER.readValue(httpRequest.body(), valueType);
        }
        return null;
    }
}
