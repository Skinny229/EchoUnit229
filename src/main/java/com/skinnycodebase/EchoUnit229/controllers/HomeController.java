package com.skinnycodebase.EchoUnit229.controllers;

import com.skinnycodebase.EchoUnit229.models.EchoGameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {


    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public String getTestingHome(){

        return "home.html";
    }

}
