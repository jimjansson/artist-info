package com.jimjansson.artistinfo.coverartarchive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Jim on 2016-10-05.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Image {

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
