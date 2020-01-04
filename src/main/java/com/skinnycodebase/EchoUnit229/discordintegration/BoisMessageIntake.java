package com.skinnycodebase.EchoUnit229.discordintegration;

import com.skinnycodebase.EchoUnit229.discordintegration.commands.CreateGame;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class BoisMessageIntake extends ListenerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(BoisMessageIntake.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        String rawMsg = event.getMessage().getContentRaw();

        if(rawMsg.startsWith("-")){

            logger.info("Possible command attempt by ["+ event.getMember().getNickname() +"] with: " + rawMsg);

            String command;
            if(rawMsg.indexOf(' ') == -1)
                command = rawMsg.substring(1);
            else
                command = rawMsg.substring(1,rawMsg.indexOf(' '));

            switch(command.toUpperCase()){

                case "CREATEGAME":
                    CreateGame.run(event);
                    break;
                case "FLUSH":
                    break;
                case "INSTALLHELP":
                    break;
                case "LISTGAMES":
                    break;
                case "DELETEGAME":
                    break;
                default:
            }
        }


   }


}
