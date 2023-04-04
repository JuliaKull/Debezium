package com.knits.enterprise.controller.company;


import com.knits.enterprise.dto.api.BulkUpdateResponseDto;
import com.knits.enterprise.dto.data.company.GroupDto;
import com.knits.enterprise.service.company.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class GroupController {

    private static final String ENDPOINT_NAME="/groups";

    private final GroupService groupService;

    @PostMapping(value = ENDPOINT_NAME,produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto){
        log.debug("REST request to create Group");
        return ResponseEntity.ok()
                .body(groupService.create(groupDto));
    }

    @PatchMapping(value = ENDPOINT_NAME+"/{id}/employees",produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<BulkUpdateResponseDto<GroupDto>> addListOfEmployeesToGroup(@PathVariable Long id, @RequestBody Set<Long> employeeIds){
        log.debug("REST request to add list of Employees to Group :{}",id);
        return ResponseEntity.ok()
                .body(groupService.addEmployeeToGroup(id,employeeIds));
    }

}
