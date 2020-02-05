package com.skinnycodebase.EchoUnit229.discordintegration;


import com.skinnycodebase.EchoUnit229.DeploymentSettings;
import com.skinnycodebase.EchoUnit229.EchoUnit229Application;
import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.models.responsebody.EchoLiveRequestBody;
import com.skinnycodebase.EchoUnit229.models.responsebody.EchoUpdateResponseBody;
import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import com.skinnycodebase.EchoUnit229.service.GuildConfigService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.awt.Color;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Component
public class FiggyUtility {


    private static EchoGameService echoGameService;
    private static GuildConfigService guildService;

    @Autowired
    public FiggyUtility(EchoGameService service, GuildConfigService guildConfigService) {
        if (FiggyUtility.echoGameService == null)
            FiggyUtility.echoGameService = service;
        if (FiggyUtility.guildService == null)
            FiggyUtility.guildService = guildConfigService;
    }


    //Rewrite
    public static void updateAllPublicGamesList(Guild guild) {

        //Get mention roles
        Optional<GuildConfig> configOptional = guildService.getById(guild.getId());
        if (!configOptional.isPresent() || configOptional.get().getGuildId() == null)
            return;
        GuildConfig config = configOptional.get();

        //listing channel
        if (config.getPublicListingChannelId() == null)
            return;
        TextChannel listingChanel = guild.getTextChannelById(config.getPublicListingChannelId());


        //@role mention if it exists
        String mentionRole = "";
        String mentionRoleId = config.getMentionRoleID();
        if (mentionRoleId != null)
            mentionRole = Objects.requireNonNull(guild.getRoleById(mentionRoleId)).getAsMention();

        //Delete messages within channel
        assert listingChanel != null;
        for (Message msg : listingChanel.getIterableHistory()) {
            if (Objects.requireNonNull(msg.getMember()).getId().equals(DeploymentSettings.BOT_ID))
                msg.delete().queue();
        }

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

            builder.setColor(new Color(243, 0, 17));

            //Generate link to join the game
            builder.setTitle(game.getPlayerName() + "'s game(click me)", createLinkHttp(game.getSessionid()));

            //Post time since it was created
            builder.addField("Time since creation", ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) + " MINS", true);

            RestAction<Message> restAction = listingChanel.sendMessage(builder.build());
            Message msg = restAction.complete();
            game.setMessageId(msg.getId());
            echoGameService.savePublic(game);
        }

    }


    private static EmbedBuilder getPubBuilder(EchoGamePublic game, EchoUpdateResponseBody body) {
        EmbedBuilder builder = new EmbedBuilder();
        int maxPlayers = 8;

        builder.setColor(new Color(0, 217, 243));

        String link = body.getPlayers().length  >= maxPlayers ? " " : createLinkHttp(game.getSessionid());

        //Generate link to join the game
        builder.setTitle(game.getPlayerName() + "'s game(if it's blue click me for the website version)", link);

        //Post time since it was created
        builder.addField("Time since creation", ChronoUnit.MINUTES.between(game.getTimeGameCreated(), LocalDateTime.now()) + " MINS", true);

        String status;
        switch(body.getGame_status()){
            case "pre_match":
                status = "Waiting to start....";
                break;
            case "playing":
                status = "Playing";
                break;
            case "score":
                status  = "Gooooooaaaaal";
                break;
            case "round_start":
                status = "In the tubes, ready to launch!";
                break;
            case "round_over":
                status = "Round completed";
                break;
            case "post_match":
                status = "Set Completed";
                break;
            case "pre_sudden_death":
            case "sudden_death":
                status = "SUDDENT DEATH";
                break;
            case "post_sudden_death":
                status = (body.getBlue_points() > body.getOrange_points() ? "Orange" : "Blue") + " choked hard";
                break;
            default:
                status = "????";
        }

        builder.addField("Game Status", status, true);

        builder.addField("Score", String.format("Orange [%s] - [%s] Blue", body.getOrange_points(), body.getBlue_points()), false );

        builder.addField("Game Clock" , body.getGame_clock_display(), false);

        StringBuilder playerList = new StringBuilder();
        playerList.append(" ");
        int playerSize;
        if (body.getPlayers() != null) {
            playerSize = body.getPlayers().length;
            for (String player : body.getPlayers())
                playerList.append(player.equals("Kungg") ? "Kungg aka 'Choke Master'" : player).append("\n");
        }
            else
                playerSize = 0;


        builder.addField(String.format("Players [%s]",playerSize), playerList.toString(), false);


        String directLink = playerSize >= maxPlayers ? "Full Game" : createLinkEchoProtocol(body.getSessionid());


        builder.addField("Direct Join Link", directLink,false);

        return builder;
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

    public static String createLinkHttp(String ID) {
        return "http://echovrprotocol.com/api/" + DeploymentSettings.API_CONTROLLER_VERSION + "/joinGame?lobbyId=" + ID;
    }

    public static String createLinkEchoProtocol(String sessionid){
        return String.format("<echoprotocol://launch:%s>", sessionid);
    }

    public static Optional<GuildConfig> getConfig(String guildId) {
        return guildService.getById(guildId);
    }

    public static void saveConfig(GuildConfig config) {
        guildService.registerServer(config);
    }

    public static void registerPublicGame( String userId, String guildId) {
        EchoGamePublic game = new EchoGamePublic();
        game.setSessionid("no");
        game.setPlayerID(userId);
        game.setGuildId(guildId);
        game.setTimeGameCreated(LocalDateTime.now());
        game.setInUse(true);
        game.setConnectedToLiveClient(false);
        echoGameService.savePublic(game);
    }


    public static void confirmAutoPublicGame(EchoLiveRequestBody body) {
        EchoGamePublic newGame = echoGameService.getPublicGameByUserId(body.getDiscord_user_id());
        //For now default to echo scrim organizer
        newGame.setGuildId(body.getGuild_id());
        newGame.setConnectedToLiveClient(true);
        newGame.setPlayerName(body.getClient_name());
        newGame.setSessionid(body.getSessionid());
        newGame.setTimeGameCreated(LocalDateTime.now());
        echoGameService.savePublic(newGame);
        updateAllPublicGamesList(EchoUnit229Application.jda.getGuildById(body.getGuild_id()));
    }

    public static void updateAutoPublicGame(EchoUpdateResponseBody body) {
        EchoGamePublic pub = echoGameService.getPublicGameBySessionId(body.getSessionid());
        Guild guild = EchoUnit229Application.jda.getGuildById(pub.getGuildId());
        GuildConfig config = getConfig(guild.getId()).get();
        pub.setTimeLastLiveUpdate(LocalDateTime.now());
        guild.getTextChannelById(config.getPublicListingChannelId()).editMessageById(
                pub.getMessageId(),
                getPubBuilder(pub, body).build()
        ).queue();
        echoGameService.savePublic(pub);

    }

    public static boolean hasActiveGameInGuild(Guild guild, User user) {
        return echoGameService.hasActivePublicIn(guild.getId(), user.getId());
    }

    public static void decommissionGame(Guild guild, User user) {

        if (echoGameService.decommissionGame(guild.getId(), user.getId()))
            privateMessage(user, "Game deleted");
        else
            privateMessage(user, "No active game found but I'm updating the listings anyways");


        updateAllPublicGamesList(guild);

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
                FiggyUtility.createLinkHttp(lobbyId);
        privateMessage(user, msg);

    }

    static Random random = new Random();

    public static String createPublicGameConfirmationLink(Guild guild, User user){
        StringBuilder result = new StringBuilder();
        EchoGamePublic game = echoGameService.getPublicGameByUserId(user.getId());
        long id = game.getId();
        StringBuilder confirmationCode = new StringBuilder();
        for(int i = 0; i < 5; i++)
            confirmationCode.append(((char) random.nextInt(127) + 1));

        game.setConfirmationCode(confirmationCode.toString());
        result.append("<echoprotocol://createpub:");
        result.append(guild.getId()).append(":");
        result.append(user.getId()).append(":");
        result.append(id).append(":");
        result.append(confirmationCode.toString()).append(":");
        result.append(">");
        echoGameService.savePublic(game);
        return result.toString();
    }

}
