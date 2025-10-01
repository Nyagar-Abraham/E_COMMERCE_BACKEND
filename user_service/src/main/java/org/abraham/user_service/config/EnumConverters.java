package org.abraham.user_service.config;

import org.abraham.commondtos.UserStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

@Configuration
public class EnumConverters {

    @WritingConverter
    public static class UserStatusToStringConverter implements Converter<UserStatus, String> {
        @Override
        public String convert(UserStatus source) {
            return source.name();
        }
    }

    @ReadingConverter
    public static class StringToUserStatusConverter implements Converter<String, UserStatus> {
        @Override
        public UserStatus convert(String source) {
            return UserStatus.valueOf(source);
        }
    }

}

