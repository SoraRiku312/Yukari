/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discord;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

/**
 *
 * @author sora
 */

public class LogIn {

            
    public static IDiscordClient createClient(String token, boolean login){
        ClientBuilder clientBuilder = new ClientBuilder();
        
        clientBuilder.withToken(token);
        try{
            if(login){
                
                return clientBuilder.login();
            } else {
                return clientBuilder.build();
            }
        } catch (DiscordException e) {
            e.printStackTrace();
            return null;
        }
    }
}


