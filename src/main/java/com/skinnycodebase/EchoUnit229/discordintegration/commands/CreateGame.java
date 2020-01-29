package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class CreateGame {

    private static final Logger logger = LoggerFactory.getLogger(CreateGame.class);


    public static void run(MessageReceivedEvent event) {


        ArrayList<String> cmdbreakdown = new ArrayList<>(Arrays.asList(event.getMessage().getContentRaw().split(" ")));

        if (cmdbreakdown.size() < 3) {
            logger.warn("No game type detected!");
            FiggyUtility.privateMessage(event.getAuthor(), "Game Creation FAILED. Did you forget to input game type by any chance?");
            return;
        }

        String type = cmdbreakdown.get(2).toLowerCase();

        String lobbyID = cmdbreakdown.get(1);

        logger.info("Expected game type [{}]", type);
        logger.info("Expected sessionID[{}]", lobbyID);

        StringBuilder fullMessage = new StringBuilder();

        if (!FiggyUtility.isValidID(lobbyID)) {
            logger.warn("Invalid ID. Cancelling....");
            FiggyUtility.privateMessage(event.getAuthor(), "It seems like this is an invalid 'sessionid' please try again.\n" +
                    "Is it possible you are still using V0.1 of the Game inviter?");

            return;
        }


        if (type.equals("public")) {

            if (!FiggyUtility.hasActiveGameInGuild(event.getGuild(), event.getAuthor()))
                FiggyUtility.registerPublicGame(lobbyID, event.getAuthor().getId(), event.getGuild().getId());
            else {
                FiggyUtility.privateMessage(event.getAuthor(), "It seems you have an active game, use -delmygame on the current guild to delete it");
                return;
            }

            //Display all new and current running games to the lfg-bot channel
            FiggyUtility.updatePublicGamesList(event.getGuild());


            //Notify player that the game has been created
            String plyMention = event.getAuthor().getAsMention();
            event.getMessage().getChannel().sendMessage("Game has successfully been created " + plyMention).queue();


        } else if (type.equals("private")) {

            //Add mentioned players to the invite list
            HashSet<User> toBeInvited = new HashSet<>(event.getMessage().getMentionedUsers());

            //Add mentioned roles
            List<Role> rolesToInvite = event.getMessage().getMentionedRoles();
            if (rolesToInvite.size() != 0)
                for (Member member : event.getGuild().getMembersWithRoles(rolesToInvite))
                    toBeInvited.add(member.getUser());


            //Double check the bimbo actually invited someone
            if (toBeInvited.isEmpty()) {
                logger.warn("No players have been invited to the private. Canceling Creation");
                FiggyUtility.privateMessage(event.getAuthor(), "You didn't invite anyone! Canceling....");
                return;
            }

            if (toBeInvited.size() > 14) {
                FiggyUtility.privateMessage(event.getAuthor(), "wowowow too many players.. Canceling...");
                return;
            }

            //Private Message all new players they have been invited to a new game
            for (User user : toBeInvited) {
                //If the user is the same as the creator send separate msg
                if (user.getId().equals(event.getAuthor().getId())) {
                    FiggyUtility.privateMessage(user, "Game creation SUCCESSFUL. Invites are being sent...\n" + FiggyUtility.createLink(lobbyID));
                    continue;
                }

                logger.info("Inviting user [{}] to {} 's Game", user.getName(), event.getAuthor().getName());
                FiggyUtility.privateMessageOnPrivateInvite(user, lobbyID);
            }


        } else {
            FiggyUtility.privateMessage(event.getAuthor(), "I didn't understand game type please try again.");
        }

    }

}