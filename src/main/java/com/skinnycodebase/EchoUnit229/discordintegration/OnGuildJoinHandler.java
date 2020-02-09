package com.skinnycodebase.EchoUnit229.discordintegration;

import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class OnGuildJoinHandler extends ListenerAdapter {


    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        GuildConfig config = new GuildConfig();

        config.setGuildId(event.getGuild().getId());
        config.setGuildName(event.getGuild().getName());
        config.setLastGameCount(0);

        FiggyUtility.saveConfig(config);

        User serverOwner = event.getGuild().getOwner().getUser();

        FiggyUtility.privateMessage(serverOwner,
                "Howdy owner! It seems you either just added the server or I lost your data(oops).\n" +
                        "To set up public listings WITHIN THE SERVER YOU ARE SETTING UP type `-set listings [id]`\n" +
                        "where `[id]` is the text channel id\n" +
                        "This will automatically enable your public listings, which I recommend only im able to type in.");
        FiggyUtility.saveConfig(config);

    }
}
