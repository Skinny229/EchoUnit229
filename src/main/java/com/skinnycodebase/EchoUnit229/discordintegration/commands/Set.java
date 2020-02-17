package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import com.skinnycodebase.EchoUnit229.DeploymentSettings;
import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Set {

    public static void run(MessageReceivedEvent event) {


        long valueId = -1;

        Member member = event.getMember();
        User user = event.getAuthor();

        if (member.hasPermission(Permission.ADMINISTRATOR) && !user.getId().equals(DeploymentSettings.DEV_ID)) {
            FiggyUtility.privateMessage(user, "It seems you do not have administrator powers. So I cant let you sorry");
            return;
        }

        ArrayList<String> cmdbreakdown = new ArrayList<>(Arrays.asList(event.getMessage().getContentRaw().split(" ")));

        String settingType = cmdbreakdown.get(1);

        String settingValue = cmdbreakdown.get(2);

        //Confirm that we have the guild config other wise exit
        Optional<GuildConfig> configOp = FiggyUtility.getConfig(event.getGuild().getId());
        if (!configOp.isPresent()) {
            FiggyUtility.privateMessage(event.getAuthor(), "Something has gone really really wrong.\n " + "Please Copy paste skinny with the following " +
                    "```\nConfig Retrieve Failure, ServerID: " + event.getGuild().getId() +
                    "\n```");
            return;
        }
        GuildConfig guildConfig = configOp.get();


        //
        switch (settingType.toUpperCase()) {
            case "LISTINGS":
                List<TextChannel> channelByString = event.getGuild().getTextChannelsByName(settingValue, false);
                TextChannel channelById;
                try {
                    channelById = event.getGuild().getTextChannelById(settingValue);
                } catch (Exception e) {
                    channelById = null;
                }
                if (channelByString.size() > 1) {
                    FiggyUtility.privateMessage(user, "Too many text channels with a similar name. Either be more specific or provide the id(Enabled in the discord developer mode)");
                    return;
                } else if (channelByString.size() == 1) {
                    guildConfig.setPublicListingChannelId(channelByString.get(0).getId());
                } else if (channelById != null) {
                    guildConfig.setPublicListingChannelId(settingValue);
                } else {
                    FiggyUtility.privateMessage(user, "I cannot find anything with Name/ID similar to what you provided: " + settingValue);
                    return;
                }
                break;
            case "ROLE":
                List<Role> roleByString = event.getGuild().getRolesByName(settingValue, false);
                Role roleById;
                try {
                    roleById = event.getGuild().getRoleById(settingValue);
                } catch (Exception e) {
                    roleById = null;
                }
                if (roleByString.size() > 1) {
                    FiggyUtility.privateMessage(user, "Too many roles with a similar name. Either be more specific or provide the id(Enabled in the discord developer mode)");
                    return;
                } else if (roleByString.size() == 1) {
                    guildConfig.setMentionRoleID(roleByString.get(0).getId());
                } else if (roleById != null) {
                    guildConfig.setMentionRoleID(settingValue);
                } else {
                    FiggyUtility.privateMessage(user, "I cannot find anything with Name/ID similar to what you provided: " + settingValue);
                    return;
                }
                break;
            default:
                FiggyUtility.privateMessage(user, "Invalid SET type");
                return;
        }

        FiggyUtility.saveConfig(guildConfig);
        FiggyUtility.privateMessage(user, String.format("Successful [%s] set!", settingType));


    }
}



