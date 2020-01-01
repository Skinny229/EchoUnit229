package com.skinnycodebase.EchoUnit229.discordintegration;

import com.skinnycodebase.EchoUnit229.discordintegration.commands.CreateGame;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;

public class BoisMessageIntake extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        String rawMsg = event.getMessage().getContentRaw();

        if(rawMsg.startsWith("-")){
            String command = rawMsg.substring(1,rawMsg.indexOf(' '));

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
