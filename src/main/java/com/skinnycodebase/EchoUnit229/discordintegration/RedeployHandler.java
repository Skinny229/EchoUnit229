package com.skinnycodebase.EchoUnit229.discordintegration;

import com.skinnycodebase.EchoUnit229.DeploymentSettings;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RedeployHandler extends ListenerAdapter {


    @Override
    public void onGuildReady(GuildReadyEvent event) {

        if(event.getGuild().getId().equals(DeploymentSettings.ESO_GUILD_ID) )
            event.getGuild().getMemberById(DeploymentSettings.DEV_ID).getUser().openPrivateChannel().queue((channel) ->
            {
                channel.sendMessage("It seems I have been reloaded. Just to let you know").queue();
            });
    }

}
