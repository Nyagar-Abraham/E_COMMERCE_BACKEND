package org.abraham.user_service.config.kafka.serializers;

import org.abraham.messages.UserCreatedMessage;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.checkerframework.checker.units.qual.K;

public class UserCreatedMessageSerializer implements Serializer<UserCreatedMessage> {
    @Override
    public byte[] serialize(String s, UserCreatedMessage message) {
        return message.toByteArray();
    }
}
