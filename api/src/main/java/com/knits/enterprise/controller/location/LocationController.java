package com.knits.enterprise.controller.location;

import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.dto.search.location.LocationSearchDto;
import com.knits.enterprise.service.location.LocationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class LocationController {

    private LocationService locationService;

    private static final String ENDPOINT_NAME="/locations";

    @PostMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<LocationDto> create(@RequestBody LocationDto locationDto) {
        log.debug("Http request to create {} with data: {} ",ENDPOINT_NAME,locationDto);
        return ResponseEntity.ok().body(locationService.create(locationDto));
    }

    @PutMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<LocationDto> update(@RequestBody LocationDto locationDto) {
        log.debug("Http request to update {} with data {} ",ENDPOINT_NAME,locationDto);
        return ResponseEntity.ok().body(locationService.update(locationDto));
    }

    @PatchMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<LocationDto> partialUpdate(@RequestBody LocationDto locationDto) {
        log.debug("Http request to partialUpdate {} with request data: {} ",ENDPOINT_NAME,locationDto);
        return ResponseEntity.ok().body(locationService.partialUpdate(locationDto));
    }

    @DeleteMapping(value =ENDPOINT_NAME+"/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Http request to delete {} with Id: {}",ENDPOINT_NAME, id);
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = ENDPOINT_NAME+"/search", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<List<LocationDto>> search(@RequestBody LocationSearchDto locationSearchDto) {
        log.debug("Http request to search {} with request data: {} ",ENDPOINT_NAME, locationSearchDto.toString());
        return ResponseEntity.ok().body(locationService.search(locationSearchDto).toList());
    }



}
