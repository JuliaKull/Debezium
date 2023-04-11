package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.BusinessUnitDto;
import com.knits.enterprise.dto.data.company.EmployeeDto;
import com.knits.enterprise.dto.search.company.BusinessUnitSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.company.BusinessUnitMapper;
import com.knits.enterprise.model.company.BusinessUnit;
import com.knits.enterprise.repository.company.BusinessUnitRepository;
import com.knits.enterprise.service.common.GenericService;
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
public class BusinessUnitService extends GenericService {

    private final BusinessUnitRepository repository;
    private final BusinessUnitMapper mapper;


    public BusinessUnitDto create(BusinessUnitDto businessUnitDto) {
        String operationLog =format("Request to save BusinessUnit : %s", businessUnitDto.toString());
        logCurrentUser(operationLog);
        String businessUnitName =businessUnitDto.getName();
        if (repository.findOneByName(businessUnitName).isPresent()){
            throw new UserException(format("BusinessUnit with name %s already exists",businessUnitName));
        }
        BusinessUnit businessUnit = mapper.toEntity(businessUnitDto);
        businessUnit.setCreatedBy(getCurrentUserAsEntity());
        businessUnit.setStartDate(LocalDateTime.now());
        businessUnit.setActive(true);
        return mapper.toDto(repository.save(businessUnit));
    }

    @Transactional
    public BusinessUnitDto update(BusinessUnitDto businessUnitDto) {
        log.debug("Request to update BusinessUnit : {}", businessUnitDto);

        BusinessUnit businessUnit = repository.findById(businessUnitDto.getId()).orElseThrow(()
                -> new UserException("BusinessUnit#" + businessUnitDto.getId() + " not found"));
        mapper.update(businessUnit, businessUnitDto);
        repository.save(businessUnit);
        return mapper.toDto(businessUnit);
    }

    @Transactional
    public BusinessUnitDto partialUpdate(BusinessUnitDto businessUnitDto) {
        log.debug("Request to update BusinessUnit : {}", businessUnitDto);

        BusinessUnit businessUnit = repository.findById(businessUnitDto.getId()).orElseThrow(()
                -> new UserException("BusinessUnit#" + businessUnitDto.getId() + " not found"));

        mapper.partialUpdate(businessUnit, businessUnitDto);        ;
        return mapper.toDto(repository.save(businessUnit));
    }

    public void delete(Long id) {
        log.debug("Delete BusinessUnit by id : {}", id);
        repository.deleteById(id);
    }

    public Page<BusinessUnitDto> getActive(BusinessUnitSearchDto businessUnitSearchDto) {

        Page<BusinessUnit> businessUnitPage = repository.findAll(businessUnitSearchDto.getSpecification(), businessUnitSearchDto.getPageable());
        List<BusinessUnitDto> businessUnitDtos = mapper.toDtos(businessUnitPage.getContent());
        return new PageImpl<>(businessUnitDtos, businessUnitSearchDto.getPageable(), businessUnitPage.getTotalElements());
    }

    public PaginatedResponseDto<BusinessUnitDto> search(BusinessUnitSearchDto searchDto) {

        Page<BusinessUnit> businessUnitPage = repository.findAll(searchDto.getSpecification(), searchDto.getPageable());
        List<BusinessUnitDto> businessUnitDtos = mapper.toDtos(businessUnitPage.getContent());
        return PaginatedResponseDto.<BusinessUnitDto>builder()
                .page(searchDto.getPage())
                .size(businessUnitDtos.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(businessUnitDtos)
                .build();
    }

    public BusinessUnitDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow( ()-> new UserException("BusinessUnit#" + id + " not found")));
    }
}
