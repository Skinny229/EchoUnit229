package com.skinnycodebase.EchoUnit229.controllers;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.EchoGamePrivate;
import com.skinnycodebase.EchoUnit229.models.EchoUpdateResponseBody;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import com.skinnycodebase.EchoUnit229.service.GuildConfigService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
@RequestMapping("/api/v2")
public class EchoProtocolUpdateController {


    private static final Logger logger = LoggerFactory.getLogger(EchoProtocolUpdateController.class);

    @Autowired
    private EchoGameService echoGameService;
    @Autowired
    private GuildConfigService guildConfigService;

    @PostMapping(path = "/updateGame", consumes = "application/json")
    public HttpStatus updateGame(@RequestBody EchoUpdateResponseBody body){
        if(body.getSessionid() == null || body.getClient_name() == null)
            return HttpStatus.BAD_REQUEST;
        FiggyUtility.updateAutoPublicGame(body);
        return HttpStatus.OK;
    }

    @PostMapping(path = "/createPubGame", consumes = "application/json")
    public HttpStatus createPub(@RequestBody EchoUpdateResponseBody body){
        if(body.getSessionid() == null || body.getClient_name() == null)
            return HttpStatus.BAD_REQUEST;

        FiggyUtility.registerAutoPublicGame(body);
        return HttpStatus.OK;
    }


}
