package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Help  {


    public static void run(MessageReceivedEvent event){
            TextChannel channel = event.getTextChannel();
            User user = event.getAuthor();

            //If you wanna pm someone use the static FiggyUtilities Method
            //FiggyUtilities.privateMessage().....



    }

}
