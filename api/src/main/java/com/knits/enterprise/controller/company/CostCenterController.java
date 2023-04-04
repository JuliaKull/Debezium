package com.knits.enterprise.controller.company;

import com.knits.enterprise.dto.data.company.CostCenterDto;
import com.knits.enterprise.dto.search.company.CostCenterSearchDto;
import com.knits.enterprise.service.company.CostCenterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CostCenterController {

    @Autowired
    private CostCenterService costCenterService;

    @PostMapping(value = "/cost-centers", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<CostCenterDto> createBusinessUnit(@RequestBody CostCenterDto costCenterDto) {
        log.debug("REST request to create CostCenter ");
        return ResponseEntity.ok().body(costCenterService.create(costCenterDto));
    }

    @DeleteMapping("/cost-centers/{id}")
    public ResponseEntity<Void> deleteCostCenter(@PathVariable Long id) {
        log.debug("REST request to delete CostCenter : {}", id);
        costCenterService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/cost-centers", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<CostCenterDto> partialUpdate(@RequestBody CostCenterDto costCenterDto) {
        log.debug("REST request to update CostCenter ");
        return ResponseEntity.ok().body(costCenterService.partialUpdate(costCenterDto));
    }

    @GetMapping(value = "/cost-centers/search", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<List<CostCenterDto>> getAll(@RequestBody CostCenterSearchDto costCenterSearchDto) {
        log.debug("REST request to search CostCenter");
        return ResponseEntity.ok().body(costCenterService.getAll(costCenterSearchDto).toList());
    }
}
