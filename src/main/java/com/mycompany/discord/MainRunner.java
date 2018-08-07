/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discord;

import sx.blah.discord.api.IDiscordClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author sora
 */
public class MainRunner {
    public static void main(String[] args){
        String token="MzAxMDM5OTAzNzk2NDk0MzM3.DNtxYA.f5At2VUgkBNmVRkpfaYMir242ns";
        IDiscordClient cli = BotUtils.getBuiltDiscordClient(token);
        cli.getDispatcher().registerListener(new MyEvents());
        cli.login();



    }
}
