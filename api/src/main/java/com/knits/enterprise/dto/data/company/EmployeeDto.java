package com.knits.enterprise.dto.data.company;

import com.knits.enterprise.dto.data.common.OrganizationDto;
import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.model.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
public class EmployeeDto extends AbstractActiveDto {

    private Long id;
    @NotNull()
    private String firstName;
    @NotNull()
    private String lastName;

    private String email;

    @NotNull()
    private String birthDate;

    @NotNull()
    private Gender gender;

    @NotNull()
    private String startDate;

    @NotNull()
    private String endDate;

    @NotNull()
    private String companyPhone;

    @NotNull()
    private String companyMobileNumber;

    @NotNull()
    private String role;

    @NotNull()
    private OrganizationDto organization;

    @NotNull()
    private LocationDto office;

    @NotNull()
    private BusinessUnitDto businessUnit;

    @NotNull()
    private JobTitleDto jobTitle;

    @NotNull()
    private DepartmentDto department;

    @NotNull()
    private DivisionDto division;

    @NotNull()
    private List<GroupDto> groups;

    @NotNull()
    private TeamDto team;

    @NotNull()
    private CostCenterDto costCenter;

    @NotNull()
    private EmployeeDto solidLineManager;
}

