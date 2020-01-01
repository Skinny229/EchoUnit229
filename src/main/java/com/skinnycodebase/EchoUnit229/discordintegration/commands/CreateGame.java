package com.skinnycodebase.EchoUnit229.discordintegration.commands;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateGame {


    public static void run(MessageReceivedEvent event){

        MessageChannel channelToDisplay = getChannelByGuild();


         ArrayList<String> cmdbreakdown = new ArrayList(Arrays.asList(event.getMessage().getContentRaw().split(" ")));

        String ID = cmdbreakdown.get(1);

        //String playersRequested = cmdbreakdown.get(2);


        if(isValidID(ID))
        {
            StringBuilder fullMessage = new StringBuilder();

            fullMessage.append("Hey ");
            fullMessage.append(getRoleToCallByGuild(event.getGuild()));
            fullMessage.append(" ha");

        }


    }

    private static boolean isValidID(String a){
        return a.length() == 9;
    }



    /*
            Looks internally for set ouput dependent on the server
     */
    private static MessageChannel getChannelByGuild(){

        //TODO impement me
        return null;
    }

    private static String getRoleToCallByGuild(Guild guild){
        //TODO Implement me
        return null;
    }


}
