package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Install {
    public static void run(MessageReceivedEvent event) {
        event.getChannel().sendMessage("To install the Echo Protocol, " +
                "please follow the link below and install\n" +
                "https://github.com/Skinny229/EchoUnit229/releases/tag/1.0.0").queue();
    }
}
