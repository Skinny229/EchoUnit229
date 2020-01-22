package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelMyGame {

    @Autowired
    EchoGameService echoGameService;


    public void run(MessageReceivedEvent event){

            String msg;
        //Look for the game within the public echo game table and delete if it exists
        if(echoGameService.deletePublicGameByPlayerID(event.getMember().getUser().getId()))
            msg = "Game Deleted! Game listings updated.";
        else
            msg = "It seems you dont have a game. But I took the liberty to update the listing anyways";

        //Update the lfgbot channel public games
        CreateGame.updatePublicMessageGameList(event.getGuild(),echoGameService);


        //Notify the player through private message
        String plyMention = event.getAuthor().getAsMention();
        event.getAuthor().openPrivateChannel().queue((channel) -> {
            channel.sendMessage(msg).queue();
        });
    }
}
