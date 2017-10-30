package com.jimjansson.artistinfo.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;

import java.io.IOException;

public abstract class RestRequest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    protected <T> T unmarshall(HttpRequest httpRequest, Class<T> valueType) throws IOException {
        if(httpRequest.ok()) {
            return MAPPER.readValue(httpRequest.body(), valueType);
        }
        return null;
    }
}
