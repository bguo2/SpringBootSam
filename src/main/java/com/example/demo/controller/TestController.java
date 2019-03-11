package com.example.demo.controller;

import com.example.demo.dtos.UserDto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import com.example.demo.managers.UserDaoService;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserDaoService manager;

    @RequestMapping("/hello")
    public String Hello() {
        return "hello";
    }

    @RequestMapping(path = "/queryall", produces = "application/json")
    @Async
    public CompletableFuture<Resources<UserDto>> getAllUsers() {
        List<UserDto> userDtos = manager.getUsers();
        //HATEOAS
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(methodOn(this.getClass()).getAllUsers());
        Resources<UserDto> resources = new Resources<>(userDtos, linkTo.withRel("getAllUsers"));
        return CompletableFuture.completedFuture(resources);
    }

    @GetMapping(path = "/query/{email}", produces = "application/json")
    public Resource<UserDto> getUser(@PathVariable String email) {
        UserDto user = manager.getUser(email);
        if (user == null)
            throw new NotFoundException("email:" + email);
        //HATEOAS
        Resource<UserDto> resource = new Resource<>(user);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(methodOn(this.getClass()).getUser(email));
        resource.add(linkTo.withRel("getUser"));
        return resource;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        try {
            UserDto savedUser = manager.createUser(user);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(savedUser.getEmail()).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            //starting from Spring boot 5 (2.0.5): use ResponseStatusException to throw any status code.
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }
}
