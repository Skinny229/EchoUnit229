package com.skinnycodebase.EchoUnit229.service;

import com.skinnycodebase.EchoUnit229.models.EchoGame;
import com.skinnycodebase.EchoUnit229.models.EchoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EchoGameService {

    @Autowired
    private EchoGameRepository echoGameRepository;


    public Iterable<EchoGame> findAll(){
        return echoGameRepository.findAll();
    }

    public EchoGame save(EchoGame game){
        return echoGameRepository.save(game);
    }
}
