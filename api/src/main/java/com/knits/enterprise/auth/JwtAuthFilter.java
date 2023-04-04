package com.knits.enterprise.auth;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.service.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenComponent jwtTokenComponent;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {

        if (req.getServletPath().contains("/api/login")) {
            chain.doFilter(req, res);
            return;
        }

        String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(Constants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        String token = authorizationHeader.substring(Constants.TOKEN_PREFIX.length());
        String username = jwtTokenComponent.getUsernameFromToken(token);
        UserDto authenticatedUser = (UserDto) userService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(
                authenticatedUser,
                Constants.NO_CREDENTIALS,
                authenticatedUser.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(req, res);
    }
}
