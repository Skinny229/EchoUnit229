package com.skinnycodebase.EchoUnit229.discordintegration.commands;


import com.skinnycodebase.EchoUnit229.DeploymentSettings;
import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class CreateGame {

    private static final Logger logger = LoggerFactory.getLogger(CreateGame.class);

    private final EchoGameService echoGameService;

    @Autowired
    public CreateGame(EchoGameService echoGameService) {
        this.echoGameService = echoGameService;
    }


    public void run(MessageReceivedEvent event) {


        ArrayList<String> cmdbreakdown = new ArrayList<>(Arrays.asList(event.getMessage().getContentRaw().split(" ")));

        if (cmdbreakdown.size() < 3) {
            logger.warn("No game type detected!");
            privateMessage(event.getAuthor(), "Game Creation FAILED. Did you forget to input game type by any chance?");
            return;
        }

        String type = cmdbreakdown.get(2).toLowerCase();

        String lobbyID = cmdbreakdown.get(1);

        logger.info("Expected game type [{}]", type);
        logger.info("Expected sessionID[{}]", lobbyID);

        StringBuilder fullMessage = new StringBuilder();

        if (!isValidID(lobbyID)) {
            logger.warn("Invalid ID. Cancelling....");
            privateMessage(event.getAuthor(), "It seems like this is an invalid 'sessionid' please try again.\n" +
                    "Is it possible you are still using V0.1 of the Game inviter?");

            return;
        }


        if (type.equals("public")) {

            registerPublicGame(lobbyID, event.getAuthor().getId());

            String boisRole = event.getGuild().getRoleById(DeploymentSettings.BOIS_ROLE_ID).getAsMention();

            MessageChannel channel = event.getGuild().getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID);


            fullMessage.append(boisRole);
            fullMessage.append(" the available public games have been updated.");

            String plyMention = event.getAuthor().getAsMention();


            //Display all new and current running games to the lfg-bot channel
            updatePublicMessageGameList(event.getGuild(), echoGameService);

            //Call on all @Bois from the lfg-bot channel
            channel.sendMessage(fullMessage.toString()).queue();

            //Notify player that the game has been created
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
                privateMessage(event.getAuthor(), "You didn't invite anyone! Canceling....");
                return;
            }

            if(toBeInvited.size() > 14){
                privateMessage(event.getAuthor(), "wowowow too many players.. Canceling...");
                return;
            }

            //Private Message all new players they have been invited to a new game
            for (User user : toBeInvited) {
                //If the user is the same as the creator send separate msg
                if (user.getId().equals(event.getAuthor().getId())) {
                    privateMessage(user, "Game creation SUCCESSFUL. Invites are being sent...\n"+createLink(lobbyID));
                    continue;
                }

                logger.info("Inviting user [{}] to {} 's Game", user.getName(), event.getAuthor().getName());
                privateMessageOnPrivateInvite(user, lobbyID);
            }



        }else{
            privateMessage(event.getAuthor(), "I didn't understand game type please try again.");
        }

    }


    private void privateMessage(User user, String message) {
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }

    /*
     * Notify user that they have been invited to a private game
     * */
    private void privateMessageOnPrivateInvite(User user, String lobbyId) {

        String msg = "You have been invited to a Scrim/Private game! Please click on the following link to join\n" +
                createLink(lobbyId);
        privateMessage(user, msg);

    }


    private void registerPublicGame(String lobbyID, String plyID) {
        echoGameService.deletePublicGameByPlayerID(plyID);
        EchoGamePublic game = new EchoGamePublic();

        game.setLobbyID(lobbyID);
        game.setPlayerID(plyID);
        game.setTimeGameCreated(LocalDateTime.now());

        echoGameService.savePublic(game);

    }

    public static void updatePublicMessageGameList(Guild guild, EchoGameService service) {

        if(guild.getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID) == null)
            return;

        //Delete all messages from the bot under the lfgbot channel
        for (Message msg : guild.getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID).getIterableHistory())
            if (msg.getMember().getUser().getId().equals(DeploymentSettings.BOT_ID))
                msg.delete().queue();

        //Query for all public games within the DB
        Iterable<EchoGamePublic> list = service.findAllPublic();

        ArrayList<EchoGamePublic> publicGames = new ArrayList<>();

        //Out of those add only those who have been created within the last 45 mins
        for (EchoGamePublic game : list) {
            if (ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) < 45)
                publicGames.add(game);
        }

        //Create a Embed message for every joinable public game and post it in the LFGBOT channel
        for (EchoGamePublic game : publicGames) {

            EmbedBuilder builder = new EmbedBuilder();

            builder.setColor(new Color(11, 243, 8));

            //Generate link to join the game
            builder.setTitle(guild.getMemberById(game.getPlayerID()).getUser().getName() + "'s game", createLink(game.getLobbyID()));

            //Post time since it was created
            builder.addField("Time since creation", ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) + " MINS", true);

            guild.getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID).sendMessage(builder.build()).queue();
        }


    }

    /*
     * Return current link to be used for the echoprotocol schema
     * */
    private static String createLink(String ID) {

        return "http://echovrprotocol.com/api/" + DeploymentSettings.API_CONTROLLER_VERSION + "/genGame?lobbyID=" + ID;
    }

    private static boolean isValidID(String a) {

        char someChar = '-';
        int count = 0;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == someChar) {
                count++;
            }
        }
        return a.length() > 25 && count > 3;
    }



}