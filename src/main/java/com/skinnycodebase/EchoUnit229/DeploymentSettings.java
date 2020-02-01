package com.skinnycodebase.EchoUnit229;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DeploymentSettings {


    public static final String BOT_AUTH_TOKEN = System.getenv("AUTH_TOKEN");

    public static String BOT_ID;

    public static final String ESO_GUILD_ID = "623999906646065172";

    public static final String DEV_ID = "142673915964030976";

    public static final String API_CONTROLLER_VERSION = "v2";

}
