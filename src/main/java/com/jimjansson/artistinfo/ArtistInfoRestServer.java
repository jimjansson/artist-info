package com.jimjansson.artistinfo;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * ArtistInfoRestServer class.
 *
 */
public class ArtistInfoRestServer {
    // Base URI the Grizzly HTTP server will listen on
    public static final URI BASE_URI  = UriBuilder.fromUri("http://localhost/").port(8081).build();

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.jimjansson.artistinfo package
        final ResourceConfig rc = new ResourceConfig().packages("com.jimjansson.artistinfo");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    /**
     * ArtistInfoRestServer method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Artist info REST server started at URI: %s " +
                "\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}
