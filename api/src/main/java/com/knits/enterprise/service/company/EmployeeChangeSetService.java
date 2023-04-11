package com.knits.enterprise.service.company;

import com.fasterxml.jackson.databind.JsonNode;
import com.knits.enterprise.model.company.EmployeeChangeSet;
import com.knits.enterprise.repository.company.EmployeeChangeSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.SequenceGenerator;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class EmployeeChangeSetService {

    private final EmployeeChangeSetRepository repository;

//    public void processChangeData(DebeziumChangeEvent<JsonNode, JsonNode> changeEvent) {
//        String tableName = changeEvent.getMetadata().getId().getTable();
//        JsonNode before = changeEvent.getPayload().getBefore();
//        JsonNode after = changeEvent.getPayload().getAfter();
//
//        if (before != null && after != null) {
//            Long employeeId = after.get("id").asLong();
//
//            Iterator<String> fieldNames = after.fieldNames();
//            while (fieldNames.hasNext()) {
//                String fieldName = fieldNames.next();
//                JsonNode beforeValue = before.get(fieldName);
//                JsonNode afterValue = after.get(fieldName);
//                if (!beforeValue.equals(afterValue)) {
//                    String previousValue = beforeValue.asText();
//                    String newValue = afterValue.asText();
//
//                    EmployeeChangeSet changeSet = new EmployeeChangeSet(employeeId, fieldName, previousValue, newValue);
//                    repository.save(changeSet);
//                }
//            }
//        }
//    }
}
