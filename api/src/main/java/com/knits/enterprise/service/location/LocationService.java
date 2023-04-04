package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.dto.search.location.LocationSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.location.LocationMapper;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.repository.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class LocationService {
    @Autowired
    private LocationRepository repository;
    @Autowired
    private LocationMapper mapper;


    public LocationDto create(LocationDto locationDto) {
        log.debug("Request to create Location : {}", locationDto);
        Location location = mapper.toEntity(locationDto);
        repository.save(location);
        return mapper.toDto(location);
    }

    public LocationDto update(LocationDto locationDto){
        log.debug("Request to edit Location : {}", locationDto);
        final Location locationFromDb = repository.findById(locationDto.getId()).get();
        if ( locationFromDb.getId()==null) {
            String message = "Location with id = " + locationDto.getId() + " does not exist.";
            log.warn(message);
            throw new UserException(message);
        }
        mapper.update(locationFromDb,locationDto);
        repository.save(locationFromDb);
        return mapper.toDto(locationFromDb);
    }

    public LocationDto partialUpdate (LocationDto locationDto){
        log.debug("Request to partial update Location : {}", locationDto);
        Location location = repository.findById(locationDto.getId()).orElseThrow(() -> new UserException("Location#" + locationDto.getId() + " not found"));
        mapper.partialUpdate(location,locationDto);
        repository.save(location);
        return mapper.toDto(location);
    }

    public void delete(Long id){
        log.debug("Set status deleted = true to Location Id: {}", id);
        repository.deleteById(id);}

    public Page<LocationDto> search(LocationSearchDto locationSearch) {
        Page<Location> locationPage = repository.findAll(locationSearch.getSpecification(), locationSearch.getPageable());
        List<LocationDto> locationDtos = mapper.toDtos(locationPage.getContent());
        return new PageImpl<>(locationDtos, locationSearch.getPageable(), locationPage.getTotalElements());
    }



}
