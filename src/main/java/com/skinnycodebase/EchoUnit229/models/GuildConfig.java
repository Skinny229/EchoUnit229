package com.skinnycodebase.EchoUnit229.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GuildDetails {

    @Id
    private String guildId;

    private String publicListingChannelId;

    private String mentionRoleID;


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
}
