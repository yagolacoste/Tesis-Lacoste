package com.admin.Controller.AppUserHasRoute;

import com.admin.Dto.AppUserRouteRequestDto;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
@RequestMapping(IAppuserHasRouteController.PATH)
public interface IAppuserHasRouteController {

    static final String PATH ="/appuserroute";


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path={"/add"},consumes = MediaType.APPLICATION_JSON_VALUE)
    void addAppuserRoute(@RequestBody AppUserRouteRequestDto appUserRouteRequestDto);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path={"/get"},consumes = MediaType.APPLICATION_JSON_VALUE)
    void ();

}
