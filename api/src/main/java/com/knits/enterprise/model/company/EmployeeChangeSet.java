package com.knits.enterprise.model.company;

import com.knits.enterprise.model.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employee_change_set")
public class EmployeeChangeSet extends AbstractEntity {

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "previous_value")
    private String previousValue;

    @Column(name = "new_value")
    private String newValue;


}
