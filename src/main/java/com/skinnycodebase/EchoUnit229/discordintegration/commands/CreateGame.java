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


        if (echoGameService.deleteGameByPlayerID(event.getMember().getId())) {
            fullMessage.append("Your previous public game has been successfully deleted!\n");
            updatePinnedMessageGameList(event.getGuild());
        }


        if (type.equals("public")) {
            if (isValidID(lobbyID)) {


                String boisRole = event.getGuild().getRoleById("645047793500815387").getAsMention();
                MessageChannel channel = event.getGuild().getTextChannelById("661307549496115232");


                fullMessage.append("@BOISTEMP");//TODO::REPLACE ME
                fullMessage.append(" the available games have been updated.");

                channel.sendMessage(fullMessage.toString()).queue();

                registerGameToDB(lobbyID, event.getAuthor().getId(), false);

                for (Message msg : event.getMessage().getChannel().getHistory().getRetrievedHistory())
                        if(msg.getMember().getUser().getId().equals("661304672832847872"))
                            msg.delete().queue();

                updatePinnedMessageGameList(event.getGuild());

            }
        }


        String plyMention = event.getAuthor().getAsMention();

        event.getMessage().getChannel().sendMessage("Game has successfully been created " + plyMention).queue();

        event.getMessage().delete().queue();
    }


    private void registerGameToDB(String lobbyID, String plyID, boolean isPrivate) {


        EchoGame game = new EchoGame();

        game.setLobbyID(lobbyID);
        game.setPlayerID(plyID);
        game.setPrivate(isPrivate);
        game.setTimeGameCreated(LocalDateTime.now());

        echoGameService.save(game);

    }

    private void updatePinnedMessageGameList(Guild guild) {

        Iterable<EchoGame> list = echoGameService.findAll();
        ArrayList<EchoGame> publicGames = new ArrayList<>();


        for (EchoGame game : list) {
            if (!game.isPrivate() && ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) < 45)
                publicGames.add(game);
        }

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Current active games");
        builder.setColor(53380);

        for (EchoGame game : publicGames)
            builder.addField(guild.getMemberById(game.getPlayerID()).getUser().getName() + "'s game", createLink(game.getLobbyID()), true);


        guild.getTextChannelById("661307549496115232").sendMessage(builder.build()).queue();

    }


    private static String createLink(String ID) {
        return "http://echovrprotocol.com/api/v1/genGame?lobbyID=" + ID;
    }

    private static boolean isValidID(String a) {
        return a.length() == 9;
    }
}