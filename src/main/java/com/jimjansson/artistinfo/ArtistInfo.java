package com.jimjansson.artistinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.jimjansson.artistinfo.coverartarchive.ReleaseGroupResponse;
import com.jimjansson.artistinfo.musicbrainz.MusicBrainzResponse;
import com.jimjansson.artistinfo.musicbrainz.Relation;
import com.jimjansson.artistinfo.musicbrainz.ReleaseGroup;
import com.jimjansson.artistinfo.response.Album;
import com.jimjansson.artistinfo.response.ArtistInfoResponse;
import com.jimjansson.artistinfo.wikipedia.WikipediaResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/artistinfo/{mbid}")
public class ArtistInfo {

    private static final String MUSIC_BRAINZ_REQUEST_TEMPLATE = "http://musicbrainz.org/ws/2/artist/%s?" +
            "&fmt=json" +
            "&inc=url-rels+release-groups";

    private static final String COVER_ART_ARCHIVE_REQUEST_TEMPLATE = "http://coverartarchive.org/release-group/%s";

    private static final String WIKIPEDIA_REQUEST_TEMPLATE = "https://en.wikipedia.org/w/api.php?" +
            "action=query&" +
            "format=json&" +
            "prop=extracts&exintro=true&redirects=true&titles=%s";

    private static final String WIKIPEDIA_TYPE_STRING = "wikipedia";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArtistInfoResponse getArtistInfo(@PathParam("mbid") String mbid) {
        try {
            MusicBrainzResponse musicBrainzResponse = getMusicBrainzResponse(getMusicBrainzHttpRequest(mbid));
            String wikipediaDescription = getWikipediaDescription(musicBrainzResponse);
            List<Album> albumList = computeAlbumList(musicBrainzResponse);
            return new ArtistInfoResponse(mbid, wikipediaDescription, albumList);
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    private String getWikipediaDescription(MusicBrainzResponse musicBrainzResponse) {
        String wikipediaTitle = extractWikipediaTitleFromMusicBrainzResponse(musicBrainzResponse);
        try {
            WikipediaResponse wikipediaResponse = getWikipediaResponse(getWikipediaHttpRequest(wikipediaTitle));
            return wikipediaResponse.getQuery().getPages().values().stream().findFirst().get().getExtract();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Album> computeAlbumList(MusicBrainzResponse musicBrainzResponse) {
        return musicBrainzResponse.getReleaseGroups().parallelStream()
                .map(this::toAlbum).collect(Collectors.toList());
    }

    private MusicBrainzResponse getMusicBrainzResponse(String request) throws IOException {
        HttpRequest httpRequest = HttpRequest.get(request);
        if(httpRequest.ok()) {
            return MAPPER.readValue(httpRequest.body(), MusicBrainzResponse.class);
        }
        return null;
    }

    private String getMusicBrainzHttpRequest(String mbid) {
        return String.format(MUSIC_BRAINZ_REQUEST_TEMPLATE, mbid);
    }

    private WikipediaResponse getWikipediaResponse(String request) throws IOException {
        HttpRequest httpRequest = HttpRequest.get(request);
        if(httpRequest.ok()) {
            return MAPPER.readValue(httpRequest.body(), WikipediaResponse.class);
        }
        return null;
    }

    private String getWikipediaHttpRequest(String title) {
        return String.format(WIKIPEDIA_REQUEST_TEMPLATE, title);
    }

    private String extractWikipediaTitleFromMusicBrainzResponse(MusicBrainzResponse musicBrainzResponse) {
        Optional<Relation> wikiRelation = musicBrainzResponse.getRelations().stream()
                .filter(relation -> WIKIPEDIA_TYPE_STRING.equals(relation.getType())).findFirst();
        if(wikiRelation.isPresent()) {
            return getWikipediaTitleFromUrl(wikiRelation.get().getUrl().getResource());
        }
        return null;
    }

    private String getWikipediaTitleFromUrl(String wikipediaUrl) {
        if(wikipediaUrl != null) {
            return wikipediaUrl.substring(wikipediaUrl.lastIndexOf("/") + 1);
        }
        return null;
    }

    private ReleaseGroupResponse getReleaseGroupResponse(String request) throws IOException {
        HttpRequest httpRequest = HttpRequest.get(request);
        if(httpRequest.ok()) {
            return MAPPER.readValue(httpRequest.body(), ReleaseGroupResponse.class);
        }
        return null;
    }

    private String getCoverArtArchiveReleaseGroupsHttpRequest(String releaseGroupMbid) {
        return String.format(COVER_ART_ARCHIVE_REQUEST_TEMPLATE, releaseGroupMbid);
    }

    private Album toAlbum(ReleaseGroup releaseGroup) {
        String imageUrl = null;
        try {
            ReleaseGroupResponse releaseGroupResponse =
                    getReleaseGroupResponse(getCoverArtArchiveReleaseGroupsHttpRequest(releaseGroup.getId()));
            if(releaseGroupResponse != null) {
                imageUrl = releaseGroupResponse.getImages().get(0).getImage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Album(releaseGroup.getId(), releaseGroup.getTitle(), imageUrl);
    }

}
