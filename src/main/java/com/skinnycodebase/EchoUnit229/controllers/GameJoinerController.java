package com.skinnycodebase.EchoUnit229.controllers;


import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/v2")
public class GameJoinerController {


    @Autowired
    EchoGameService echoGameService;

    @GetMapping("/joinGame")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView createGame(@RequestParam String lobbyId) {
        ModelAndView  returned = new ModelAndView("joinGame.html");
        returned.addObject("lobbyId", lobbyId);
        return returned;
    }



}
