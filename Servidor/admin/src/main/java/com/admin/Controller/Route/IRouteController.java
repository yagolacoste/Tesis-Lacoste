package com.admin.Controller.Route;

import com.admin.Dto.RouteNewRequestDto;
import com.admin.Models.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequestMapping(IRouteController.PATH)
public interface IRouteController {
    static final String PATH ="/route";

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"/get"},produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<Route> getById(@RequestParam (value = "id")Long id);

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = {"/add"},consumes = MediaType.APPLICATION_JSON_VALUE)
    void addRoute(@RequestBody RouteNewRequestDto route);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"/list"},produces = MediaType.APPLICATION_JSON_VALUE)
    List<Route> getRoutes();
}
