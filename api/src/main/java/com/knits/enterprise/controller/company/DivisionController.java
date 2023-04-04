package com.knits.enterprise.controller.company;

import com.knits.enterprise.dto.data.company.DivisionDto;
import com.knits.enterprise.service.company.DivisionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class DivisionController {

    @Autowired
    private DivisionService divisionService;

    @PostMapping(value = "/divisions", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<DivisionDto> createBusinessUnit(@RequestBody DivisionDto divisionDto) {
        log.debug("REST request to create Division ");
        return ResponseEntity.ok().body(divisionService.create(divisionDto));
    }

    @DeleteMapping("/divisions/{id}")
    public ResponseEntity<Void> deleteDivision(@PathVariable Long id) {
        log.debug("REST request to delete Division : {}", id);
        divisionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/divisions", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<DivisionDto> partialUpdate(@RequestBody DivisionDto divisionDto) {
        log.debug("REST request to update Division ");
        return ResponseEntity.ok().body(divisionService.partialUpdate(divisionDto));
    }


}
