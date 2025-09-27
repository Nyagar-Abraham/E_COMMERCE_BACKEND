package org.abraham.user_service.utils;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Utils {
    public static Timestamp convertToTimestamp(LocalDateTime ldt) {
        return Timestamp.newBuilder().setSeconds(Instant.from(ldt.atZone(ZoneId.systemDefault()).toInstant()).getEpochSecond()).build();
    }
}
