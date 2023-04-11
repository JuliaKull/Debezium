package com.knits.enterprise.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.company.*;
import com.knits.enterprise.model.enums.Gender;
import com.knits.enterprise.model.location.Location;

import javax.persistence.Entity;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class EmployeeDeserializer extends JsonDeserializer<Employee> {


    @Override
    public Employee deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Employee employee = new Employee();

        employee.setFirstName(node.get("first_name").asText());
        employee.setLastName(node.get("last_name").asText());
        employee.setEmail(node.get("email").asText());
        employee.setBirthDate(deserializeLocalDate(node, "birth_date"));
        employee.setGender(Gender.valueOf(node.get("gender").asText()));
        employee.setStartDate(deserializeLocalDate(node, "start_date"));
        employee.setEndDate(deserializeLocalDate(node, "end_date"));
        employee.setCompanyPhone(node.get("company_phone").asText());
        employee.setCompanyMobileNumber(node.get("company_mobile_phone").asText());
        employee.setBusinessUnit(deserializeNestedObject(node, "businessUnit", BusinessUnit.class));
        employee.setOrganization(deserializeNestedObject(node, "organization", Organization.class));
        employee.setOffice(deserializeNestedObject(node, "office", Location.class));
        employee.setJobTitle(deserializeNestedObject(node, "jobTitle", JobTitle.class));
        employee.setDepartment(deserializeNestedObject(node, "department", Department.class));
        employee.setDivision(deserializeNestedObject(node, "division", Division.class));
        employee.setCostCenter(deserializeNestedObject(node, "costCenter", CostCenter.class));
        employee.setTeam(deserializeNestedObject(node, "team", Team.class));

        return employee;
    }

    private <T> T deserializeNestedObject(JsonNode node, String fieldName, Class<T> nestedClass) throws JsonProcessingException {
        JsonNode nestedNode = node.get(fieldName);
        return new ObjectMapper().treeToValue(nestedNode, nestedClass);
    }

    private LocalDate deserializeLocalDate(JsonNode node, String fieldName) {
        LocalDate date = LocalDate.ofEpochDay(node.get(fieldName).asLong());
        return LocalDate.parse(date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }
}



