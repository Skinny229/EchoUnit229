package com.skinnycodebase.EchoUnit229.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="echo_game_private")
public class EchoGamePrivate {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean isCreator;

    private String userId;

    private String lobbyId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCreator() {
        return isCreator;
    }

    public void setCreator(boolean creator) {
        isCreator = creator;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }
}
