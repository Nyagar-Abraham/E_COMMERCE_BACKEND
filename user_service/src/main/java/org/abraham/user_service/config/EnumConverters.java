package org.abraham.user_service.config;

import org.abraham.user_service.dto.UserStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.util.List;

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

