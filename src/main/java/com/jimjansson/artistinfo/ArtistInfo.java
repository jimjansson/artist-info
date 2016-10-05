package com.jimjansson.artistinfo;

import com.jimjansson.artistinfo.coverartarchive.ReleaseGroupResponse;
import com.jimjansson.artistinfo.musicbrainz.MusicBrainzResponse;
import com.jimjansson.artistinfo.musicbrainz.ReleaseGroup;
import com.jimjansson.artistinfo.response.Album;
import com.jimjansson.artistinfo.response.ArtistInfoResponse;
import com.jimjansson.artistinfo.util.HttpRequestUtil;
import com.jimjansson.artistinfo.wikipedia.WikipediaResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/artistinfo/{mbid}")
public class ArtistInfo {

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
            MusicBrainzResponse musicBrainzResponse = HttpRequestUtil.getMusicBrainzResponse(mbid);
            String wikipediaDescription = getWikipediaDescription(musicBrainzResponse);
            List<Album> albumList = computeAlbumList(musicBrainzResponse);
            return new ArtistInfoResponse(mbid, wikipediaDescription, albumList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getWikipediaDescription(MusicBrainzResponse musicBrainzResponse) {
        try {
            WikipediaResponse wikipediaResponse = HttpRequestUtil
                    .getWikipediaResponse(musicBrainzResponse.getWikipediaTitle());
            if(wikipediaResponse != null) {
                return wikipediaResponse.getDescription();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Album> computeAlbumList(MusicBrainzResponse musicBrainzResponse) {
        return musicBrainzResponse.getReleaseGroups().parallelStream()
                .map(this::toAlbum).collect(Collectors.toList());
    }

    private Album toAlbum(ReleaseGroup releaseGroup) {
        String imageUrl = null;
        try {
            ReleaseGroupResponse releaseGroupResponse = HttpRequestUtil.getReleaseGroupResponse(releaseGroup.getId());
            if(releaseGroupResponse != null) {
                imageUrl = releaseGroupResponse.getImages().get(0).getImage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Album(releaseGroup.getId(), releaseGroup.getTitle(), imageUrl);
    }
}
