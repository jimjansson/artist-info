package com.jimjansson.artistinfo.external.musicbrainz.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.jimjansson.artistinfo.external.musicbrainz.response.MusicBrainzResponse;

import java.io.IOException;

public class MusicBrainzRequest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String MUSIC_BRAINZ_REQUEST_TEMPLATE = "http://musicbrainz.org/ws/2/artist/%s?" +
            "&fmt=json" +
            "&inc=url-rels+release-groups";

    private final String mbid;

    private MusicBrainzRequest(String mbid) {
        this.mbid = mbid;
    }

    public static MusicBrainzRequest createMusicBrainzRequest(String mbid) {
        return new MusicBrainzRequest(mbid);
    }

    public MusicBrainzResponse getMusicBrainzResponse() throws IOException {
        return unmarshall(HttpRequest.get(getMusicBrainzHttpRequestUrl()), MusicBrainzResponse.class);
    }

    private String getMusicBrainzHttpRequestUrl() {
        return String.format(MUSIC_BRAINZ_REQUEST_TEMPLATE, mbid);
    }

    private <T> T unmarshall(HttpRequest httpRequest, Class<T> valueType) throws IOException {
        if(httpRequest.ok()) {
            return MAPPER.readValue(httpRequest.body(), valueType);
        }
        return null;
    }
}
