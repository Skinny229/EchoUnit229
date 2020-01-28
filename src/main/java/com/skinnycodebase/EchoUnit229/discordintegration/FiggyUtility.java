package com.skinnycodebase.EchoUnit229.discordintegration;


import com.skinnycodebase.EchoUnit229.DeploymentSettings;
import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class FiggyUtility  {


    private static EchoGameService service;

    @Autowired
    public FiggyUtility(EchoGameService service){
        if(FiggyUtility.service == null)
            FiggyUtility.service = service;
    }



    public static void updatePublicMessageGameList(Guild guild) {

        if(guild.getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID) == null)
            return;

        //Delete all messages from the bot under the lfgbot channel
        for (Message msg : guild.getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID).getIterableHistory())
            if (msg.getMember().getUser().getId().equals(DeploymentSettings.BOT_ID))
                msg.delete().queue();

        //Query for all public games within the DB
        Iterable<EchoGamePublic> list = service.findAllPublic();

        ArrayList<EchoGamePublic> publicGames = new ArrayList<>();

        //Out of those add only those who have been created within the last 45 mins
        for (EchoGamePublic game : list) {
            if (ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) < 45)
                publicGames.add(game);
        }

        //Create a Embed message for every joinable public game and post it in the LFGBOT channel
        for (EchoGamePublic game : publicGames) {

            EmbedBuilder builder = new EmbedBuilder();

            builder.setColor(new Color(243, 50, 149));

            //Generate link to join the game
            builder.setTitle(guild.getMemberById(game.getPlayerID()).getUser().getName() + "'s game", createLink(game.getLobbyID()));

            //Post time since it was created
            builder.addField("Time since creation", ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) + " MINS", true);

            guild.getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID).sendMessage(builder.build()).queue();
        }


    }

    private static String createLink(String ID) {

        return "http://echovrprotocol.com/api/" + DeploymentSettings.API_CONTROLLER_VERSION + "/genGame?lobbyID=" + ID;
    }


}
