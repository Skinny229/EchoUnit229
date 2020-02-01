package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Help  {


    public static void run(MessageReceivedEvent event){

        String message = "";

        event.getChannel().sendMessage(message).queue();

    }

}
