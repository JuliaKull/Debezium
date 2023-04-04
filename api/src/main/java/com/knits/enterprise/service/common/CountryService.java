package com.knits.enterprise.service.common;

import com.knits.enterprise.dto.data.common.CountryDto;
import com.knits.enterprise.mapper.common.CountryMapper;
import com.knits.enterprise.repository.common.CountryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CountryService {

    private final CountryRepository repository;
    private final CountryMapper countryMapper;

    public List<CountryDto> getCountryByName(String name){
        return repository.findByName(name).stream().map( c-> countryMapper.toDto(c)).collect(Collectors.toList());
    }
}

