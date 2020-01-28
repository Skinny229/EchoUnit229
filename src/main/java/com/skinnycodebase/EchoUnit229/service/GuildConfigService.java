package com.skinnycodebase.EchoUnit229.service;

import com.skinnycodebase.EchoUnit229.models.GuildConfig;
import com.skinnycodebase.EchoUnit229.service.repos.GuildConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuildConfigService {

    GuildConfigRepository guildConfigRepository;


    @Autowired
    public GuildConfigService(GuildConfigRepository guildConfigRepository) {
        this.guildConfigRepository = guildConfigRepository;
    }

    public Optional<GuildConfig> getById(String guildId){
        return guildConfigRepository.findById(guildId);
    }


    public void registerServer(GuildConfig guild){
        guildConfigRepository.save(guild);
    }



}
