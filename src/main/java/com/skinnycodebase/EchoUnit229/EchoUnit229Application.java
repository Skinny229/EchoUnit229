package com.skinnycodebase.EchoUnit229;

import com.skinnycodebase.EchoUnit229.discordintegration.BoisMessageIntake;
import com.skinnycodebase.EchoUnit229.discordintegration.MessageIntake;
import com.skinnycodebase.EchoUnit229.discordintegration.RedeployHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EchoUnit229Application {

    private static final Logger logger = LoggerFactory.getLogger(EchoUnit229Application.class);

    public static void main(String[] args) {
        logger.info("Starting Spring REST Service");
        SpringApplication.run(EchoUnit229Application.class, args);
    }



    @EventListener(ApplicationReadyEvent.class)
    public static void botStartup() {
        JDA jda = null;
        try {
            jda = new JDABuilder(DeploymentSettings.BOT_AUTH_TOKEN).build();
        } catch (Exception e) {
            logger.error(e.toString());
        }


        jda.addEventListener(new RedeployHandler());
        jda.addEventListener(new MessageIntake());
        jda.addEventListener(new BoisMessageIntake());


    }
}
