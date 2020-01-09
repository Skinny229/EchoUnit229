package com.skinnycodebase.EchoUnit229.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/ap1/v2")
public class GameJoinerController {





    @GetMapping("/genGame")
    @ResponseStatus(HttpStatus.OK)
    public String createGame(@RequestParam String gameID){
        return "joinGame.html";
    }
}
