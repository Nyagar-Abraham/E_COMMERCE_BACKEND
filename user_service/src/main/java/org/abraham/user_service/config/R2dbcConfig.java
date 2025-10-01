package org.abraham.user_service.config;



import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.codec.EnumCodec;
import io.r2dbc.spi.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.abraham.commondtos.AddressType;
import org.abraham.commondtos.UserRoles;
import org.abraham.commondtos.UserStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@AllArgsConstructor
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private R2dbcProperties r2dbcProperties;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        Map<String, String> options = new HashMap<>();
        options.put("lock_timeout", "10s");

        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5434)  // optional, defaults to 5432
                .username(r2dbcProperties.getUsername())
                .password(r2dbcProperties.getPassword())
                .database("user_service_db")  // optional
                .options(options) // optional
                .codecRegistrar(EnumCodec.builder().withEnum("user_status_enum", UserStatus.class).build())
                .codecRegistrar(EnumCodec.builder().withEnum("address_type_enum", AddressType.class).build())
                .codecRegistrar(EnumCodec.builder().withEnum("user_roles", UserRoles.class).build())
                .build());
    }

    @Override
    protected List<Object> getCustomConverters() {
        return List.of(
                new EnumConverters.UserStatusToStringConverter(),
                new EnumConverters.StringToUserStatusConverter()
        );
    }

}
