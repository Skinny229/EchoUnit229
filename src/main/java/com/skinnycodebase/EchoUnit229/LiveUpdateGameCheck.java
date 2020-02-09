package com.skinnycodebase.EchoUnit229;

import com.skinnycodebase.EchoUnit229.models.EchoGamePublic;
import com.skinnycodebase.EchoUnit229.service.EchoGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (EchoGamePublic game : echoGameService.findAllActivePublic())
                if (
                        ( !game.isConnectedToLiveClient() &&
                                ChronoUnit.SECONDS.between(game.getTimeGameCreated(), LocalDateTime.now()) >= 180
                                )||
                        game.isConnectedToLiveClient() &&
                        ChronoUnit.SECONDS.between(game.getTimeLastLiveUpdate(), LocalDateTime.now()) >= 60) {
                    logger.info("Found Game to decommission!");
                    logger.info(game.toString());
                    echoGameService.decommissionGame(game);
                    logger.info("Game created by [{}] with id [{}] deleted", game.getPlayerNameDiscord(), game.getGuildId());
                }

        }
    }
}
