package org.abraham.apigateway.auth;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import org.abraham.constants.Constants;

import java.util.concurrent.Executor;

public class Credentials extends CallCredentials {
    private static final String TOKEN_FORMAT = "%s %s";
    private final String jwt;

    public Credentials(String jwt){
        this.jwt = jwt;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        var metadata = new Metadata();
        metadata.put(Constants.AUTHORIZATION_KEY,TOKEN_FORMAT.formatted(Constants.BEARER , jwt));
        metadataApplier.apply(metadata);
    }
}
