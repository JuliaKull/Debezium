package com.knits.enterprise.controller.security;

import com.knits.enterprise.auth.JwtTokenComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private AuthenticationManager authenticationManager;

    private JwtTokenComponent jwtTokenComponent;




    /*
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/user", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<AuthenticatedUserDto> updateUser(@RequestBody AuthenticatedUserDto user) {
        log.debug("REST request to update User : {}", user);
        Objects.requireNonNull(user);
        return ResponseEntity
                .ok()
                .body(authenticatedUserService.update(user));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/user/{id}", produces = {"application/json"}, consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<AuthenticatedUserDto> partialUpdateUser(
            @PathVariable(value = "id") final Long id,
            @RequestBody AuthenticatedUserDto user) {
        log.debug("REST request to partialUpdateUser Id: {} User : {}", id, user);

        user.setToken(user.getToken());
        return ResponseEntity
                .ok()
                .body(authenticatedUserService.partialUpdate(id, user));
    }

 */
}
