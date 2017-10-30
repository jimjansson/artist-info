package com.jimjansson.artistinfo.external.musicbrainz.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jim on 2016-10-04.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MusicBrainzResponse {

    private static final String WIKIPEDIA_TYPE_STRING = "wikipedia";

    private String id;
    private String name;
    private List<Relation> relations;

    @JsonProperty("release-groups")
    private List<ReleaseGroup> releaseGroups;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public void setReleaseGroups(List<ReleaseGroup> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }

    public List<ReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    @JsonIgnore
    public String getWikipediaTitle() {
        if(getRelations() != null) {
            Optional<Relation> wikiRelation = getRelations().stream()
                    .filter(relation -> WIKIPEDIA_TYPE_STRING.equals(relation.getType())).findFirst();
            if(wikiRelation.isPresent()) {
                return getWikipediaTitleFromUrl(wikiRelation.get().getUrl().getResource());
            }
        }
        return null;
    }

    @JsonIgnore
    private static String getWikipediaTitleFromUrl(String wikipediaUrl) {
        if(wikipediaUrl != null) {
            return wikipediaUrl.substring(wikipediaUrl.lastIndexOf("/") + 1);
        }
        return null;
    }

    /**
     * Created by Jim on 2016-10-04.
     */
    @JsonIgnoreProperties(ignoreUnknown=true)
    public static class Relation {

        @JsonIgnore
        private static final String RESOURCE = "resource";

        private String type;
        private Url url;

        String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(Url url) {
            this.url = url;
        }

        Url getUrl() {
            return url;
        }
    }
}
