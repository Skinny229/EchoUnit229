package com.skinnycodebase.EchoUnit229.discordintegration;

import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class PrivateMessageHandler extends ListenerAdapter {



    /*WIP*/
    /*
    *
    *  TO be later implemented. Goal being asking the users to confirm their private game with a list of players to be invited
    * */
    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {

        String rawMsg = event.getMessage().getContentRaw();

        if(rawMsg.startsWith("-")){

            String command;
            if(rawMsg.indexOf(' ') == -1)
                command = rawMsg.substring(1);
            else
                command = rawMsg.substring(1,rawMsg.indexOf(' '));

            switch(command.toUpperCase()){
                case "CONFIRM":
                    break;
                default:
            }
        }
    }

}
