package com.knits.enterprise.repository.common;

import com.knits.enterprise.model.common.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {

    List<Country> findByName(String name);
}
