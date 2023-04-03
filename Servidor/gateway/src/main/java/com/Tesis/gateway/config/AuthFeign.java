package com.Tesis.gateway.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service",url = "http://localhost:8081/auth")
public interface AuthFeign {

    @PostMapping(value = "/validate",produces = MediaType.APPLICATION_JSON_VALUE)
    public RefreshTokenDto validate(@RequestParam String token);
}
