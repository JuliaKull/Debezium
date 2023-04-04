package com.knits.enterprise.model.company;

import com.knits.enterprise.model.common.AbstractActiveEntity;
import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.enums.Gender;
import com.knits.enterprise.model.location.Location;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Table(name = "employee")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Employee extends AbstractActiveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "email", length = 254, unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "company_phone", nullable = false)
    private String companyPhone;

    @Column(name = "company_mobile_phone", nullable = false)
    private String companyMobileNumber;

    @ManyToOne
    @JoinColumn(name = "business_unit_id", referencedColumnName = "id")
    private BusinessUnit businessUnit;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private Location office;

    @ManyToOne
    @JoinColumn(name = "job_title_id", referencedColumnName = "id")
    private JobTitle jobTitle;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "division_id", referencedColumnName = "id")
    private Division division;
    @ManyToOne
    @JoinColumn(name = "cost_center_id", referencedColumnName = "id")
    private CostCenter costCenter;
    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solid_line_manager_id", referencedColumnName = "id")
    private Employee solidLineManager;

    @ManyToMany(mappedBy = "employees",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Group> groups;


}


