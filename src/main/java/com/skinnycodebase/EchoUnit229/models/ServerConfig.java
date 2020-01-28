package com.skinnycodebase.EchoUnit229.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ServerConfig {


    @Id
    private String guildId;

    private String publicListingChannelId;

    private String mentionRoleID;



}
