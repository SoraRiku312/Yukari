/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discord;
import java.io.IOException;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import java.util.*;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

public class MyEvents {
    static int chance;
    static Connector abc;
    @EventSubscriber
    public void onLogIn(ReadyEvent a)
    {
        abc = new Connector();
        abc.connect();
        System.out.println("Bot online.");
                        try{
                Markov.Markov();
                } catch(IOException err)
                {
                    System.out.println(err.getMessage());
                }
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
        
        
        String[] argArray = event.getMessage().getContent().split(" ");
        
//            if(event.getMessageID()>0){
//            chance=(int)(Math.random()*100+1);
//            if(chance<3)
//            {
//                BotUtils.foundIQ(event.getChannel(), event.getAuthor().getName());
//                BotUtils.iqFlag=true;
//            }
//        }
            
            
                
        if(argArray.length==0)
            return;
        
        if(!argArray[0].startsWith(BotUtils.BOT_PREFIX)&&argArray.length>5)
        {
            List<String> wordsList = new ArrayList<>(Arrays.asList(argArray));

            Markov.addWords(wordsList);
            return;
        }
        
        String commandStr = argArray[0].substring(1);
        
        List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
        argsList.remove(0);
        
        
        switch(commandStr)
        {
           // case "test":
              //  BotUtils.test(event, argsList);
              //  break;
            case "shoot":
                BotUtils.shoot(event, argsList);
                break;
            case "avatar":
                BotUtils2.avatar(event, argsList);
                break;
            case "roulette":
                BotUtils2.roulette(event);
                break;
            case "flip": 
                BotUtils.gamble(event, argsList);
                break;
            case "iq":
                BotUtils2.intelCheck(event);
                break;
            case "tester":
                BotUtils.tester(event);
                break;
            case "starter":
                BotUtils.starter(event, argsList);
                break;
            case "squirtTest":
                BotUtils.squirtTest(event);
                break;
            case "bulbTest":
                BotUtils.bulbTest(event);
                break;
            case "temp":
                BotUtils.tempMessage(event);
                break;
            case "evolve":
                if(BotUtils.iqFlag)
                {
                BotUtils.evolution(event);
                }
                break;
            case "poke":
           //     BotUtils.poke(event);
                break;
            case "search":
                BotUtils.search(event, argsList);
                break;
            case "talk":
                event.getChannel().sendMessage(Markov.generateSentence());
                break;
//            case "new":
//                try{
//                Markov.Markov();
//                } catch(IOException err)
//                {
//                    System.out.println(err.getMessage());
//                }
//                break;
            case "profile":
                BotUtils.profile(event, argsList);
                break;
            case "help":
                BotUtils.help(event);
                break;
            case "xkcd":
                BotUtils.xkcd(event);
                break;
            case "test":
                BotUtils.test(event);
                break;
            case "give":
                BotUtils.give(event, argsList);
                break;
            case "spam":
                BotUtils.spam(event, argsList);
                break;
            case "addquote":
                BotUtils.addQuote(event, argsList);
                break;
            case "quote":
                BotUtils.seeQuote(event, argsList);
                break;
            case "waittest":
                BotUtils.waitTest(event);
                break;
            case "edittest":
                BotUtils.editTest(event);
                break;
            case "race":
                BotUtils.race(event, argsList);
                break;
            case "portal":
                BotUtils.portalChecker(event, argsList);
                break;
            case "status":
                BotUtils.status(event, argsList);
                break;
            case "urban":
                BotUtils2.jsonTest(event, argsList);
                break;
            case "yoda":
                BotUtils2.translate(event, argsList);
                break;
            case "music":
                BotUtils2.musicSearch(event, argsList);
                break;
            case "resetalliq":
                BotUtils2.resetalliq(event);
                break;
                    
               }
              
            
        

            
        }
    }


        
       
        /*

        //if(event.getMessage().getAuthor().getName().equalsIgnoreCase("HugeStomper"))
        //    BotUtils.addReaction(event.getChannel(), event.getMessage());
        if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "name"))
            BotUtils.sendMessage(event.getChannel(), event.getAuthor().getName());
     

        if((event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "evolve")&& BotUtils.iqFlag)){
            
            BotUtils.evolution(event.getChannel(), event.getAuthor().getName(), event.getAuthor().getLongID());
            BotUtils.iqFlag=false;
        }
        if(event.getMessage().getContent().startsWith(BotUtils.BOT_PREFIX + "id"))
            BotUtils.newAddIQ(event.getChannel(),event.getAuthor().getName(), event.getAuthor().getLongID());
    }
    
   

}
*/
