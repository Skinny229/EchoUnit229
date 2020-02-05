package com.skinnycodebase.EchoUnit229.models.responsebody;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat
public class EchoLiveRequestBody {

    private String confirmation_code;

    private String discord_user_id;

    private long id;

    private String guild_id;

    private String sessionid;

    private String client_name;

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

    public String getDiscord_user_id() {
        return discord_user_id;
    }

    public void setDiscord_user_id(String discord_user_id) {
        this.discord_user_id = discord_user_id;
    }

    public String getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(String guild_id) {
        this.guild_id = guild_id;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
