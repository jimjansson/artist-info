package com.jimjansson.artistinfo.rest.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jimjansson.artistinfo.external.coverartarchive.ReleaseGroupResponse;
import com.jimjansson.artistinfo.external.coverartarchive.request.CoverArtArchiveRequest;
import com.jimjansson.artistinfo.external.musicbrainz.MusicBrainzResponse;
import com.jimjansson.artistinfo.external.musicbrainz.ReleaseGroup;
import com.jimjansson.artistinfo.rest.response.Album;
import com.jimjansson.artistinfo.rest.response.ArtistInfoResponse;
import com.jimjansson.artistinfo.util.HttpRequestUtil;
import com.jimjansson.artistinfo.external.wikipedia.WikipediaResponse;
import org.glassfish.jersey.server.ManagedAsync;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Root resource (exposed at "artistinfo" path)
 */
@Path("/artistinfo/{mbid}")
public class ArtistInfo {

    /**
     * A simple cache with max-size 100000
     */
    private static final Cache<String, ArtistInfoResponse> simpleCache = CacheBuilder.
            newBuilder().maximumSize(100000).build();

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Method handling HTTP GET requests. The returned object will be sent, when ready,
     * to the client as "application/json" media type.
     *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getArtistInfo(@Suspended final AsyncResponse asyncResponse,
                              @PathParam("mbid") String mbid) {
        ArtistInfoResponse artistInfoResponse = simpleCache.getIfPresent(mbid);
        if(artistInfoResponse != null) {
            asyncResponse.resume(artistInfoResponse);
        } else {
            try {
                MusicBrainzResponse musicBrainzResponse = HttpRequestUtil.getMusicBrainzResponse(mbid);
                Future<String> wikiDesc = executorService.submit(getWikipediaDescriptionCallable(musicBrainzResponse));
                Future<List<Album>> albumList = executorService.submit(getAlbumListCallable(musicBrainzResponse));
                artistInfoResponse = new ArtistInfoResponse(mbid, wikiDesc.get(), albumList.get());
                simpleCache.put(mbid, artistInfoResponse);
                asyncResponse.resume(artistInfoResponse);
            } catch (IOException | InterruptedException | ExecutionException e) {
                asyncResponse.resume(e);
            }
        }
    }

    private Callable<List<Album>> getAlbumListCallable(MusicBrainzResponse musicBrainzResponse) {
        return () -> computeAlbumList(musicBrainzResponse);
    }

    private Callable<String> getWikipediaDescriptionCallable(MusicBrainzResponse musicBrainzResponse) {
        return () -> getWikipediaDescription(musicBrainzResponse);
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
            ReleaseGroupResponse releaseGroupResponse = CoverArtArchiveRequest
                    .createCoverArtArchiveRequest(releaseGroup.getId())
                    .getReleaseGroupResponse();
            if(releaseGroupResponse != null) {
                imageUrl = releaseGroupResponse.getImages().get(0).getImage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Album(releaseGroup.getId(), releaseGroup.getTitle(), imageUrl);
    }
}
