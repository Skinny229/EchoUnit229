package com.skinnycodebase.EchoUnit229.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Basic Home Controller to just shoo off people
 */
@Controller
public class HomeController {


    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public String getTestingHome() {
        return "home.html";
    }

}
