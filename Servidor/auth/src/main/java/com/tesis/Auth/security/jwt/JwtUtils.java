package com.tesis.Auth.security.jwt;

import com.tesis.Auth.config.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils implements IJwtUtils{
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${Bicycle.app.jwtSecret}")
    private String jwtSecret;

    @Value("${Bicycle.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Override
    public String generateJwtToken(UserDetailsImpl userDetailsServiceImpl){
        return getUserNameFromJwtToken(userDetailsServiceImpl.getUsername());
    }

    @Override
    public String getUserNameFromJwtToken(String token) {
        return Jwts.builder().setSubject(token).setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpirationMs)).signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    @Override
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    @Override
    public Long getIdFromJwtToken(String jwt) {
        return null;
    }


}
