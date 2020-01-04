package com.skinnycodebase.EchoUnit229.discordintegration;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class RedeployHandler extends ListenerAdapter {


    @Override
    public void onGuildReady(GuildReadyEvent event) {

        if(event.getGuild().getId().equals("623999906646065172") )
            event.getGuild().getMemberById("142673915964030976").getUser().openPrivateChannel().queue((channel) ->
            {
                channel.sendMessage("It seems I have been reloaded. Just to let you know").queue();
            });
    }

}
