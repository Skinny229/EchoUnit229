package com.skinnycodebase.EchoUnit229.service;

import com.skinnycodebase.EchoUnit229.models.EchoGame;
import com.skinnycodebase.EchoUnit229.models.EchoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;


@Service
public class EchoGameService {


    private EchoGameRepository echoGameRepository;

    @Autowired
    public void setEchoGameRepository(EchoGameRepository echoGameRepository) {
        this.echoGameRepository = echoGameRepository;
    }

    public Iterable<EchoGame> findAll() {
        return echoGameRepository.findAll();
    }

    public EchoGame save(EchoGame game) {
        return echoGameRepository.save(game);
    }

    public boolean deleteGameByPlayerID(String playerID) {
        for (EchoGame game : findAll())
            if (game.getPlayerID().equals(playerID)){
            echoGameRepository.deleteById(game.getId());
            return true;
        }
        return false;
    }
}
