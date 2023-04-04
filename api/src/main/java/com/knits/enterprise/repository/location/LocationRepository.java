package com.knits.enterprise.repository.location;

import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends ActiveEntityRepository<Location> {

    Location findByName(String location);

}
