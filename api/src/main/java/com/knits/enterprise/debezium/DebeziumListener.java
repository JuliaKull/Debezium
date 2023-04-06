package com.knits.enterprise.debezium;

import com.knits.enterprise.service.company.EmployeeService;
import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static io.debezium.data.Envelope.FieldName.*;
import static io.debezium.data.Envelope.Operation;
import static java.util.stream.Collectors.toMap;


@Slf4j
@Component
public class DebeziumListener {

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final EmployeeService employeeService;

    private final EmbeddedEngine debeziumEngine;

    public DebeziumListener(Configuration employeeConnectorConfiguration, EmployeeService employeeService) {

        this.debeziumEngine = EmbeddedEngine
                .create()
                .using(employeeConnectorConfiguration)
                .notifying(this::handleChangeEvent)
                .build();

        this.employeeService = employeeService;
    }

    private void handleChangeEvent(SourceRecord sourceRecord) {
        Struct sourceRecordValue = (Struct) sourceRecord.value();

        if (sourceRecordValue != null) {
            Operation operation = Operation.forCode((String) sourceRecordValue.get(OPERATION));

            //Only if this is a transactional operation.
            if (operation != Operation.READ) {

                Map<String, Object> message;
                String record = AFTER; //For Update & Insert operations.

                if (operation == Operation.DELETE) {
                    record = BEFORE; //For Delete operations.
                }

                //Build a map with all row data received.
                Struct struct = (Struct) sourceRecordValue.get(record);
                message = struct.schema().fields().stream()
                        .map(Field::name)
                        .filter(fieldName -> struct.get(fieldName) != null)
                        .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                        .collect(toMap(Pair::getKey, Pair::getValue));

                //Call the service to handle the data change.
                this.employeeService.replicateData(message, operation);
                log.info("Data Changed: {} with Operation: {}", message, operation.name());
            }
        }
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }

}