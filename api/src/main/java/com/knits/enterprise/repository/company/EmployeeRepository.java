package com.knits.enterprise.repository.company;

import com.knits.enterprise.dto.projection.AggregationResultDto;
import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends ActiveEntityRepository<Employee> {

    @Query("select e.gender as key, count (e.id) as count from Employee e group by e.gender")
    List<AggregationResultDto> countByGender();


    @Query("select j.name as key, count (e.id) as count from Employee e join JobTitle j on e.jobTitle.id = j.id group by j.name")
    List<AggregationResultDto> countByJobTitle();

    @Query("select d.name as key, count (e.id) as count from Employee e join Department d on e.department.id = d.id group by d.name")
    List<AggregationResultDto> countByDepartment();

    @Query("select c.name as key, count (e.id) as count from Employee e join Location l on e.office.id = l.id" +
            " join Address a on l.address.id=a.id join Country c on a.country.id=c.id group by c.name order by c.name asc ")
    List<AggregationResultDto> countByCountry();

    @Query("select b.name as key, count (e.id) as count from Employee e " +
            "join BusinessUnit b on e.businessUnit.id = b.id group by b.name")
    List<AggregationResultDto> countByBusinessUnit();

    @Query("select e from Employee e where e.id IN (:ids) AND e.active = true")
    Set<Employee> findAllByIdAsSet(@Param("ids") Set<Long> ids);

//    @Query("select case " +
//            "    when (:currentYear - extract(year from e.startDate)) between 0 and 1 then '1 year'" +
//            "    when (:currentYear - extract(year from e.startDate)) between 1 and 2 then '2 years'" +
//            "    when (:currentYear - extract(year from e.startDate)) between 2 and 5 then '5 years'" +
//            "    else '10+ years'" +
//            "  end as key," +
//            "  count(e.id) as count " +
//            "from Employee e " +
//            "where e.endDate is null " +
//            "group by key " +
//            "order by key asc ")
//    List<StatsKeyCountDto> countBySeniority(@Param("currentYear") Integer year);

    @Query("select case " +
            "    when (extract(YEAR from CURRENT_DATE ) - extract(YEAR from e.startDate))=0 then '0'" +
            "    when (extract(YEAR from CURRENT_DATE ) - extract(YEAR from e.startDate))=1 then '1'" +
            "    when (extract(YEAR from CURRENT_DATE ) - extract(YEAR from e.startDate))=2 then '2'" +
            "    when (extract(YEAR from CURRENT_DATE ) - extract(YEAR from e.startDate)) between 3 and 4 then '3+'" +
            "    when (extract(YEAR from CURRENT_DATE ) - extract(YEAR from e.startDate)) between 5 and 9 then '5+'"+
            "    else '10+ years'" +
            "  end as key," +
            "  count(e.id) as count " +
            "from Employee e " +
            "where e.endDate is null " +
            "group by key " +
            "order by key asc ")
    List<AggregationResultDto> countBySeniority(@Param("currentYear") Integer year);


    @Query("select case " +
            "    when (:currentYear - extract(year from e.birthDate)) between 20 and 24 then '20-24 years'" +
            "    when (:currentYear - extract(year from e.birthDate)) between 25 and 29 then '25-29 years'" +
            "    when (:currentYear - extract(year from e.birthDate)) between 30 and 34 then '30-34 years'" +
            "    when (:currentYear - extract(year from e.birthDate)) between 35 and 39 then '35-39 years'" +
            "    else '40+ years'" +
            "  end as key," +
            "  count(e.id) as count " +
            "from Employee e " +
            "group by key")
    List<AggregationResultDto> countByAge(@Param("currentYear") Integer currentYear);


    /*


 /*



 @Query("select year (e.startDate) as year, count (e.id) as hired from Employee e " +
         " where year (e.startDate) >= :last15Years group by year (e.startDate) order by year (e.startDate) asc ")
 List<PieDto> hiresByYear15(@Param("last15Years") Integer year);

 @Query("select year (e.endDate) as year, count (e.id) as leaves from Employee e " +
         " where year (e.endDate) >= :last15Years group by year (e.endDate) order by year (e.endDate) asc ")
 List<EmployeeLeavesDto> leavesIn15Years(@Param("last15Years") Integer year);

 @Query(nativeQuery = true, value = "select b.name as businessUnit, count(e.id) as count from Employee e " +
         "join business_unit b on e.business_unit_id = b.id group by b.name order by count desc limit 1 ")
 EmployeeHighestbyBusinessUnitDto higestByBusinessUnit();

 @Query(nativeQuery = true, value = "select j.name as jobTitle, count(e.id) as count from Employee e " +
         "join job_title j on e.job_title_id = j.id group by j.name order by count desc limit 1")
 EmployeeHighestbyJobTitleDto higestByJobTitle();

 @Query(nativeQuery = true, value = "select d.name as department, count(e.id) as count from Employee e " +
         "join department d on e.department_id = d.id group by d.name order by count desc limit 1 ")
 EmployeeHighestbyDepartmentDto higestByDepartment();

 @Query("select year (e.startDate) as year, count (e.id) as count from Employee e " +
         " where year (e.startDate) >= :last15Years and e.endDate is null group by year (e.startDate) order by year (e.startDate) asc ")
 List<EmployeeTotalDto> getTotalIn15Years(@Param("last15Years") Integer year);

 @Query("select count (e) > 0 from Employee e where e.id in (:employeeIds)")
 boolean existsByIds(@Param("employeeIds") Set<Long> employeeIds);




  */



}
