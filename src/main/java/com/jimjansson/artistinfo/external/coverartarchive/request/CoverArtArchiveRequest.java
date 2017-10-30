package com.jimjansson.artistinfo.external.coverartarchive.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.jimjansson.artistinfo.external.coverartarchive.ReleaseGroupResponse;

import java.io.IOException;

public class CoverArtArchiveRequest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String COVER_ART_ARCHIVE_REQUEST_TEMPLATE = "http://coverartarchive.org/release-group/%s";

    private String releaseGroupMbid;

    private CoverArtArchiveRequest(String releaseGroupMbid) {
        this.releaseGroupMbid = releaseGroupMbid;
    }

    public static CoverArtArchiveRequest createCoverArtArchiveRequest(String releaseGroupMbid) {
        return new CoverArtArchiveRequest(releaseGroupMbid);
    }

    public ReleaseGroupResponse getReleaseGroupResponse() throws IOException {
        return unmarshall(HttpRequest.get(getCoverArtArchiveReleaseGroupsHttpRequest()),
                ReleaseGroupResponse.class);
    }

    private String getCoverArtArchiveReleaseGroupsHttpRequest() {
        return String.format(COVER_ART_ARCHIVE_REQUEST_TEMPLATE, releaseGroupMbid);
    }

    private static <T> T unmarshall(HttpRequest httpRequest, Class<T> valueType) throws IOException {
        if(httpRequest.ok()) {
            return MAPPER.readValue(httpRequest.body(), valueType);
        }
        return null;
    }
}
