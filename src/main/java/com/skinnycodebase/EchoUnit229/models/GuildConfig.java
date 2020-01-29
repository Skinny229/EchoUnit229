package com.skinnycodebase.EchoUnit229.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GuildConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String guildId;

    private String publicListingChannelId;

    private String mentionRoleID;

    private int lastGameCount;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getPublicListingChannelId() {
        return publicListingChannelId;
    }

    public void setPublicListingChannelId(String publicListingChannelId) {
        this.publicListingChannelId = publicListingChannelId;
    }

    public String getMentionRoleID() {
        return mentionRoleID;
    }

    public void setMentionRoleID(String mentionRoleID) {
        this.mentionRoleID = mentionRoleID;
    }

    public int getLastGameCount() {
        return lastGameCount;
    }

    public void setLastGameCount(int lastGameCount) {
        this.lastGameCount = lastGameCount;
    }
}
