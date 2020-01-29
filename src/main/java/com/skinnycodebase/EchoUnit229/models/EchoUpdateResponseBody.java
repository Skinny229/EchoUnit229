package com.skinnycodebase.EchoUnit229.models;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;

import java.util.Optional;

public class EchoUpdateResponseBody {

    private String uniqueId;

    private String sessionId;

    private String client_name;

    private String[] players;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

//    public EchoGamePublic topub(){
//        Optional<EchoGamePublic> pubOp = FiggyUtility.get
//        EchoGamePublic pub = ? new EchoGamePublic();
//        pub.setGuildId("669565853880025107");
//        pub.setId(uniqueId);
//        pub.set
//        return pub;
//    }
}
