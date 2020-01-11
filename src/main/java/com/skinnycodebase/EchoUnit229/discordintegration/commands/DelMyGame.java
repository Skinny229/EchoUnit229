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
        echoGameService.deleteGameByPlayerID(event.getMember().getUser().getId());
        CreateGame.updatePinnedMessageGameList(event.getGuild(),echoGameService);
        event.getMessage().delete().queue();
        String plyMention = event.getAuthor().getAsMention();
        event.getAuthor().openPrivateChannel().queue((channel) -> {
            channel.sendMessage(plyMention + " your game has been successfully removed from the public listing").queue();
        });
    }


}
