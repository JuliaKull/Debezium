package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.data.common.OrganizationDto;
import com.knits.enterprise.dto.data.company.*;
import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.mapper.common.*;
import com.knits.enterprise.mapper.company.*;
import com.knits.enterprise.mapper.location.LocationMapper;
import com.knits.enterprise.mapper.location.LocationMapperImpl;
import com.knits.enterprise.mocks.dto.company.EmployeeDtoMock;
import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.company.*;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.repository.company.*;
import com.knits.enterprise.repository.location.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BusinessUnitRepository businessUnitRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private JobTitleRepository jobTitleRepository;

    @Mock
    private CostCenterRepository costCenterRepository;

    @Mock
    private DivisionRepository divisionRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private OrganizationRepository organizationRepository;
    @Spy
    private EmployeeMapper employeeMapper= new EmployeeMapperImpl();

    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    private void iniMapperDependencies(){

        OrganizationMapper organizationMapper= new OrganizationMapperImpl();
        BusinessUnitMapper businessUnitMapper = new BusinessUnitMapperImpl();
        CostCenterMapper costCenterMapper = new CostCenterMapperImpl();
        GroupMapper groupMapper = new GroupMapperImpl();
        JobTitleMapper jobTitleMapper = new JobTitleMapperImpl();
        TeamMapper teamMapper = new TeamMapperImpl();
        DivisionMapper divisionMapper = new DivisionMapperImpl();
        LocationMapper locationMapper = new LocationMapperImpl();
        DepartmentMapper departmentMapper = new DepartmentMapperImpl();
        AddressMapper addressMapper = new AddressMapperImpl();
        CountryMapper countryMapper = new CountryMapperImpl();

        ReflectionTestUtils.setField(addressMapper,"countryMapper",countryMapper);
        ReflectionTestUtils.setField(locationMapper,"addressMapper",addressMapper);

        ReflectionTestUtils.setField(employeeMapper,"organizationMapper",organizationMapper);
        ReflectionTestUtils.setField(employeeMapper, "divisionMapper", divisionMapper);
        ReflectionTestUtils.setField(employeeMapper, "businessUnitMapper", businessUnitMapper);
        ReflectionTestUtils.setField(employeeMapper, "costCenterMapper", costCenterMapper);
        ReflectionTestUtils.setField(employeeMapper, "groupMapper", groupMapper);
        ReflectionTestUtils.setField(employeeMapper, "jobTitleMapper", jobTitleMapper);
        ReflectionTestUtils.setField(employeeMapper, "teamMapper", teamMapper);
        ReflectionTestUtils.setField(employeeMapper, "departmentMapper", departmentMapper);
        ReflectionTestUtils.setField(employeeMapper, "locationMapper", locationMapper);

    }

    @Test
    @DisplayName("Save new employee Success")
    void saveNewEmployee() {
        final Long mockId=1L;
        final Long employeeGeneratedId=1L;

        EmployeeDto toSaveDto = EmployeeDtoMock.shallowEmployeeDto(null);

        toSaveDto.setBusinessUnit(BusinessUnitDto.builder().id(mockId).build());
        toSaveDto.setDepartment(DepartmentDto.builder().id( mockId).build());
        toSaveDto.setBusinessUnit(BusinessUnitDto.builder().id( mockId).build());
        toSaveDto.setCostCenter(CostCenterDto.builder().id(mockId).build());
        toSaveDto.setDivision(DivisionDto.builder().id(mockId).build());
        toSaveDto.setJobTitle(JobTitleDto.builder().id(mockId).build());
        toSaveDto.setDepartment(DepartmentDto.builder().id(mockId).build());
        toSaveDto.setOffice(LocationDto.builder().id(mockId).build());
        toSaveDto.setOrganization(OrganizationDto.builder().id(mockId).build());

        when(businessUnitRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new BusinessUnit()));
        when(departmentRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new Department()));
        when(divisionRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new Division()));
        when(jobTitleRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new JobTitle()));
        when(costCenterRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new CostCenter()));
        when(locationRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new Location()));
        when(organizationRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new Organization()));

        when(employeeRepository.save(Mockito.any(Employee.class)))
                .thenAnswer(invocation -> {
                    Employee employee = (Employee)invocation.getArgument(0);
                    employee.setId(employeeGeneratedId);
                    return employee;
                });

        EmployeeDto savedDto = employeeService.create(toSaveDto);

        verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee toSaveEntity = employeeArgumentCaptor.getValue();

        verify(businessUnitRepository, times(1)).findById(mockId);
        verify(costCenterRepository, times(1)).findById(mockId);
        verify(divisionRepository, times(1)).findById(mockId);
        verify(jobTitleRepository, times(1)).findById(mockId);
        verify(departmentRepository, times(1)).findById(mockId);
        verify(locationRepository, times(1)).findById(mockId);
        verify(organizationRepository, times(1)).findById(mockId);

        verify(employeeMapper, times(1)).toDto(toSaveEntity);
        verify(employeeMapper, times(1)).toEntity(toSaveDto);
        verify(employeeRepository, times(1)).save(toSaveEntity);


        assertThat(savedDto.getId()).isEqualTo(employeeGeneratedId);
        assertThat(savedDto.getFirstName()).isEqualTo(toSaveDto.getFirstName());
        assertThat(savedDto.getLastName()).isEqualTo(toSaveDto.getLastName());
        assertThat(savedDto.getEmail()).isEqualTo(toSaveDto.getEmail());
        assertThat(savedDto.getBirthDate()).isEqualTo(toSaveDto.getBirthDate());
        assertThat(savedDto.getGender()).isEqualTo(toSaveDto.getGender());
        assertThat(savedDto.getStartDate()).isEqualTo(toSaveDto.getStartDate());
        assertThat(savedDto.getEndDate()).isEqualTo(toSaveDto.getEndDate());
        assertThat(savedDto.getCompanyPhone()).isEqualTo(toSaveDto.getCompanyPhone());
        assertThat(savedDto.getCompanyMobileNumber()).isEqualTo(toSaveDto.getCompanyMobileNumber());
    }

    @Test
    @DisplayName("Save employee Fail: Business Unit does not exist")
    void saveNewExceptionBusinessUnit() {

    }

    @Test
    @DisplayName("Save employee Fail: Office doesnt exist")
    void saveNewExceptionOffice() {

    }

    @Test
    @DisplayName("Save employee Fail: JobTitle doesnt exist")
    void saveNewExceptionJobTitle() {

    }

    @Test
    @DisplayName("Save employee Fail: Organization doesnt exist")
    void saveNewExceptionOrganization() {

    }

    @Test
    @DisplayName("Save employee Fail: Division doesnt exist")
    void saveNewExceptionDivision() {

    }

    @Test
    @DisplayName("Save employee Fail: Department doesnt exist")
    void saveNewExceptionDepartment() {

    }


    @Test
    @DisplayName("find ById employee Success")
    void findEmployeeById() {

    }

    @Test
    @DisplayName("find ById employee Not Found")
    void findEmployeeByIdNotFound() {

    }


    @Test
    @DisplayName("partial update employee Success")
    void partialEmployee() {

    }

    @Test
    @DisplayName("delete employee Success")
    void deleteEmployee() {
    }
}