package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.TeamDto;
import com.knits.enterprise.dto.search.company.TeamSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.company.TeamMapper;
import com.knits.enterprise.model.company.Team;
import com.knits.enterprise.repository.company.TeamRepository;
import com.knits.enterprise.service.common.GenericService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TeamService extends GenericService {

    private final TeamRepository repository;
    private final TeamMapper mapper;

    public TeamDto create(TeamDto teamDto) {
        String operationLog = format("Request to save Team : %s", teamDto.toString());
        logCurrentUser(operationLog);

        String teamName = teamDto.getName();
        if (repository.findOneByName(teamName).isPresent()) {
            throw new UserException(format("Team with name %s already exists", teamName));
        }

        Team team = mapper.toEntity(teamDto);
        team.setCreatedBy(getCurrentUserAsEntity());
        team.setStartDate(LocalDateTime.now());
        team.setActive(true);

        return mapper.toDto(repository.save(team));
    }

    @Transactional
    public TeamDto update(TeamDto teamDto) {
        log.debug("Request to update Team : {}", teamDto);

        Team team = repository.findById(teamDto.getId()).orElseThrow(() ->
                new UserException("Team#" + teamDto.getId() + " not found"));
        mapper.update(team, teamDto);
        return mapper.toDto(repository.save(team));
    }

    @Transactional
    public TeamDto partialUpdate(TeamDto teamDto) {
        log.debug("Request to update Team : {}", teamDto);
        Team team = repository.findById(teamDto.getId()).orElseThrow(() ->
                new UserException("Team#" + teamDto.getId() + " not found"));

        mapper.partialUpdate(team, teamDto);
        return mapper.toDto( repository.save(team));
    }

    public void delete(Long id) {
        log.debug("Delete Team by id : {}", id);
        repository.deleteById(id);
    }
    public PaginatedResponseDto<TeamDto> search(TeamSearchDto searchDto) {
        Page<Team> teamPage = repository.findAll(searchDto.getSpecification(), searchDto.getPageable());
        List<TeamDto> teamDtos = mapper.toDtos(teamPage.getContent());
        return PaginatedResponseDto.<TeamDto>builder()
                .page(searchDto.getPage())
                .size(teamDtos.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(teamDtos)
                .build();
    }


    public TeamDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() ->
                new UserException("Team#" + id + " not found")));
    }
}
