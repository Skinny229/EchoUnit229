package com.skinnycodebase.EchoUnit229;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DeploymentSettings {




    public static final String BOT_AUTH_TOKEN = System.getenv("DISCORD_UNIT229_TOKEN");

    public static final String BOIS_ROLE_ID = System.getenv("BOIS_ROLE_ID");

    public static final String LFGBOT_CHANNEL_ID = System.getenv("LFGBOT_ID");

    public static final String BOT_ID = System.getenv("BOT_ID");

    public static final String ESO_GUILD_ID = System.getenv("ESO_GUILD_ID");

    public static final String DEV_ID = System.getenv("DEV_ID");

    public static final String API_CONTROLLER_VERSION = "v1";

}
