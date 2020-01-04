package com.skinnycodebase.EchoUnit229.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public String getTestingHome(){
        return "Hello. You should not be here just yet";
    }

}
