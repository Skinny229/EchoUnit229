package com.skinnycodebase.EchoUnit229.discordintegration;

import com.skinnycodebase.EchoUnit229.discordintegration.commands.CreateGame;
import com.skinnycodebase.EchoUnit229.discordintegration.commands.DelMyGame;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;


@Component
public class BoisMessageIntake extends ListenerAdapter {


    private static final Logger logger = LoggerFactory.getLogger(BoisMessageIntake.class);


   private CreateGame createGame;
   private DelMyGame delMyGame;
    @Autowired
    public BoisMessageIntake(CreateGame createGame, DelMyGame delMyGame){
        this.createGame = createGame;
        this.delMyGame = delMyGame;
    }


    /*
    *
    *  Example input: '-[command] [arg1] [arg2]......'
    * */
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event){


        String rawMsg = event.getMessage().getContentRaw();
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
            logger.info("Command Parsed into [{}]" + command);



            switch(command){
                case "CREATEGAME":
                    createGame.run(event);
                    event.getMessage().delete().queue();
                    break;
                case "DELMYGAME":
                    delMyGame.run(event);
                    event.getMessage().delete().queue();
                    break;
                default:
                    logger.info("No command found for [{}]", command);
            }

            logger.info("Command Execution by [{}] complete", event.getAuthor().getName());
        }
   }


}
