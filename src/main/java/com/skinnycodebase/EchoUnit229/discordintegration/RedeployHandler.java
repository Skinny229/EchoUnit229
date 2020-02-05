package com.skinnycodebase.EchoUnit229.discordintegration;

import com.skinnycodebase.EchoUnit229.DeploymentSettings;
import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class RedeployHandler extends ListenerAdapter {

    public static final Logger logger = LoggerFactory.getLogger(RedeployHandler.class);


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




        Optional<GuildConfig> configOptional = FiggyUtility.getConfig(event.getGuild().getId());

        //If set up doesnt exists run inital setup
        if(!configOptional.isPresent()){
            logger.info("Setting up new server with name[{}]",event.getGuild().getName());
            User serverOwner = event.getGuild().getOwner().getUser();
            GuildConfig newConfig = new GuildConfig();
            newConfig.setGuildId(event.getGuild().getId());
            newConfig.setLastGameCount(0);
            FiggyUtility.saveConfig(newConfig);
            FiggyUtility.privateMessage(serverOwner,
                    "Howdy owner! It seems you either just added the server or I lost your data(oops).\n" +
                            "To set up public listings WITHIN THE SERVER YOU ARE SETTING UP type `-set listings [id]`\n" +
                            "where `[id]` is the text channel id\n" +
                            "This will automatically enable your public listings, which I recommend only im able to type in.");
            return;
        }
        FiggyUtility.updateAllPublicGamesList(event.getGuild());



    }

}
