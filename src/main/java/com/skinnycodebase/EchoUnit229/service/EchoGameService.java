package com.skinnycodebase.EchoUnit229.service;

import com.skinnycodebase.EchoUnit229.models.EchoGamePrivate;
import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.service.repos.EchoGamePrivateRepository;
import com.skinnycodebase.EchoUnit229.service.repos.EchoGamePublicRepository;
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





    public Iterable<EchoGamePublic> findAllPublic() {
        return echoGamePublicRepository.findAll();
    }

    public void savePublic(EchoGamePublic game) {
         echoGamePublicRepository.save(game);
    }

    public boolean deletePublicGameByPlayerID(String playerId) {
        for (EchoGamePublic game : findAllPublic())
            if (game.getPlayerID().equals(playerId)) {
                echoGamePublicRepository.deleteById(game.getId());
                return true;
            }
        return false;
    }

    public ArrayList<EchoGamePrivate> getJoinablePrivateGames(String playerID) {
        ArrayList<EchoGamePrivate> result = new ArrayList<>();
        for (EchoGamePrivate game : echoGamePrivateRepository.findAll())
            if (game.getUserId() == playerID)
                result.add(game);
        return result;
    }


}
