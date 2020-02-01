package com.skinnycodebase.EchoUnit229.models;


public class EchoUpdateResponseBody {


    private String sessionid;

    private String client_name;

    private String game_clock_display;

    private String game_status;

    private String[] players;


    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getGame_clock_display() {
        return game_clock_display;
    }

    public void setGame_clock_display(String game_clock_display) {
        this.game_clock_display = game_clock_display;
    }

    public String getGame_status() {
        return game_status;
    }

    public void setGame_status(String game_status) {
        this.game_status = game_status;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

}
