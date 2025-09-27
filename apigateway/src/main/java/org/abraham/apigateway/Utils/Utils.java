package org.abraham.apigateway.Utils;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Utils {
    public static OffsetDateTime offsetDateTimeFromTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC); // or systemDefault()
    }
}
