package com.jimjansson.artistinfo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.jimjansson.artistinfo.external.musicbrainz.response.MusicBrainzResponse;
import com.jimjansson.artistinfo.external.wikipedia.response.WikipediaResponse;

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

    private static <T> T unmarshall(HttpRequest httpRequest, Class<T> valueType) throws IOException {
        if(httpRequest.ok()) {
            return MAPPER.readValue(httpRequest.body(), valueType);
        }
        return null;
    }
}
