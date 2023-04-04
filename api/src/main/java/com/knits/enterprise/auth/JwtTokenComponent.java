package com.knits.enterprise.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.knits.enterprise.config.ApplicationProperties;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.exceptions.ExceptionCodes;
import com.knits.enterprise.exceptions.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@AllArgsConstructor
@Slf4j
public class JwtTokenComponent {

    private ApplicationProperties config;

    public String generateToken(UserDto userDetails) {
        Algorithm algorithm = Algorithm.HMAC512(config.getSecret());

        return JWT.create()
                .withIssuer(config.getIssuer())
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date((new Date()).getTime() + config.getJwtExpirationMillis()))
                .withClaim("username", userDetails.getUsername())
                .withClaim("role", userDetails.getRoleName())
                .sign(algorithm);
    }

    public String getUsernameFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(config.getSecret());
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(config.getIssuer()).build();
        DecodedJWT jwt = verifier.verify(token);
        if (isTokenExpired(jwt)) {
            throw new UserException("Token expired", ExceptionCodes.JWT_TOKEN_EXPIRED);
        }
        return jwt.getSubject();
    }

    public boolean isTokenExpired(DecodedJWT jwt) {
        Date now = new Date();
        return now.after(jwt.getExpiresAt());
    }


}
