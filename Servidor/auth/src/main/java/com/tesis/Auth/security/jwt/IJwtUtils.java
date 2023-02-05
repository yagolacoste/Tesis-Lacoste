package com.tesis.Auth.security.jwt;


import com.tesis.Auth.config.UserDetailsImpl;
import org.springframework.security.core.Authentication;

public interface IJwtUtils {
    String generateJwtToken(UserDetailsImpl userDetailsImpl);

    public String getUserNameFromJwtToken(String token);

    public boolean validateJwtToken(String authToken);

    Long getIdFromJwtToken(String jwt);
}
