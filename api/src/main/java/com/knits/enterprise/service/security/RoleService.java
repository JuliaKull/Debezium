package com.knits.enterprise.service.security;

import com.knits.enterprise.dto.data.security.RoleDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.security.RolerMapper;
import com.knits.enterprise.model.security.Role;
import com.knits.enterprise.repository.security.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.lang.String.format;
@Service
@Transactional
@Slf4j
public class RoleService {

    @Autowired
    private RolerMapper rolerMapper;

    @Autowired
    private RoleRepository roleRepository;

    public RoleDto create(RoleDto roleDto){
        Role role =rolerMapper.toEntity(roleDto);
        if (roleRepository.existsRoleByName(roleDto.getName())){
            throw new UserException(format("Role %s already exists",roleDto.getName()));
        }
        role.setActive(true);
        return rolerMapper.toDto(roleRepository.save(role));
    }

    public Optional<RoleDto> findByName(String name){

        Optional<Role> roleIfAny =roleRepository.findOneByName(name);
        if(roleIfAny.isPresent()){
            return Optional.of(rolerMapper.toDto(roleIfAny.get()));
        }
        return Optional.ofNullable(null);
    }
}
