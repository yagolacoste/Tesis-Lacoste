package com.tesis.Auth.security.jwt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

            logger.error("Unauthorized error: {}", authException.getMessage());
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        OutputStream out = response.getOutputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(out, new ErrorMessage( HttpServletResponse.SC_UNAUTHORIZED,"bad-credentials", authException.getMessage()));
//        out.flush();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");

//            throw new BadRequestException(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), authException.getMessage());
    }
}
