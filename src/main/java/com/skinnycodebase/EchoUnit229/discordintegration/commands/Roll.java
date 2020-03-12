package com.skinnycodebase.EchoUnit229.discordintegration.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Random;

public class Roll {
    
    public int Roller(int numOfDice, int numOfSides) {
        Random rnd = new Random();
        int total = 0;
        for (i=0;i<numOfDice;i++){
            int x = rnd.nextInt(numOfSides)+1;
            total += x;
        }
        return total;
    }

    public static void run(MessageReceivedEvent event) {
        String rollCmd = event.getMessage().getContentRaw();
        int numOfDice,numOfSides;
        if(rollCmd.startswith("-")){
            
        }
    }
}
