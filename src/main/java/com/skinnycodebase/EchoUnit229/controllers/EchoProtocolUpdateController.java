package com.skinnycodebase.EchoUnit229.controllers;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.models.responsebody.EchoCloseLiveListingResponseBody;
import com.skinnycodebase.EchoUnit229.models.responsebody.EchoLiveRequestBody;
import com.skinnycodebase.EchoUnit229.models.responsebody.EchoUpdateResponseBody;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
@RequestMapping("/api/v2")
public class EchoProtocolUpdateController {


    private static final Logger logger = LoggerFactory.getLogger(EchoProtocolUpdateController.class);

    private final EchoGameService echoGameService;

    @Autowired
    public EchoProtocolUpdateController(EchoGameService echoGameService) {
        this.echoGameService = echoGameService;
    }

    @PutMapping(path = "/publicListing", consumes = "application/json", produces = "application/json")
    public HttpStatus updateGame(@RequestBody EchoUpdateResponseBody body){
        if(body.getSessionid() == null || body.getClient_name() == null)
            return HttpStatus.BAD_REQUEST;


        EchoGamePublic game = echoGameService.getPublicLiveGameByOculusName(body.getClient_name());
        if(game != null && !game.isInUse()) {
            echoGameService.decommissionGame(game.getGuildId(),game.getPlayerID());
            return HttpStatus.BAD_REQUEST;
        }
        if(game == null)
        {
            return HttpStatus.BAD_REQUEST;
        }

        FiggyUtility.updateAutoPublicGame(body);
        return HttpStatus.OK;
    }

    @PostMapping(path = "/publicListing", consumes = "application/json", produces = "application/json")
    public HttpStatus createPub(@RequestBody EchoLiveRequestBody body){


        if(body.getSessionid() == null || body.getClient_name() == null)
            return HttpStatus.BAD_REQUEST;

        EchoGamePublic game = echoGameService.getPublicGameById(body.getId());
        EchoGamePublic sessionIdGame = echoGameService.getPublicGameBySessionId(body.getSessionid());

       if(!game.isInUse() || (sessionIdGame!=null && sessionIdGame.isConnectedToLiveClient()))
           return HttpStatus.CONFLICT;
       else if(sessionIdGame != null &&  sessionIdGame.isInUse() && sessionIdGame.isConnectedToLiveClient() && game.getId() != sessionIdGame.getId())
           return HttpStatus.ALREADY_REPORTED;
       else if(game.getConfirmationCode().equals(body.getConfirmation_code()))
       {
           FiggyUtility.confirmAutoPublicGame(body);
           return HttpStatus.OK;
       }

       return HttpStatus.UNAUTHORIZED;

    }

    @PostMapping(value = "/closePublicListing", consumes = "application/json")
    public HttpStatus closePub(@RequestBody EchoCloseLiveListingResponseBody body){
        if(body.getConfirmation_code() == null || body.getId() == 0)
            return HttpStatus.BAD_REQUEST;
        EchoGamePublic game = echoGameService.getPublicGameById(body.getId());
        if(game!=null && game.getConfirmationCode().equals(body.getConfirmation_code())){

        }
        return HttpStatus.OK;
    }

}
