package com.jimjansson.artistinfo.external.coverartarchive.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jimjansson.artistinfo.external.coverartarchive.response.Image;

import java.util.List;

/**
 * Created by Jim on 2016-10-05.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ReleaseGroupResponse {

    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
