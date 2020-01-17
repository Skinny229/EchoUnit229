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

@Component
public class CreateGame {

    private static final Logger logger = LoggerFactory.getLogger(CreateGame.class);

    private final EchoGameService echoGameService;

    @Autowired
    public CreateGame(EchoGameService echoGameService) {
        this.echoGameService = echoGameService;
    }


    public void run(MessageReceivedEvent event) {


        ArrayList<String> cmdbreakdown = new ArrayList(Arrays.asList(event.getMessage().getContentRaw().split(" ")));

        String type = cmdbreakdown.get(1);

        String lobbyID = cmdbreakdown.get(2);

        StringBuilder fullMessage = new StringBuilder();


        if (type.equals("public")) {
            if (isValidID(lobbyID)) {

                registerPublicGame(lobbyID, event.getAuthor().getId());

                String boisRole = event.getGuild().getRoleById(DeploymentSettings.BOIS_ROLE_ID).getAsMention();

                MessageChannel channel = event.getGuild().getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID);


                fullMessage.append(boisRole);
                fullMessage.append(" the available public games have been updated.");

                String plyMention = event.getAuthor().getAsMention();

                event.getMessage().delete().queue();

                updatePinnedMessageGameList(event.getGuild(), echoGameService);

                channel.sendMessage(fullMessage.toString()).queue();

                event.getMessage().getChannel().sendMessage("Game has successfully been created " + plyMention).queue();

            }
        } else if (type.equals("private")) {

            //Add mentioned players to the invite list
            HashSet<User> toBeInvited = new HashSet<>(event.getMessage().getMentionedUsers());

            //Add mentioned roles
            for (Member member : event.getGuild().getMembersWithRoles(event.getMessage().getMentionedRoles()))
                toBeInvited.add(member.getUser());


            if(toBeInvited.isEmpty()){
                privateMessage(event.getAuthor(), "You didn't invite anyone! Canceling....");
                event.getMessage().delete().queue();
                return;
            }

            //Private Message all new players they have been invited to a new game
            for(User user : toBeInvited) {
                //If the user is the same as the creator send seperate msg
                if(user.getId().equals(event.getAuthor().getId()))
                {
                    privateMessage(user, "Game registered and invites sent");
                    continue;
                }

                logger.info("Inviting: " + user.getName() + " -- FROM: " + event.getAuthor().getName());
                privateMessageOnPrivateInvite(user, lobbyID);
            }

            //RegisterCreatedGame
            //LogInvitees


        }


    }


    private void privateMessageOnPrivateInvite(User user, String lobbyId){

        String msg = "This is a test please ignore for now. You will most likely see this multiple times. You are currently my test monkey\n"+
                createLink(lobbyId);
        privateMessage(user, msg);

    }

    private void privateMessage(User user, String message) {
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }


    private void registerPublicGame(String lobbyID, String plyID) {
        echoGameService.deletePublicGameByPlayerID(plyID);
        EchoGamePublic game = new EchoGamePublic();

        game.setLobbyID(lobbyID);
        game.setPlayerID(plyID);
        game.setTimeGameCreated(LocalDateTime.now());

        echoGameService.savePublic(game);

    }

    public static void updatePinnedMessageGameList(Guild guild, EchoGameService service) {

        for (Message msg : guild.getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID).getIterableHistory())
            if (msg.getMember().getUser().getId().equals(DeploymentSettings.BOT_ID))
                msg.delete().queue();

        Iterable<EchoGamePublic> list = service.findAllPublic();
        ArrayList<EchoGamePublic> publicGames = new ArrayList<>();


        for (EchoGamePublic game : list) {
            if (!game.isPrivate() && ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) < 45)
                publicGames.add(game);
        }


        for (EchoGamePublic game : publicGames) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(new Color(11, 243, 8));
            builder.setTitle(guild.getMemberById(game.getPlayerID()).getUser().getName() + "'s game", createLink(game.getLobbyID()));
            builder.addField("Time since creation", ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) + " MINS", true);
            guild.getTextChannelById(DeploymentSettings.LFGBOT_CHANNEL_ID).sendMessage(builder.build()).queue();
        }


    }


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