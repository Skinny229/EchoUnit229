package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class Help  {


    public static void run(MessageReceivedEvent event){
            TextChannel channel = event.getTextChannel();


        StringBuilder msg = new StringBuilder();
        msg.append("```markdown\n#Commands \n" +
                   "* -help \n\t* Available in PM format as well\n\n");
        msg.append("* -creategame [sessionid] [private/public] \n\t* Create public game for everyone to join\n" +
                    "\t* This command is generated for you within the GameInviter.exe, all you need to type is public or private"+
                    "\n\t* Visit http://echovrprotocol.com to use live updates public game instead\n\n");
        msg.append("* -delmygame \n\t * deletes active public game in the Guild's public listings\n\n");
        msg.append("# TO JOIN GAMES\n\n");
        msg.append("1) Click on the links either by pm or in the public listings\n");
        msg.append("2) Click on the join as player option (Make sure you have the echo protocol installed)\n\n");
        if(Objects.requireNonNull(event.getMember()).isOwner())
            msg.append("### FOR SERVER OWNERS: `-set [listings/role] [id] set public text channel to display public games or set the role to mention when a game is created\n```");
        else
            msg.append("```");

        channel.sendMessage(msg.toString()).queue();

    }

}
