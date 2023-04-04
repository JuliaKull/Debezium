package com.knits.enterprise.controller.company;

import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.JobTitleDto;
import com.knits.enterprise.dto.search.company.JobTitleSearchDto;
import com.knits.enterprise.service.company.JobTitleService;
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
public class JobTitleController {
    @Autowired
    private  JobTitleService jobTitleService;

    private static final String ENDPOINT_NAME="/teams";

    @PostMapping(value = "/job-titles", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<JobTitleDto> createJobTitle(@RequestBody JobTitleDto jobTitleDto) {
        log.debug("Http Post request to create {} , with data: {} ",ENDPOINT_NAME,jobTitleDto);
        return ResponseEntity.ok().body(jobTitleService.create(jobTitleDto));
    }

    @DeleteMapping(value = ENDPOINT_NAME+"/{id}")
    public ResponseEntity<Void> deleteJobTitle(@PathVariable Long id) {
        log.debug("Http delete to delete {} : {}", ENDPOINT_NAME,id);
        jobTitleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<JobTitleDto> partialUpdate(@RequestBody JobTitleDto jobTitleDto) {
        log.debug("Http Put request to update {} with data: {} ",ENDPOINT_NAME,jobTitleDto);
        return ResponseEntity.ok().body(jobTitleService.partialUpdate(jobTitleDto));
    }

    @GetMapping(value = ENDPOINT_NAME+"/search", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<PaginatedResponseDto<JobTitleDto>> getActive(@RequestBody JobTitleSearchDto jobTitleSearchDto) {
        log.debug("REST request to search JobTitle");
        return ResponseEntity.ok().body(jobTitleService.search(jobTitleSearchDto));
    }

}
