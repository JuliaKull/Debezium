package com.knits.enterprise.model.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Table(name = "business_unit")
public class BusinessUnit extends AbstractOrganizationStructure implements Serializable{

    @OneToMany(mappedBy = "businessUnit")
    private List<Employee> employees;
}
