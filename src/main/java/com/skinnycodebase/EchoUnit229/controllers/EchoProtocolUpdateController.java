package com.skinnycodebase.EchoUnit229.controllers;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.models.EchoLiveRequestBody;
import com.skinnycodebase.EchoUnit229.models.EchoUpdateResponseBody;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import com.skinnycodebase.EchoUnit229.service.GuildConfigService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping(path = "/publicListing", consumes = "application/json", produces = "application/json")
    public HttpStatus updateGame(@RequestBody EchoUpdateResponseBody body){
        if(body.getSessionid() == null || body.getClient_name() == null)
            return HttpStatus.BAD_REQUEST;
        FiggyUtility.updateAutoPublicGame(body);
        return HttpStatus.OK;
    }

    @PostMapping(path = "/publicListing", consumes = "application/json", produces = "application/json")
    public HttpStatus createPub(@RequestBody EchoLiveRequestBody body){


        if(body.getSessionid() == null || body.getClient_name() == null)
            return HttpStatus.BAD_REQUEST;

        EchoGamePublic game = echoGameService.getPublicGameBySessionId(body.getSessionid());

        if(game == null)
            FiggyUtility.registerAutoPublicGame(body);
        else if(game.isInUse())
            return HttpStatus.CONFLICT;
        else if(echoGameService.hasActivePublicIn(body.getGuild_id(), body.getDiscord_user_id())){
            echoGameService.decommissionGame(body.getGuild_id(),body.getDiscord_user_id());
            FiggyUtility.registerAutoPublicGame(body);
        }

        return HttpStatus.OK;
    }


}
