package com.knits.enterprise.controller.security;

import com.knits.enterprise.auth.JwtTokenComponent;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.exceptions.ExceptionCodes;
import com.knits.enterprise.exceptions.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    private JwtTokenComponent jwtTokenComponent;

    @PostMapping(value = "/login", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<UserDto> login(@RequestBody UserDto login) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new UserException(String.format("login for %s failed ", login.getUsername()), ExceptionCodes.NOT_AUTHENTICATED);
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDto authenticatedUserDto = (UserDto) authentication.getPrincipal();
            authenticatedUserDto.setToken(jwtTokenComponent.generateToken(authenticatedUserDto));
            authenticatedUserDto.setPassword(null);

            return ResponseEntity
                    .ok()
                    .body(authenticatedUserDto);
        } catch (BadCredentialsException e) {
            throw new UserException(String.format("login for %s failed. Bad credentials ", login.getUsername()), ExceptionCodes.NOT_AUTHENTICATED);
        }


    }
}
