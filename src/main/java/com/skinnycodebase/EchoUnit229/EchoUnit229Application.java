package com.skinnycodebase.EchoUnit229;

import com.skinnycodebase.EchoUnit229.discordintegration.BoisMessageIntake;
import com.skinnycodebase.EchoUnit229.discordintegration.PrivateMessageHandler;
import com.skinnycodebase.EchoUnit229.discordintegration.RedeployHandler;
import com.skinnycodebase.EchoUnit229.discordintegration.commands.CreateGame;
import com.skinnycodebase.EchoUnit229.discordintegration.commands.DelMyGame;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@SpringBootApplication
public class EchoUnit229Application {

    private static final Logger logger = LoggerFactory.getLogger(EchoUnit229Application.class);

    public static void main(String[] args) {
        logger.info("Starting Spring REST Service");
        SpringApplication.run(EchoUnit229Application.class, args);
    }


    /*
    * Current method to make sure beans for the EchoGameService are instanciated correctly for CreateGame and DelMyGame commands
    * which require no static instance of the EchoGameService, Commands for the bot are seen as Spring Components
    * */
    private CreateGame createGame;
    private  DelMyGame delMyGame;

    @Autowired
    public EchoUnit229Application(CreateGame createGame, DelMyGame delMyGame) {
        this.createGame = createGame;
        this.delMyGame = delMyGame;
    }

    @Autowired
    Environment environment;

    /*
    * Annotation makes sure spring is has started before running this method
    * */
    @EventListener(ApplicationReadyEvent.class)
    public void botStartup() {

        logger.info("Starting with address[{}] and hostname[{}]", InetAddress.getLoopbackAddress().getHostAddress(), InetAddress.getLoopbackAddress().getHostName());
        //Boot JDA (Discord bot)
        JDA jda = null;
        try {
            jda = new JDABuilder(DeploymentSettings.BOT_AUTH_TOKEN).build();
        } catch (Exception e) {
            //Exit in case of the bot not created successfully(Usually due to bad auth token
            logger.error(e.toString());
            logger.error("Shutting down to prevent start up without bot");
            System.exit(-1);
        }


        /*
        *  Bot is implemented using the ListenerAdapter class
        * */
        jda.addEventListener(new RedeployHandler());
        jda.addEventListener(new PrivateMessageHandler());
        jda.addEventListener(new BoisMessageIntake(createGame, delMyGame));

    }
}
