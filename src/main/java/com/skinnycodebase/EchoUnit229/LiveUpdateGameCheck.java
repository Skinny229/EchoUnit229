package com.skinnycodebase.EchoUnit229;

import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class LiveUpdateGameCheck implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(LiveUpdateGameCheck.class);

    final
    EchoGameService echoGameService;

    public LiveUpdateGameCheck(EchoGameService echoGameService) {
        this.echoGameService = echoGameService;
    }


    @Override
    public void run() {
        logger.info("Starting loop for the non active game garbage collector");

        while(true) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (EchoGamePublic game : echoGameService.getAllPublicGames())
                if (game.isConnectedToLiveClient() && game.isInUse() && ChronoUnit.SECONDS.between(game.getTimeLastLiveUpdate(), LocalDateTime.now()) >= 30) {
                    logger.info("Found Game to decommission!");
                    echoGameService.decommissionGame(game);
                    logger.info("Game created by [{}] on [{}] deleted", game.getPlayerName(), game.getGuildId());
                }

        }
    }
}
