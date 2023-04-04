package com.knits.enterprise.repository.company;

import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.company.JobTitle;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends ActiveEntityRepository<Organization> {

    @Query("SELECT o from Organization o WHERE o.name=:name AND o.active = true")
    Optional<Organization> findOneByName(String name);
}
