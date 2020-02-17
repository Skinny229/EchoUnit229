package com.skinnycodebase.EchoUnit229.controllers;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.models.responsebody.EchoLiveRequestBody;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnit4.class)
public class V2ControllerTests {

    @InjectMocks
    EchoProtocolUpdateController controller;

    @Mock
    EchoGameService echoGameService;

    @Test
    public void validPostLiveUpdateRequest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));



        EchoLiveRequestBody live = new EchoLiveRequestBody();
        live.setClient_name("skinny");
        live.setConfirmation_code("123");
        live.setGuild_id("4967");
        live.setId(2);
        live.setDiscord_user_id("123");
        live.setSessionid("ayylmao");

        EchoGamePublic game = new EchoGamePublic();
        game.setId(2);
        game.setInUse(true);
        game.setGuildId("4967");
        game.setPlayerID("123");
        game.setConfirmationCode("123");

       when(echoGameService.getPublicGameById(any(Long.class))).thenReturn(game);
       when(echoGameService.getGameByUserGuild(any(String.class),any(String.class))).thenReturn(game);
       //when(FiggyUtility.updateAllPublicGamesList(any(Guild.class)))

       new FiggyUtility(echoGameService, null);

       HttpStatus responseEntity = controller.createPub(live);

       Assert.assertEquals(responseEntity, HttpStatus.OK);

    }


}
