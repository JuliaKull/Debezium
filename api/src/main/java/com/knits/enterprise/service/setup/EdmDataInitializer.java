package com.knits.enterprise.service.setup;

import com.knits.enterprise.dto.data.security.RoleDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.model.common.Address;
import com.knits.enterprise.model.common.Contact;
import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.company.*;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.repository.common.AddressRepository;
import com.knits.enterprise.repository.common.ContactRepository;
import com.knits.enterprise.repository.company.*;
import com.knits.enterprise.repository.location.LocationRepository;
import com.knits.enterprise.repository.security.RoleRepository;
import com.knits.enterprise.repository.security.UserRepository;
import com.knits.enterprise.service.security.RoleService;
import com.knits.enterprise.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class EdmDataInitializer {



    private static final String ADMIN_ROLE="Admin";
    private static final String USERNAME="stefanofiorenza";


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Autowired
    private CostCenterRepository costCenterRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private LocationRepository locationRepository;

    /**
     * This assumes that
     *  1) Database structure has been created already
     *  2) Data in tables have been entered manually or with liquibase
     *  3) Only Connection between tables are missing
     */
    public void setupDatabase(){

        log.info("setupDatabase Started. This might take some minutes, please wait....");

        if(userRepository.findOneByUsername(USERNAME).isPresent()){
            log.info("{} already existing. DB initialization will be skipped",USERNAME);
            log.info("setupDatabase Terminated.");
            return;
        };

        User adminUser =createAdminUser();

        List<Employee> employees =employeeRepository.findAllActive();

        setupOrganizations(adminUser);
        setupLocations(adminUser);
        setupBusinessUnits(employees,adminUser);
        setupDivisions(employees,adminUser);
        setupJobTitles(employees,adminUser);
        setupDepartments(employees,adminUser);
        setupCostCenters(employees,adminUser);
        setupGroups(employees,adminUser);
        setupTeams(employees,adminUser);
        setupOrganizations(employees,adminUser);
        setupOffices(employees,adminUser);

        log.info("setupDatabase Terminated.");
    }

    private User createAdminUser (){
        Optional<RoleDto> optionalRole =roleService.findByName(ADMIN_ROLE);
        if (!optionalRole.isPresent()){
            createRole();
        }
        createUser(ADMIN_ROLE);
        return userRepository.findOneByUsername(USERNAME).get();
    }


    private void setupBusinessUnits(List<Employee> employees, User admin) {

        List<BusinessUnit> businessUnits=  businessUnitRepository.findAllActive();
        int bunitCounter=0;
        for (Employee employee : employees){
            if (bunitCounter==businessUnits.size()){
                bunitCounter=0;
            }
            BusinessUnit businessUnit =businessUnits.get(bunitCounter);
            businessUnit.setCreatedBy(admin);
            businessUnitRepository.save(businessUnit);

            employee.setBusinessUnit(businessUnit);
            employeeRepository.save(employee);
            bunitCounter++;

        }
    }

    private void setupDivisions(List<Employee> employees, User admin) {

        List<Division> divisions=  divisionRepository.findAllActive();
        int counter=0;
        for (Employee employee : employees){
            if (counter==divisions.size()){
                counter=0;
            }
            Division division =divisions.get(counter);
            division.setCreatedBy(admin);
            divisionRepository.save(division);

            employee.setDivision(division);
            employeeRepository.save(employee);
            counter++;
        }
    }

    private void setupJobTitles(List<Employee> employees, User admin) {

        List<JobTitle> jobTitles= jobTitleRepository.findAllActive();
        int counter=0;
        for (Employee employee : employees){
            if (counter==jobTitles.size()){
                counter=0;
            }
            JobTitle jobTitle =jobTitles.get(counter);
            jobTitle.setCreatedBy(admin);
            jobTitleRepository.save(jobTitle);

            employee.setJobTitle(jobTitle);
            employeeRepository.save(employee);
            counter++;
        }
    }

    private void setupDepartments(List<Employee> employees, User admin) {

        List<Department> departments= departmentRepository.findAllActive();
        int counter=0;
        for (Employee employee : employees){
            if (counter==departments.size()){
                counter=0;
            }
            Department department =departments.get(counter);
            department.setCreatedBy(admin);
            departmentRepository.save(department);

            employee.setDepartment(departments.get(counter));
            employeeRepository.save(employee);
            counter++;
        }
    }

    private void setupOrganizations(List<Employee> employees, User admin) {

        List<Organization> organizations= organizationRepository.findAllActive();
        int counter=0;
        for (Employee employee : employees){
            if (counter==organizations.size()){
                counter=0;
            }
            Organization organization =organizations.get(counter);
            organization.setCreatedBy(admin);
            organizationRepository.save(organization);

            employee.setOrganization(organization);
            employeeRepository.save(employee);
            counter++;
        }
    }

    private void setupCostCenters(List<Employee> employees, User admin) {

        List<CostCenter> costCenters= costCenterRepository.findAllActive();
        int counter=0;
        for (Employee employee : employees){
            if (counter==costCenters.size()){
                counter=0;
            }
            CostCenter costCenter =costCenters.get(counter);
            costCenter.setCreatedBy(admin);
            costCenterRepository.save(costCenter);

            employee.setCostCenter(costCenter);
            employeeRepository.save(employee);
            counter++;
        }
    }

    private void setupTeams(List<Employee> employees, User admin) {

        List<Team> teams= teamRepository.findAllActive();
        int counter=0;
        for (Employee employee : employees){
            if (counter==teams.size()){
                counter=0;
            }
            Team team =teams.get(counter);
            team.setCreatedBy(admin);
            teamRepository.save(team);

            employee.setTeam(team);
            employeeRepository.save(employee);
            counter++;
        }
    }

    private void setupGroups(List<Employee> employees, User admin) {

        List<Group> groups= groupRepository.findAllActive();
        int counter=0;

        for (Group group : groups){
            if (counter==employees.size()){
                counter=0;
            }

            Set<Employee> employeeSet=new HashSet<>();
            employeeSet.add(employees.get(counter));
            group.setEmployees(employeeSet);
            group.setCreatedBy(admin);
            groupRepository.save(group);
            counter++;
        }
    }

    private void setupOrganizations(User adminUser) {

        List<Organization> organizations=  organizationRepository.findAllActive();
        List<Address> addresses=addressRepository.findAllActive();
        List<Contact> contacts=contactRepository.findAllActive();

        int counter=0;
        int rotation=(addresses.size()>contacts.size())? contacts.size() : addresses.size();
        for (Organization organization : organizations){
            if (counter==rotation){
                counter=0;
            }
            Address address =addresses.get(counter);
            organization.setLegalAddress(address);
            organization.setContactPerson(contacts.get(counter));
            organization.setTaxRegistrationCountry(address.getCountry());
            organization.setCreatedBy(adminUser);
            organizationRepository.save(organization);
            counter++;
        }
    }

    private void setupLocations(User adminUser) {
        List<Address> addresses=addressRepository.findAllActive();
        List<Location> locations = locationRepository.findAllActive();

        int counter=0;

        for (Location location : locations){
            if (counter==addresses.size()){
                counter=0;
            }
            location.setAddress(addresses.get(counter));
            location.setCreatedBy(adminUser);
            locationRepository.save(location);
            counter++;
        }
    }

    private void setupOffices(List<Employee> employees,User adminUser) {
        List<Location> locations= locationRepository.findAllActive();
        int counter=0;
        for (Employee employee : employees){
            if (counter==locations.size()){
                counter=0;
            }
            employee.setOffice(locations.get(counter));
            employeeRepository.save(employee);
            counter++;
        }
    }

    private void createUser(String roleName){

        UserDto newUser=UserDto.builder()
                .firstName("Stefano")
                .lastName("Fiorenza")
                .password("123")
                .username(USERNAME)
                .roleName(roleName)
                .email("stefanofiorenza@email.it")
                .build();

        userService.save(newUser);
    }

    private String createRole() {
        RoleDto roleDto= RoleDto.builder().
                name(ADMIN_ROLE).build();

        RoleDto created=roleService.create(roleDto);
        return created.getName();
    }
}
