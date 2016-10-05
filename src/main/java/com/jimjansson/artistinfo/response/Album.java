package com.jimjansson.artistinfo.response;

/**
 * Created by Jim on 2016-10-05.
 */
public class Album {

    private String id;
    private String title;
    private String image;

    public Album() {

    }

    public Album(String id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return String.format("ID: %s\nTitle: %s\nImage: %s", id, title, image);
    }
}
