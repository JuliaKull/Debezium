package com.knits.enterprise.repository.company;

import com.knits.enterprise.model.company.CostCenter;
import com.knits.enterprise.model.company.Division;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DivisionRepository extends ActiveEntityRepository<Division> {

    @Query("SELECT d from Division d WHERE d.name=:name AND d.active = true")
    Optional<Division> findOneByName(@Param("name") String name);
}

