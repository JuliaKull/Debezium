package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.data.company.DivisionDto;
import com.knits.enterprise.dto.search.company.DivisionSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.company.DivisionMapper;
import com.knits.enterprise.model.company.Division;
import com.knits.enterprise.repository.company.DivisionRepository;
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
public class DivisionService extends GenericService {

    private final DivisionRepository repository;
    private final DivisionMapper mapper;

    public DivisionDto create(DivisionDto divisionDto) {
        String operationLog = format("Request to save Division: %s", divisionDto.toString());
        logCurrentUser(operationLog);
        String divisionName = divisionDto.getName();
        if (repository.findOneByName(divisionName).isPresent()) {
            throw new UserException(format("Division with name %s already exists", divisionName));
        }
        Division division = mapper.toEntity(divisionDto);
        division.setCreatedBy(getCurrentUserAsEntity());
        division.setStartDate(LocalDateTime.now());
        division.setActive(true);
        return mapper.toDto(repository.save(division));
    }

    @Transactional
    public DivisionDto update(DivisionDto divisionDto) {
        log.debug("Request to update Division: {}", divisionDto);

        Division division = repository.findById(divisionDto.getId()).orElseThrow(() ->
                new UserException("Division#" + divisionDto.getId() + " not found"));
        mapper.update(division, divisionDto);
        repository.save(division);
        return mapper.toDto(division);
    }

    @Transactional
    public DivisionDto partialUpdate(DivisionDto divisionDto) {
        log.debug("Request to update Division: {}", divisionDto);

        Division division = repository.findById(divisionDto.getId()).orElseThrow(() ->
                new UserException("Division#" + divisionDto.getId() + " not found"));

        mapper.partialUpdate(division, divisionDto);
        repository.save(division);
        return mapper.toDto(division);
    }

    public void delete(Long id) {
        log.debug("Delete Division by id : {}", id);
        repository.deleteById(id);
    }

    public Page<DivisionDto> getActive(DivisionSearchDto divisionSearchDto) {

        Page<Division> divisionPage = repository.findAll(divisionSearchDto.getSpecification(), divisionSearchDto.getPageable());
        List<DivisionDto> divisionDtos = mapper.toDtos(divisionPage.getContent());
        return new PageImpl<>(divisionDtos, divisionSearchDto.getPageable(), divisionPage.getTotalElements());
    }

    public Page<DivisionDto> search(DivisionSearchDto divisionSearchDto) {

        Page<Division> divisionPage = repository.findAll(divisionSearchDto.getSpecification(), divisionSearchDto.getPageable());
        List<DivisionDto> divisionDtos = mapper.toDtos(divisionPage.getContent());
        return new PageImpl<>(divisionDtos, divisionSearchDto.getPageable(), divisionPage.getTotalElements());
    }
}