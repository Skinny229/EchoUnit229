package com.skinnycodebase.EchoUnit229.discordintegration;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageIntake extends ListenerAdapter {



    private static final Logger logger = LoggerFactory.getLogger(MessageIntake.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!ping") && event.getMember().getId().equals("142673915964030976"))
        {
            MessageChannel channel = event.getChannel();
             channel = event.getGuild().getTextChannelById("661307549496115232");

            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                    });
        }

        if(msg.getContentRaw().startsWith("-") && event.getMember().getId().equals("142673915964030976")){

            if(msg.getContentRaw().equals("-PreviewGameCreated"))
            {

                String boisRole =  event.getGuild().getRoleById("645047793500815387").getAsMention();
                MessageChannel channel = event.getGuild().getTextChannelById("661307549496115232");

                channel.sendMessage("Hey "+ boisRole+" A Private game has requested [3] echo units! Please click HERE to join. Make sure you have the client component installed!").queue();
            }


            if(msg.getContentRaw().equals("-customURLLauncher"))
            {

                String boisRole =  event.getGuild().getRoleById("645047793500815387").getAsMention();
                MessageChannel channel = event.getGuild().getTextChannelById("661307549496115232");

                channel.sendMessage("Hey "+ boisRole+" A Private game has requested [3] echo units! Please click HERE to join. Make sure you have the client component installed!").queue();
            }


            if(msg.getContentRaw().equals("-urltest"))
            {
                MessageChannel channel = event.getGuild().getTextChannelById("661307549496115232");

                EmbedBuilder builder = new EmbedBuilder();

                //builder.add


                channel.sendMessage("[HyperLink](https://www.google.com/)").queue();
            }
        }
    }
}
