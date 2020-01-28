package com.skinnycodebase.EchoUnit229.controllers;


import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/v2")
public class GameJoinerController {


    @Autowired
    EchoGameService echoGameService;

    @GetMapping("/genGame")
    @ResponseStatus(HttpStatus.OK)
    public String createGame(@RequestParam String lobbyID) {
        return "joinGameOld.html";
    }



}
