package com.skinnycodebase.EchoUnit229.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class EchoGamePublic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Discord player ID from the game generated
    private String playerID;

    //Guild id generated from
    private String guildId;

    //EchoVR lobbyID
    private String lobbyID;

    private LocalDateTime timeGameCreated;

    private int desiredPlayers;

    private boolean isInUse;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(String lobbyID) {
        this.lobbyID = lobbyID;
    }

    public LocalDateTime getTimeGameCreated() {
        return timeGameCreated;
    }

    public void setTimeGameCreated(LocalDateTime timeGameCreated) {
        this.timeGameCreated = timeGameCreated;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public int getDesiredPlayers() {
        return desiredPlayers;
    }

    public void setDesiredPlayers(int desiredPlayers) {
        this.desiredPlayers = desiredPlayers;
    }

    public boolean isInUse() {
        return isInUse;
    }

    public void setInUse(boolean inUse) {
        isInUse = inUse;
    }
}
