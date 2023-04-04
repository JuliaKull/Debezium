package com.knits.enterprise.service.common;

import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.exceptions.AuthorizationException;
import com.knits.enterprise.mapper.security.UserMapper;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class GenericService {

    @Autowired
    private UserService userService;


    protected void logCurrentUser (String actionPerformed){
        log.debug("{} performed by {}",actionPerformed,userService.getCurrentUserAsDto());
    }

    protected UserDto getCurrentUserAsDto() {
        return userService.getCurrentUserAsDto();
    }

    protected User getCurrentUserAsEntity() {
        return userService.getCurrentUserAsEntity();
    }
}
