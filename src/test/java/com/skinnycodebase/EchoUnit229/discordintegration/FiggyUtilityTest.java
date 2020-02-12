package com.skinnycodebase.EchoUnit229.discordintegration;

import org.junit.Assert;
import org.junit.jupiter.api.Test;



class FiggyUtilityTest {


    @Test
    public void httpProtocolLinkTest(){
        String id = "123123125233121413";

        String expected = "http://echovrprotocol.com/api/v2/joinGame?lobbyId="+id;

        String actual = FiggyUtility.createLinkHttp(id);

        Assert.assertEquals(expected,actual);
    }


    @Test
    public void httpProtocolLinkTest2(){

        String expected = "http://echovrprotocol.com/api/v2/joinGame?lobbyId=";

        String actual = FiggyUtility.createLinkHttp(null);

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void httpProtocolLinkTest3(){

        String expected = "http://echovrprotocol.com/api/v2/joinGame?lobbyId=5BB10EC1-C2DB-41CB-9540-9E1DC6BA902B";

        String actual = FiggyUtility.createLinkHttp("5BB10EC1-C2DB-41CB-9540-9E1DC6BA902B");

        Assert.assertEquals(expected,actual);
    }





}