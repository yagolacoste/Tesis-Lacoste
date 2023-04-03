package com.Tesis.admin.Controller.Route;


import com.Tesis.admin.Dto.Route.RouteNewRequestDto;
import com.Tesis.admin.Models.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(IRouteController.PATH)
public interface IRouteController {
    static final String PATH ="/route";

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"/get"},produces = MediaType.APPLICATION_JSON_VALUE)
    Route getById(@RequestParam (value = "id")String id);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = {"/add"},consumes = MediaType.APPLICATION_JSON_VALUE)
    void addRoute(@RequestBody RouteNewRequestDto route);

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping(path = {"/list"},produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("/list")
    List<Route> getRoutes();
}
