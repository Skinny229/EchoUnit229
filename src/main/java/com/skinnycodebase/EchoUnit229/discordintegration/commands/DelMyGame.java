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

        //Look for the game within the public echo game table and delete if it exists
        echoGameService.deletePublicGameByPlayerID(event.getMember().getUser().getId());

        //Update the lfgbot channel public games
        CreateGame.updatePublicMessageGameList(event.getGuild(),echoGameService);

        //Delete original command message
        event.getMessage().delete().queue();

        //Notify the player through private message
        String plyMention = event.getAuthor().getAsMention();
        event.getAuthor().openPrivateChannel().queue((channel) -> {
            channel.sendMessage(plyMention + " your game has been successfully removed from the public listing").queue();
        });
    }
}
