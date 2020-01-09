package com.skinnycodebase.EchoUnit229.discordintegration.commands;


import com.skinnycodebase.EchoUnit229.models.EchoGame;
import com.skinnycodebase.EchoUnit229.models.EchoGameRepository;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@Configurable
public class CreateGame {

    @Autowired
    private EchoGameRepository echoGameService;


    public  void run(MessageReceivedEvent event) {


        ArrayList<String> cmdbreakdown = new ArrayList(Arrays.asList(event.getMessage().getContentRaw().split(" ")));


        String type = cmdbreakdown.get(1);

        String lobbyID = cmdbreakdown.get(2);

        //String playersRequested = cmdbreakdown.get(2);


        if (type.equals("public")) {
            if (isValidID(lobbyID)) {
                StringBuilder fullMessage = new StringBuilder();

                String boisRole = event.getGuild().getRoleById("645047793500815387").getAsMention();
                MessageChannel channel = event.getGuild().getTextChannelById("661307549496115232");

                registerGameToDB(lobbyID, event.getAuthor().getId(), false);

                updatePinnedMessageGameList();

                fullMessage.append("Hey ");
                fullMessage.append("@BOISTEMP");//TODO::REPLACE ME
                fullMessage.append(" a public game has been created!");
                fullMessage.append("\nFollow the link to join: " + createLink(lobbyID));
                fullMessage.append("\nOR checkout the LFG-BOT pins for all public games");

                channel.sendMessage(fullMessage.toString()).queue();

            }
        }


        String plyMention = event.getAuthor().getAsMention();

        event.getMessage().getChannel().sendMessage("Game has successfully been created " + plyMention).queue();

        event.getMessage().delete().queue();
    }


    public void registerGameToDB(String lobbyID, String plyID, boolean isPrivate) {


        EchoGame game = new EchoGame();

        game.setLobbyID(lobbyID);
        game.setPlayerID(plyID);
        game.setPrivate(isPrivate);
        game.setTimeGameCreated(LocalDateTime.now());

        echoGameService.save(game);


    }

    private  void updatePinnedMessageGameList() {

        Iterable<EchoGame> list = echoGameService.findAll();
        ArrayList<EchoGame> publicGames = new ArrayList<>();
        for (EchoGame game : list){
            if (!game.isPrivate() && ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) < 45)
                publicGames.add(game);
        }



    }


    private static String createLink(String ID) {

        String link = "http://echovrprotocol.com/api/v1/genGame?gameID=" + ID;
        return link;
    }

    private static boolean isValidID(String a) {
        return a.length() == 9;
    }
}