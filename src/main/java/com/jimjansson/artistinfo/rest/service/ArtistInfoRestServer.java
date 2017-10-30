package com.jimjansson.artistinfo.rest.service;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;


public class ArtistInfoRestServer {
    // Base URI that the Grizzly HTTP server will listen on
    public static final URI BASE_URI  = UriBuilder.fromUri("http://localhost/").port(8081).build();

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.jimjansson.artistinfo package
        final ResourceConfig rc = new ResourceConfig(ArtistInfo.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    /**
     * ArtistInfoRestServer main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Artist Info REST server started at URI: %s", BASE_URI));
        System.out.println("Hit enter to stop it...");
        System.in.read();
        server.shutdownNow();
    }
}

