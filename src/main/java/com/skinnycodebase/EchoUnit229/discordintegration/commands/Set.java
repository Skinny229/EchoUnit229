package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import com.skinnycodebase.EchoUnit229.DeploymentSettings;
import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Set {

    public static void run(MessageReceivedEvent event){

        User user = event.getAuthor();


        if( !event.getAuthor().getId().equals(DeploymentSettings.DEV_ID) && !(event.getGuild().getOwner().getUser().equals(user))) {
            FiggyUtility.privateMessage(user, "It seems you cannot execute the command as you dont have permissions");
            return;
        }

        ArrayList<String> cmdbreakdown = new ArrayList<>(Arrays.asList(event.getMessage().getContentRaw().split(" ")));

        String setType = cmdbreakdown.get(1);

        String value = cmdbreakdown.get(2);

        Optional<GuildConfig> configOp = FiggyUtility.getConfig(event.getGuild().getId());

        if(!configOp.isPresent()){

            FiggyUtility.privateMessage(event.getAuthor(), "Something has gone really really wrong.\n " +
                    "Please Copy paste skinny with the following " +
                    "```\nConfig Retrieve Failure, ServerID: " + event.getGuild().getId()+
                    "\n```");

            return;
        }

        GuildConfig a = configOp.get();

        switch(setType.toUpperCase())
        {
            case "LISTINGS":
                if(event.getGuild().getTextChannelById(value) == null){
                    FiggyUtility.privateMessage(user, "Invalid text channel");
                    return;
                }
                a.setPublicListingChannelId(value);
                break;
            case "ROLE":
                if(event.getGuild().getRoleById(value) == null){
                    FiggyUtility.privateMessage(user, "Invalid ROLE");
                    return;
                }
                a.setMentionRoleID(value);
                break;
            default:
                FiggyUtility.privateMessage(user, "Invalid option");
                return;
        }
        FiggyUtility.saveConfig(a);
        FiggyUtility.privateMessage(user, "Successful Set!");

    }
}
