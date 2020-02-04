package com.skinnycodebase.EchoUnit229.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonFormat
public class EchoUpdateResponseBody {

    private String sessionid;

    private String discordUserId;

    private String client_name;

    private String game_clock_display;

    private String game_status;

    private String players[];

    private int orange_points;

    private int blue_points;

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


    public int getBlue_points() {
        return blue_points;
    }

    public void setBlue_points(int blue_points) {
        this.blue_points = blue_points;
    }

    public int getOrange_points() {
        return orange_points;
    }

    public void setOrange_points(int orange_points) {
        this.orange_points = orange_points;
    }

    public String getDiscordUserId() {
        return discordUserId;
    }

    public void setDiscordUserId(String discordUserId) {
        this.discordUserId = discordUserId;
    }
}
