package com.skinnycodebase.EchoUnit229.discordintegration;

import com.skinnycodebase.EchoUnit229.DeploymentSettings;
import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Optional;

public class RedeployHandler extends ListenerAdapter {


    /**
    *  Current method to notify owner that the application has started in case of reload
    * */
    @Override
    public void onGuildReady(GuildReadyEvent event) {

        if(event.getGuild().getId().equals(DeploymentSettings.ESO_GUILD_ID) )

            //Sends Private message to Skinny
            event.getGuild().getMemberById(DeploymentSettings.DEV_ID).getUser().openPrivateChannel().queue((channel) ->
            {
                channel.sendMessage("It seems I have been reloaded. Just to let you know").queue();
            });

        GuildConfig config;
            config = new GuildConfig();
            config.setGuildId(event.getGuild().getId());
            config.setPublicListingChannelId("669569226427858954");
            FiggyUtility.saveConfig(config);




    }

}
