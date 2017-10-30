package com.jimjansson.artistinfo;

import com.jimjansson.artistinfo.response.ArtistInfoResponse;
import com.jimjansson.artistinfo.service.ArtistInfoRestServer;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArtistInfoTest {

    private static String MBID_QUEEN = "0383dadf-2a4e-4d10-a46a-e9e041da8eb3";

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = ArtistInfoRestServer.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(ArtistInfoRestServer.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Test to see that the server responds for the artist Queen
     */
    @Test
    public void testGetArtistInfoQueen() {
        ArtistInfoResponse artistInfoResponse = target.path("artistinfo/"+MBID_QUEEN).request().get(ArtistInfoResponse.class);
        assertEquals(MBID_QUEEN, artistInfoResponse.getMbid());
        assertNotNull(artistInfoResponse.getDescription());
        assertNotNull(artistInfoResponse.getAlbums());
    }
}
