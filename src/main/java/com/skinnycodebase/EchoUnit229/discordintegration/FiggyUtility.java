package com.skinnycodebase.EchoUnit229.discordintegration;


import com.skinnycodebase.EchoUnit229.DeploymentSettings;
import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.models.EchoUpdateResponseBody;
import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import com.skinnycodebase.EchoUnit229.service.GuildConfigService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.awt.Color;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class FiggyUtility  {


    private static EchoGameService echoGameService;
    private static GuildConfigService guildService;
    private static List<Guild> guilds;
    private static HashSet<String> possibleUniqueIds = new HashSet<>();

    @Autowired
    public FiggyUtility(EchoGameService service, GuildConfigService guildConfigService){
        if(FiggyUtility.echoGameService == null)
            FiggyUtility.echoGameService = service;
        if(FiggyUtility.guildService == null)
            FiggyUtility.guildService = guildConfigService;
    }

    public static void setGuilds(List<Guild> guildsl){
        guilds = guildsl;
    }



    //Rewrite
    public static void updatePublicGamesList(Guild guild){

        //Get mention roles
        Optional<GuildConfig> configOptional = guildService.getById(guild.getId());
        if(!configOptional.isPresent() || configOptional.get().getGuildId() == null )
            return;
        GuildConfig config = configOptional.get();

        //listing channel
        if(config.getPublicListingChannelId() == null)
            return;
        TextChannel listingChanel = guild.getTextChannelById(config.getPublicListingChannelId());



        //@role mention if it exists
        String mentionRole  = "";
        String mentionRoleId = config.getMentionRoleID();
        if(mentionRoleId != null)
            mentionRole = guild.getRoleById(mentionRoleId).getAsMention();

        //Delete messages within channel
        for (Message msg : listingChanel.getIterableHistory())
            if (msg.getMember().getId().equals(DeploymentSettings.BOT_ID))
                msg.delete().queue();


        Iterable<EchoGamePublic> list = echoGameService.findAllActivePublic(guild.getId());
        ArrayList<EchoGamePublic> publicGames = new ArrayList<>();

        //Out of those add only those who have been created within the last 45 mins
        for (EchoGamePublic game : list) {
            if (ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) < 45)
                publicGames.add(game);
        }

        //if(publicGames.size() > )

        //Create a Embed message for every joinable public game and post it in the LFGBOT channel
        for (EchoGamePublic game : publicGames) {

            EmbedBuilder builder = new EmbedBuilder();

            builder.setColor(new Color(0, 243, 14));

            //Generate link to join the game
            builder.setTitle(guild.getMemberById(game.getPlayerID()).getUser().getName() + "'s game(click me)", createLink(game.getLobbyID()));

            //Post time since it was created
            builder.addField("Time since creation", ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) + " MINS", true);

            listingChanel.sendMessage(builder.build()).queue();
        }

    }

    public static boolean isValidID(String a) {

        char someChar = '-';
        int count = 0;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == someChar) {
                count++;
            }
        }
        return a.length() > 25 && count > 3;
    }


    public static String createLink(String ID) {

        return "http://echovrprotocol.com/api/" + DeploymentSettings.API_CONTROLLER_VERSION + "/joinGame?lobbyId=" + ID;
    }


    public static Optional<GuildConfig> getConfig(String guildId){
        return guildService.getById(guildId);
    }
    public static void saveConfig(GuildConfig config){
        guildService.registerServer(config);
    }
    public static void registerPublicGame(String lobbyId, String userId, String guildId) {
        EchoGamePublic game = new EchoGamePublic();
        game.setLobbyID(lobbyId);
        game.setPlayerID(userId);
        game.setGuildId(guildId);
        game.setTimeGameCreated(LocalDateTime.now());
        game.setInUse(true);
        echoGameService.savePublic(game);
    }
    public static boolean hasActiveGameInGuild(Guild guild,User user){
        return echoGameService.hasActivePublicIn(guild, user);
    }
    public static void updateFromRequest(EchoUpdateResponseBody body){

    }
    public static void decommissionGame(Guild guild, User user){

        if(echoGameService.decommissionGame(guild.getId(), user.getId()))
            privateMessage(user, "Game deleted");
        else
            privateMessage(user, "No active game found but I'm updating the listings anyways");


        updatePublicGamesList(guild);

    }
    public static void privateMessage(User user, String message) {
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }




    /*
     * Notify user that they have been invited to a private game
     * */
    public static void privateMessageOnPrivateInvite(User user, String lobbyId) {

        String msg = "You have been invited to a Scrim/Private game! Please click on the following link to join\n" +
                FiggyUtility.createLink(lobbyId);
        privateMessage(user, msg);

    }

    public static boolean addUniqueId(String id){
        if(possibleUniqueIds.contains(id))
            return false;
        possibleUniqueIds.add(id);
        return true;
    }
}
