package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.EmployeeDto;
import com.knits.enterprise.dto.data.company.JobTitleDto;
import com.knits.enterprise.dto.search.company.JobTitleSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.company.JobTitleMapper;
import com.knits.enterprise.model.company.BusinessUnit;
import com.knits.enterprise.model.company.JobTitle;
import com.knits.enterprise.repository.company.JobTitleRepository;
import com.knits.enterprise.service.common.GenericService;
import com.knits.enterprise.service.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class JobTitleService extends GenericService {


    private final JobTitleRepository repository;

    private final JobTitleMapper mapper;

    private final UserService userService;

    public JobTitleDto create(JobTitleDto jobTitleDto) {
        logCurrentUser(format("Request to save JobTitle : %s", jobTitleDto.toString()));
        String name =jobTitleDto.getName();
        if (repository.findOneByName(name).isPresent()){
            throw new UserException(format("JobTitle with name %s already exists",name));
        }

        log.debug("Request to save JobTitle : {}", jobTitleDto);

        JobTitle jobTitle = mapper.toEntity(jobTitleDto);
        jobTitle.setCreatedBy(getCurrentUserAsEntity());
        jobTitle.setStartDate(LocalDate.now());
        jobTitle.setActive(true);
        return mapper.toDto(repository.save(jobTitle));
    }

    public void delete(Long id) {
        logCurrentUser(format("Request to delete JobTitle# : %s", id));
        repository.deleteById(id);
    }

    @Transactional
    public JobTitleDto partialUpdate(JobTitleDto jobTitleDto) {

        logCurrentUser(format("Request to partialUpdate JobTitle : %s", jobTitleDto.toString()));
        JobTitle jobTitle = repository.findById(jobTitleDto.getId()).orElseThrow(()
                -> new UserException("JobTitle#" + jobTitleDto.getId() + " not found"));

        mapper.partialUpdate(jobTitle, jobTitleDto);
        return mapper.toDto(repository.save(jobTitle));
    }

    @Transactional
    public JobTitleDto update(JobTitleDto jobTitleDto) {
        logCurrentUser(format("Request to update JobTitle : %s", jobTitleDto.toString()));
        JobTitle jobTitle = repository.findById(jobTitleDto.getId()).orElseThrow(()
                -> new UserException("JobTitle#" + jobTitleDto.getId() + " not found"));
        mapper.update(jobTitle, jobTitleDto);
        return mapper.toDto(repository.save(jobTitle));
    }

    public PaginatedResponseDto<JobTitleDto> search(JobTitleSearchDto jobTitleSearchDto) {
        Page<JobTitle> jobTitlePage = repository.findAll(jobTitleSearchDto.getSpecification(), jobTitleSearchDto.getPageable());
        List<JobTitleDto> jobTitleDtos = mapper.toDtos(jobTitlePage.getContent());
        return PaginatedResponseDto.<JobTitleDto>builder()
                .page(jobTitleSearchDto.getPage())
                .size(jobTitleDtos.size())
                .sortingFields(jobTitleSearchDto.getSort())
                .sortDirection(jobTitleSearchDto.getDir().name())
                .data(jobTitleDtos)
                .build();
    }
}
