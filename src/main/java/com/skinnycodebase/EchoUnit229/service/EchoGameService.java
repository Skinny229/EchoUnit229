package com.skinnycodebase.EchoUnit229.service;

import com.skinnycodebase.EchoUnit229.discordintegration.FiggyUtility;
import com.skinnycodebase.EchoUnit229.models.EchoGamePrivate;
import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.service.repos.EchoGamePrivateRepository;
import com.skinnycodebase.EchoUnit229.service.repos.EchoGamePublicRepository;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class EchoGameService {


    private EchoGamePublicRepository echoGamePublicRepository;
    private EchoGamePrivateRepository echoGamePrivateRepository;

    @Autowired
    public void setEchoGamePrivateRepository(EchoGamePrivateRepository echoGamePrivateRepository) {
        this.echoGamePrivateRepository = echoGamePrivateRepository;
    }

    @Autowired
    public void setEchoGamePublicRepository(EchoGamePublicRepository echoGamePublicRepository) {
        this.echoGamePublicRepository = echoGamePublicRepository;
    }


    public ArrayList<EchoGamePublic> findAllActivePublic(String guildId) {
        ArrayList<EchoGamePublic> list = new ArrayList<>();
        for (EchoGamePublic gamePublic : echoGamePublicRepository.findAll())
            if (gamePublic.getGuildId().equals(guildId) && gamePublic.isInUse())
                list.add(gamePublic);
        return list;

    }

    public boolean hasActivePublicIn(String guildid, String userid){
        for(EchoGamePublic pub : findAllActivePublic(guildid))
            if(pub.getPlayerID().equals(userid))
                return true;
        return false;
    }

    public void savePublic(EchoGamePublic game) {
        echoGamePublicRepository.save(game);
    }

    public boolean decommissionGame(String guildId, String discordPlayerId) {
        for (EchoGamePublic game : findAllActivePublic(guildId))
            if (game.getPlayerID().equals(discordPlayerId)) {
                game.setInUse(false);
                savePublic(game);
                return true;
            }
        return false;
    }

    public ArrayList<EchoGamePrivate> getJoinablePrivateGames(String playerID) {
        ArrayList<EchoGamePrivate> result = new ArrayList<>();
        for (EchoGamePrivate game : echoGamePrivateRepository.findAll())
            if (game.getUserId().equals(playerID))
                result.add(game);
        return result;
    }


    public EchoGamePublic getPublicGameBySessionId(String sessionid) {

        for(EchoGamePublic pub : echoGamePublicRepository.findAll())
            if(pub.getSessionid().equals(sessionid))
                return pub;
            return null;
    }

    public EchoGamePublic getPublicGameByUserId(String id) {
        for(EchoGamePublic game : echoGamePublicRepository.findAll())
            if(game.getPlayerID().equals(id) && game.isInUse())
                return game;
        return null;
    }
}
