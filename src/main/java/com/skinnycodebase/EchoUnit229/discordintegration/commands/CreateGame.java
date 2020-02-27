package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class CreateGame {

    private static final Logger logger = LoggerFactory.getLogger(CreateGame.class);


    public static void run(MessageReceivedEvent event) {


        Guild guild = event.getGuild();
        User user = event.getAuthor();
        TextChannel originCommandTextChannel = event.getTextChannel();

        ArrayList<String> commandBreakdown = new ArrayList<>(Arrays.asList(event.getMessage().getContentRaw().split(" ")));
        String gameGenerationType = commandBreakdown.get(1).toLowerCase();


        logger.info("Expected game type [{}]", gameGenerationType);


        switch (gameGenerationType.toLowerCase()) {
            case "public":
                publicGameCreation(user, guild, originCommandTextChannel);
                break;
            case "private":
                //TODO: Reimplement me
                event.getChannel().sendMessage("No Boomer").queue();
                break;
            default:
                FiggyUtility.privateMessage(user, "I didn't understand game type please try again.");
        }
    }

    private static void publicGameCreation(User user, Guild guild, TextChannel responseChannel) {

        GuildConfig config = FiggyUtility.getConfig(guild.getId()).get();

        if (config.getPublicListingChannelId() == null) {
            responseChannel.sendMessage("Hmmm. It seems the owner hasn't set a public listing channels, Canceling...").queue();
            return;
        }


        if (!FiggyUtility.hasActiveGame(user))

            FiggyUtility.registerPublicGame(user.getId(), guild.getId());
        else {
            FiggyUtility.privateMessage(user, "It seems you have an active game somewhere, you cant have more than 1!");
            return;
        }


        FiggyUtility.privateMessage(user, "Please click the link below to confirm and display your game. Do NOT share this link\n"
                + FiggyUtility.createPublicGameConfirmationLink(guild, user));

        //Notify player that the game has been created
        String plyMention = user.getAsMention();
        responseChannel.sendMessage("Game confirmation code has been sent to you " + plyMention + "\n" +
                "Please note that this link will expire in 3 minutes").queue();
    }


}