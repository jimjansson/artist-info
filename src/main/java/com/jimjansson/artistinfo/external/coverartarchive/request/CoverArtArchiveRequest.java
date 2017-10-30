package com.jimjansson.artistinfo.external.coverartarchive.request;

import com.github.kevinsawicki.http.HttpRequest;
import com.jimjansson.artistinfo.external.RestRequest;
import com.jimjansson.artistinfo.external.coverartarchive.response.ReleaseGroupResponse;

import java.io.IOException;

public class CoverArtArchiveRequest extends RestRequest {

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
}
