package com.jimjansson.artistinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.jimjansson.artistinfo.musicbrainz.MusicBrainzResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/artistinfo/{mbid}")
public class ArtistInfo {

    private static final String MUSIC_BRAINZ_REQUEST_TEMPLATE = "http://musicbrainz.org/ws/2/artist/%s?" +
            "&fmt=json" +
            "&inc=url-rels+release-groups";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getArtistInfo(@PathParam("mbid") String mbid) {
        try {
            MusicBrainzResponse musicBrainzResponse = getMusicBrainzResponse(getMusicBrainzHttpRequest(mbid));
            return musicBrainzResponse.getReleaseGroups().stream().map(r -> r.getTitle() + " - " + r.getId()).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return e.toString();
        }
    }

    private MusicBrainzResponse getMusicBrainzResponse(String request) throws IOException{
        return MAPPER.readValue(HttpRequest.get(request).body(), MusicBrainzResponse.class);
    }

    private String getMusicBrainzHttpRequest(String mbid) {
        return String.format(MUSIC_BRAINZ_REQUEST_TEMPLATE, mbid);
    }
}
