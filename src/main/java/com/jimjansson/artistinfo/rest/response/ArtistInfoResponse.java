package com.jimjansson.artistinfo.rest.response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jim on 2016-10-05.
 */
public class ArtistInfoResponse {

    private String mbid;
    private String description;
    private List<Album> albums;

    public ArtistInfoResponse() {

    }

    public ArtistInfoResponse(String mbid, String description, List<Album> albums) {
        this.mbid = mbid;
        this.description = description;
        this.albums = albums;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return String.format("mbid: %s\n" +
                "Description: %s\n" +
                "Albums: %s", mbid, description,
                albums.stream().map(Album::toString).collect(Collectors.joining("\n")));
    }
}
