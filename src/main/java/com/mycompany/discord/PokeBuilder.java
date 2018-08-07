/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discord;

import java.time.LocalDateTime;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

/**
 *
 * @author sora
 */
public class PokeBuilder extends EmbedBuilder{
    String poke;
    String type;
    String trainer;
    public PokeBuilder(Pokemon mon, String trainer)
    {
        super();
        this.poke=mon.getName();
        this.trainer=trainer;
        switch(mon.getType())
            {
                case "Water":
                    type="Water :droplet:";
                    break;
                case "Fire":
                    type="Fire :fire:";
                    break;
                case "Grass":
                    type="Grass :leaves:";
                    break;
            }
    }
    
    public void getPokemon(Pokemon mon, MessageReceivedEvent e)
        {

            EmbedBuilder builder = new EmbedBuilder();
            Connector c = new Connector();
            c.connect();
            builder.appendField("Type", type, true);
        
        builder.appendField("Level", "level_variable", true);
        builder.appendField("EXP", "0/100", true);
        builder.withAuthorName(e.getAuthor().getName());
        builder.withAuthorIcon("http://i.imgur.com/PB0Soqj.png");
        builder.withAuthorUrl("http://i.imgur.com/oPvYFj3.png");

        builder.withColor(0,0,255);
        builder.withDesc("withDesc");
        builder.withDescription("Tiny Turtle Pokémon");
        builder.withTitle(poke);
        builder.withTimestamp(LocalDateTime.now());
        builder.withUrl("https://www.serebii.net/pokedex-xy/007.shtml");
        builder.withImage("http://25.media.tumblr.com/bfc020e0c9ff3c5f5dd6b990eb8f07fb/tumblr_mx8x86CRQK1qm4353o3_250.gif");

        builder.withFooterIcon("http://i.imgur.com/Ch0wy1e.png");
        builder.withFooterText("footerText");
        builder.withFooterIcon("http://i.imgur.com/TELh8OT.png");
        builder.withThumbnail("https://upload.wikimedia.org/wikipedia/en/3/39/Pokeball.PNG");
        
        c.disConnect();
        }
}
