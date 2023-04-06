package com.knits.enterprise.debezium;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class DebeziumConnectorConfig {

    @Bean
    public io.debezium.config.Configuration employeeConnector() {
        return io.debezium.config.Configuration.create()
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
                .with("offset.storage",  "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "/path/cdc/offset/student-offset.dat")
                .with("offset.flush.interval.ms", 60000)
                .with("name", "student-postgres-connector")
                .with("database.server.name", "employee")
                .with("database.hostname", "localhost")
                .with("database.port", "5432")
                .with("database.user", "postgres")
                .with("database.password", System.getenv("POSTGRESQL_PASS"))
                .with("topic.prefix", "my-app-connector")
                .with("database.dbname", "Debezium")
                .with("table.whitelist", "public.employee").build();
    }

}
