package com.skinnycodebase.EchoUnit229.discordintegration;

import com.skinnycodebase.EchoUnit229.discordintegration.commands.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;


@Component
public class PublicMessageIntake extends ListenerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(PublicMessageIntake.class);


    /*
    *
    *  Example input: '-[command] [arg1] [arg2]......'
    * */
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event){

        String rawMsg = event.getMessage().getContentRaw();
        //Put eyes if @bois is mentioned
        if(!event.getMessage().getMentionedRoles().isEmpty()&& event.getMessage().getMentionedRoles().get(0).getName().equals("Bois"))
            event.getMessage().getTextChannel().sendMessage(":eyes:").complete();

        if(rawMsg.startsWith("-")){

            logger.info("Possible command attempt by [{}] with [{}]" , event.getAuthor().getName(), event.getMessage().getContentDisplay());
            logger.debug("Raw command [{}]", rawMsg);

            String command;
            //Parse command being attempted
            if(rawMsg.indexOf(' ') == -1)
                command = rawMsg.substring(1);
            else
                command = rawMsg.substring(1,rawMsg.indexOf(' '));

            command = command.toUpperCase();
            logger.info("Command Parsed into [{}]" , command);



            switch(command){
                case "CREATEGAME":
                    CreateGame.run(event);
                    break;
                case "DELMYGAME":
                    DelMyGame.run(event);
                    break;
                    case "SET":
                    Set.run(event);
                    event.getMessage().delete().queue();
                    break;
                case "HELP":
                    Help.run(event);
                    break;
                case "INSTALL":
                    Install.run(event);
                default:
                    logger.info("No command found for [ {} ]", command);
            }

            logger.info("Command Execution [{}] by [{}] complete", command,event.getAuthor().getName());
        }
   }


}
