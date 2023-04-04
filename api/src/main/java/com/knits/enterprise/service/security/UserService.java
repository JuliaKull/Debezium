package com.knits.enterprise.service.security;

import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.exceptions.AuthorizationException;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.security.UserMapper;
import com.knits.enterprise.model.security.Role;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.repository.security.RoleRepository;
import com.knits.enterprise.repository.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Service for managing {@link User}.
 */
@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Save a employee.
     *
     * @param userDTO the entity to save.
     * @return the persisted entity.
     */
    public UserDto save(UserDto userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role= roleRepository.findOneByName(userDTO.getRoleName()).orElseThrow(
                () -> new UserException("Role with name " + userDTO.getRoleName() + " not found")
        );
        User user = userMapper.toEntity(userDTO);
        user.setRole(role);
        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Partially updates a user.
     *
     * @param userDTO the entity to update partially.
     * @return the persisted entity.
     */
    public UserDto partialUpdate(UserDto userDTO) {
        log.debug("Request to partially update User : {}", userDTO);
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new UserException("User#" + userDTO.getId() + " not found"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userMapper.partialUpdate(user, userDTO);

        //TODO: manage User relationship to check updates
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * overrides fields, including nulls.
     *
     * @param userDTO the entity to update.
     * @return the persisted entity.
     */
    public UserDto update(UserDto userDTO) {
        log.debug("Request to update User : {}", userDTO);
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new UserException("User#" + userDTO.getId() + " not found"));
        if (StringUtils.isNotEmpty(userDTO.getPassword())) {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userMapper.update(user, userDTO);
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Get by the "id" user.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public UserDto findById(Long id) {

        log.debug("Request User by id : {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User#" + id + " not found"));
        return userMapper.toDto(user);
    }

    /**
     * Delete the "id" user.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Delete User by id : {}", id);
        userRepository.deleteById(id);
    }


    /**
     * Get all the users.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserDto> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("not yet implementes");
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loggedUser = userRepository.findOneByUsername(username).orElseThrow(() -> new UserException(format("Username %s not found", username)));
        return userMapper.toDto(loggedUser);
    }


    public UserDto getCurrentUserAsDto() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthorizationException("No User currently logged while executing this operation");
        }
        return (UserDto) authentication.getPrincipal();
    }

    public User getCurrentUserAsEntity() {
        return userMapper.toEntity(getCurrentUserAsDto());
    }


}
