package com.knits.enterprise.repository.company;

import com.knits.enterprise.model.company.BusinessUnit;
import com.knits.enterprise.model.company.CostCenter;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CostCenterRepository extends ActiveEntityRepository<CostCenter>{

    @Query("SELECT c from CostCenter c WHERE c.name=:name AND c.active = true")
    Optional<CostCenter> findOneByName(@Param("name") String name);
}
