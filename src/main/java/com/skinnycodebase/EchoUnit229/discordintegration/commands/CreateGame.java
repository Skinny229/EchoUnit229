package com.skinnycodebase.EchoUnit229.discordintegration.commands;


import com.skinnycodebase.EchoUnit229.models.EchoGame;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class CreateGame {

    @Autowired
    private EchoGameService echoGameService;


    public void run(MessageReceivedEvent event) {


        ArrayList<String> cmdbreakdown = new ArrayList(Arrays.asList(event.getMessage().getContentRaw().split(" ")));

        String type = cmdbreakdown.get(1);

        String lobbyID = cmdbreakdown.get(2);

        StringBuilder fullMessage = new StringBuilder();


        if (type.equals("public")) {
            if (isValidID(lobbyID)) {

                registerGameToDB(lobbyID, event.getAuthor().getId(), false);

                String boisRole = event.getGuild().getRoleById("645047793500815387").getAsMention();
                MessageChannel channel = event.getGuild().getTextChannelById("661307549496115232");


                fullMessage.append(boisRole);
                fullMessage.append(" the available public games have been updated.");

                String plyMention = event.getAuthor().getAsMention();

                event.getMessage().delete().queue();

                updatePinnedMessageGameList(event.getGuild(), echoGameService);

                channel.sendMessage(fullMessage.toString()).queue();

                event.getMessage().getChannel().sendMessage("Game has successfully been created " + plyMention).queue();

            }
        }




    }


    private void registerGameToDB(String lobbyID, String plyID, boolean isPrivate) {
        echoGameService.deleteGameByPlayerID(plyID);
        EchoGame game = new EchoGame();

        game.setLobbyID(lobbyID);
        game.setPlayerID(plyID);
        game.setPrivate(isPrivate);
        game.setTimeGameCreated(LocalDateTime.now());

        echoGameService.save(game);

    }

    public static void updatePinnedMessageGameList(Guild guild, EchoGameService service) {

        for (Message msg : guild.getTextChannelById("661307549496115232").getIterableHistory())
            if(msg.getMember().getUser().getId().equals("661304672832847872"))
                msg.delete().queue();

        Iterable<EchoGame> list = service.findAll();
        ArrayList<EchoGame> publicGames = new ArrayList<>();


        for (EchoGame game : list) {
            if (!game.isPrivate() && ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) < 45)
                publicGames.add(game);
        }







        for (EchoGame game : publicGames){
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(new Color(11,243,8));
            builder.setTitle(guild.getMemberById(game.getPlayerID()).getUser().getName() + "'s game", createLink(game.getLobbyID()));
            builder.addField( "Time since creation", ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) + " MINS", true);
            guild.getTextChannelById("661307549496115232").sendMessage(builder.build()).queue();
        }


    }


    private static String createLink(String ID) {
        return "http://echovrprotocol.com/api/v1/genGame?lobbyID=" + ID;
    }

    private static boolean isValidID(String a) {

        char someChar = '-';
        int count = 0;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == someChar) {
                count++;
            }
        }
        return a.length()  > 25 && count > 3;
    }
}