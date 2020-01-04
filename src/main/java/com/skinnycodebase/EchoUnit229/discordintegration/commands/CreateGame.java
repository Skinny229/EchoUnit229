package com.skinnycodebase.EchoUnit229.discordintegration.commands;


import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateGame {


    public static void run(MessageReceivedEvent event) {


        ArrayList<String> cmdbreakdown = new ArrayList(Arrays.asList(event.getMessage().getContentRaw().split(" ")));


        String type = cmdbreakdown.get(1);

        String ID = cmdbreakdown.get(2);

        //String playersRequested = cmdbreakdown.get(2);


        if (type.equals("public")) {
            if (isValidID(ID)) {
                StringBuilder fullMessage = new StringBuilder();

                String boisRole = event.getGuild().getRoleById("645047793500815387").getAsMention();

                fullMessage.append("Hey ");
                fullMessage.append(boisRole);//@bois
                fullMessage.append(" a public game has been created!");

            }
        }

    }


    private static boolean isValidID(String a) {
        return a.length() == 9;
    }
}