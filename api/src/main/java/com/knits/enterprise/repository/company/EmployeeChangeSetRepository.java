package com.knits.enterprise.repository.company;

import com.knits.enterprise.model.company.EmployeeChangeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeChangeSetRepository extends JpaRepository<EmployeeChangeSet,Long> {
}
