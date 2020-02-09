package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class CreateGame {

    private static final Logger logger = LoggerFactory.getLogger(CreateGame.class);


    public static void run(MessageReceivedEvent event) {


        ArrayList<String> cmdbreakdown = new ArrayList<>(Arrays.asList(event.getMessage().getContentRaw().split(" ")));


        String type = cmdbreakdown.get(1).toLowerCase();


        logger.info("Expected game type [{}]", type);



        if (type.equals("public")) {

            GuildConfig config = FiggyUtility.getConfig(event.getGuild().getId()).get();
            if(config.getPublicListingChannelId() == null){
                event.getMessage().getTextChannel().sendMessage("Hmmm. It seems the owner hasn't set a public listing channels, Canceling...").queue();
                return;
            }


            if (!FiggyUtility.hasActiveGame(event.getAuthor()))

                FiggyUtility.registerPublicGame(event.getAuthor().getId(), event.getGuild().getId());
            else {
                FiggyUtility.privateMessage(event.getAuthor(), "It seems you have an active game somewhere, you cant have more than 1!");
                return;
            }


            FiggyUtility.privateMessage(event.getAuthor(), "Please click the link below to confirm and display your game. Do NOT share this link\n"
                    + FiggyUtility.createPublicGameConfirmationLink(event.getGuild(),event.getAuthor()));

            //Notify player that the game has been created
            String plyMention = event.getAuthor().getAsMention();
                event.getMessage().getChannel().sendMessage("Game confirmation code has been sent to you " + plyMention +"\n" +
                        "Please note that this link will expire in 3 minutes").queue();


        } else if (type.equals("private")) {
            event.getChannel().sendMessage("No Boomer").queue();
//            //Add mentioned players to the invite list
//            HashSet<User> toBeInvited = new HashSet<>(event.getMessage().getMentionedUsers());
//
//            //Add mentioned roles
//            List<Role> rolesToInvite = event.getMessage().getMentionedRoles();
//            if (rolesToInvite.size() != 0)
//                for (Member member : event.getGuild().getMembersWithRoles(rolesToInvite))
//                    toBeInvited.add(member.getUser());
//
//
//            //Double check the bimbo actually invited someone
//            if (toBeInvited.isEmpty()) {
//                logger.warn("No players have been invited to the private. Canceling Creation");
//                FiggyUtility.privateMessage(event.getAuthor(), "You didn't invite anyone! Canceling....");
//                return;
//            }
//
//            if (toBeInvited.size() > 14) {
//                FiggyUtility.privateMessage(event.getAuthor(), "wowowow too many players.. Canceling...");
//                return;
//            }
//
//            //Private Message all new players they have been invited to a new game
//            for (User user : toBeInvited) {
//                //If the user is the same as the creator send separate msg
//                if (user.getId().equals(event.getAuthor().getId())) {
//                   // FiggyUtility.privateMessage(user, "Game creation SUCCESSFUL. Invites are being sent...\n" + FiggyUtility.createLinkHttp(lobbyID));
//                    continue;
//                }
//
//                logger.info("Inviting user [{}] to {} 's Game", user.getName(), event.getAuthor().getName());
//                //FiggyUtility.privateMessageOnPrivateInvite(user, lobbyID);



        } else {
            FiggyUtility.privateMessage(event.getAuthor(), "I didn't understand game type please try again.");
        }

    }

}