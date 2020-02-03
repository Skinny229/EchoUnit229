package com.skinnycodebase.EchoUnit229.models;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="echo_game_public")
public class EchoGamePublic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //Discord player ID from the game generated
    private String playerID;

    private String playerName;

    //Guild id generated from
    private String guildId;

    private String messageId;

    //EchoVR lobbyID
    private String sessionid;

    private LocalDateTime timeGameCreated;

    private boolean isInUse;

    private LocalDateTime timeLastLiveUpdate;


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

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String lobbyID) {
        this.sessionid = lobbyID;
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

    public boolean isInUse() {
        return isInUse;
    }

    public void setInUse(boolean inUse) {
        isInUse = inUse;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public LocalDateTime getTimeLastLiveUpdate() {
        return timeLastLiveUpdate;
    }

    public void setTimeLastLiveUpdate(LocalDateTime timeLastLiveUpdate) {
        this.timeLastLiveUpdate = timeLastLiveUpdate;
    }
}
