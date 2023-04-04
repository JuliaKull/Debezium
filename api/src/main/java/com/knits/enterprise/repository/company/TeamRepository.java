package com.knits.enterprise.repository.company;

import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.company.Team;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeamRepository extends ActiveEntityRepository<Team> {
    @Query("SELECT t from Team t WHERE t.name=:name AND t.active = true")
    Optional<Team> findOneByName(String name);
    Boolean existsByName(String name);
}
