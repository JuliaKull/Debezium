package com.knits.enterprise.repository.company;

import com.knits.enterprise.model.company.Department;
import com.knits.enterprise.model.company.Division;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends ActiveEntityRepository<Department> {

    @Query("SELECT d from Department d WHERE d.name=:name AND d.active = true")
    Optional<Department> findOneByName(@Param("name") String name);
}

