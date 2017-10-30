package com.jimjansson.artistinfo.external.musicbrainz.request;

import com.github.kevinsawicki.http.HttpRequest;
import com.jimjansson.artistinfo.external.RestRequest;
import com.jimjansson.artistinfo.external.musicbrainz.response.MusicBrainzResponse;

import java.io.IOException;

public class MusicBrainzRequest extends RestRequest {

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
}
