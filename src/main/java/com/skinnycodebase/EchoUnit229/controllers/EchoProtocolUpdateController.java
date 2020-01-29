package com.skinnycodebase.EchoUnit229.controllers;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.EchoUpdateResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class EchoProtocolUpdateController {

    @PostMapping(path = "/updateGame", consumes = "application/json", produces = "application/json")

    public HttpStatus updateGame(@RequestBody EchoUpdateResponseBody body){
        FiggyUtility.updateFromRequest(body);
        return HttpStatus.OK;
    }


}
