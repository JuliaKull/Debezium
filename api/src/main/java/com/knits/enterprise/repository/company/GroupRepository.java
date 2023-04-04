package com.knits.enterprise.repository.company;

import com.knits.enterprise.model.company.Group;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends ActiveEntityRepository<Group> {

    @Query("SELECT g from Group g WHERE g.name=:name AND g.active = true")
    Optional<Group> findOneByName(String name);
    Boolean existsByName(String name);

    @Query("select g from Group g left join fetch g.employees where g.id=:id")
    Optional<Group> findByIdWithEmployees (@Param("id") Long groupId);

}
