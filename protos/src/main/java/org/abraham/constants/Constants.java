package org.abraham.constants;

import com.google.common.collect.ImmutableSet;
import io.grpc.Context;
import io.grpc.Metadata;

import java.util.Set;

public class Constants {
    public static final Metadata.Key<String> AUTHORIZATION_KEY = Metadata.Key.of("Authorization",  Metadata.ASCII_STRING_MARSHALLER);
    public static final Context.Key<String> USER_ID = Context.key("USER_ID");
    public static final Context.Key<String> USER_ROLE = Context.key("USER_ROLE");
    public static final Context.Key<String> USER_EMAIL = Context.key("USER_EMAIL");
    public static final Set<String> PUBLIC_METHODS = ImmutableSet.of("loginUser", "registerUser");
    public static final String BEARER = "Bearer ";
    public static final String JWT_TOKEN = "Jwt";
    public static final String AUTHORIZATION_HEADER = "Authorization";
}
