package com.Tesis.auth.security.Jwt;

import com.Tesis.auth.advise.BadRequestException;
import com.Tesis.auth.advise.ErrorCodes;
import com.Tesis.auth.security.Jwt.exception.TokenRefreshException;
import com.Tesis.auth.security.Service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${Bicycle.app.jwtSecret}")
    private String jwtSecret;

    @Value("${Bicycle.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        return generateTokenFromEmail(userPrincipal.getUsername());
    }

    public String generateTokenFromEmail(String email) {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            //throw new TokenRefreshException(ErrorCodes.TOKEN_EXPIRATION.getCode(), e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            //throw new TokenRefreshException(ErrorCodes.TOKEN_EXPIRATION.getCode(), e.getMessage());
        } catch ( ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());//mensaje de expiracion
          // throw new TokenRefreshException(ErrorCodes.TOKEN_EXPIRATION.getCode(), e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
           // throw new TokenRefreshException(ErrorCodes.TOKEN_EXPIRATION.getCode(), e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
           // throw new TokenRefreshException(ErrorCodes.TOKEN_EXPIRATION.getCode(), e.getMessage());
        }
        return false;
    }
}
