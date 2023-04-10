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
                .with("offset.storage.file.filename", "C:/Users/Yuliya/AppData/Local/Temp/connect.offsets")
                .with("offset.flush.interval.ms", 60000)
                .with("name", "postgres-connector")
                .with("database.server.name", "employee")
                .with("database.hostname", "localhost")
                .with("database.port", "5432")
                .with("database.user", "postgres")
                .with("database.password", System.getenv("POSTGRESQL_PASS"))
                .with("topic.prefix", "debezium-connector")
                .with("database.dbname", "postgres")
                .with("plugin.name", "pgoutput")
                .with("table.whitelist", "public.employee")
                .with("transforms", "unwrap")
                .with("transforms.unwrap.type", "io.debezium.transforms.ExtractNewRecordState")
                .with("transforms.unwrap.drop.tombstones", "false")
                .with("transforms.unwrap.delete.handling.mode", "rewrite")
                .with("transforms.unwrap.add.fields.start_date.field", "startDate")
                .with("transforms.unwrap.add.fields.start_date.type", "io.debezium.time.Date")
                .with("transforms.unwrap.add.fields.birth_date.field", "birthDate")
                .with("transforms.unwrap.add.fields.birth_date.type", "io.debezium.time.Date")
                .with("transforms.unwrap.add.fields.end_date.field", "endDate")
                .with("transforms.unwrap.add.fields.end_date.type", "io.debezium.time.Date")
                .with("time.precision.mode", "connect")
                .build();
    }

}
