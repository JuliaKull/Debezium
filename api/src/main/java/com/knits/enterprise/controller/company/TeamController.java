package com.knits.enterprise.controller.company;

import com.knits.enterprise.dto.data.company.TeamDto;
import com.knits.enterprise.service.company.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private static final String ENDPOINT_NAME="/teams";

    @PostMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto){
        log.debug("Http request to create {} with data: {} ",ENDPOINT_NAME,teamDto);
        return ResponseEntity.ok()
                .body(teamService.create(teamDto));
    }
}
