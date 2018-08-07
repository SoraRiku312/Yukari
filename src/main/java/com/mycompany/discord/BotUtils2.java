/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discord;

import java.awt.Color;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

/**
 *
 * @author Sora
 */
public class BotUtils2 {
    static String BOT_PREFIX = "!";
    static boolean iqFlag=false;
    static IMessage a,b;
    static int chamber;
    
    static IDiscordClient getBuiltDiscordClient(String token){
        return new ClientBuilder().withToken(token).build();
    }
    
    static void roulette(MessageReceivedEvent e){
        IChannel channel = e.getChannel();
        String name = e.getAuthor().getName();
        long ID = e.getAuthor().getLongID();
        EmbedBuilder builder = new EmbedBuilder();
        
        if(chamber==0)
            chamber=(int)(Math.random()*6+1);

        builder.appendField("🔫 Russian Roulette 🔫","**"+ name + "** pulls the trigger...\n", false);
        builder.appendField("*click*", "...\n\n", false);
        builder.appendField("...\n", "...\n", false);

        if(chamber==6)
        {
            builder.appendField("💀","**" +  name + "** blew their brains out!\n**-10 IQ**", false);
            MyEvents.abc.addIQ(ID, -10);
            chamber=0;
        }
        else
        {
            builder.appendField("👼", "**" + name + "** survived!\n**+3 IQ**", false);
            MyEvents.abc.addIQ(ID, 3);
            chamber++;
        }
        
        builder.withFooterText(name+"'s IQ: "+ MyEvents.abc.getIQ(ID));
        
        RequestBuffer.request(() -> channel.sendMessage(builder.build()));        
            //add color and different possible messages
        }

    static void intelCheck(MessageReceivedEvent e)
    {
        IChannel channel = e.getChannel();
        String name = e.getAuthor().getName();
        int iq = MyEvents.abc.getIQ(e.getAuthor().getLongID());
        String pic="";
        String title="";
        EmbedBuilder builder = new EmbedBuilder();
        
        if(iq<50){
            pic="https://cdn.discordapp.com/emojis/281152294509084672.png";
            title="Dumb Morty";
        }
        if(iq>=50&&iq<200){
            pic="https://cdn.discordapp.com/emojis/302583814977355776.png";
            title="Lawyer Morty";
        }
        if(iq>=200){
            pic="https://image.prntscr.com/image/8P17xR18SJOFJem4yomtIg.png";
            title="Normal Rick";
        }
        final String ti=title;
        final String message=pic;
        builder.withTitle("🤓 Intelligence Quotient 🤓");
        builder.withDescription(name);
        builder.appendField("Your IQ is " + iq, ti, false);
        builder.withImage(message);
        builder.withColor(e.getAuthor().getColorForGuild(e.getGuild()));
        
        RequestBuffer.request(() -> channel.sendMessage(builder.build()));        

    }
    
    static void avatar(MessageReceivedEvent e, List<String> args)
    {
        String av;
        EmbedBuilder embed = new EmbedBuilder();
        Color col;
        if(args.isEmpty())
        {
            av = e.getAuthor().getAvatarURL();
            col = e.getAuthor().getColorForGuild(e.getGuild());
        }
        else
        {
            long ID = BotUtils.getIDFromMention(args.get(0));
            av = e.getGuild().getUserByID(ID).getAvatarURL();
            col = e.getGuild().getUserByID(ID).getColorForGuild(e.getGuild());
        }
        


        final String avatar = av;
        final Color color = col;
        embed.withImage(avatar);
        embed.withColor(color);
        RequestBuffer.request(() -> e.getChannel().sendMessage(embed.build()));
    }
    
    static void jsonTest(MessageReceivedEvent e, List<String> args)
    {
        String[] a = new String[args.size()];
        a=args.toArray(a);
        String search = "";
        String def1, def2;
        String ex1, ex2;
        String word;
        EmbedBuilder builder = new EmbedBuilder();
        
        for(int n=0;n<a.length;n++)
        {
            a[n]+="+";
            search+=a[n];
        }
        try{
                String s = "http://api.urbandictionary.com/v0/define?term="+search;
   // s += URLEncoder.encode(search, "UTF-8");
    URL url = new URL(s);
 
    // read from the URL
    Scanner scan = new Scanner(url.openStream());
    String str = new String();
    while (scan.hasNext())
        str += scan.nextLine();
    scan.close();
 
    // build a JSON object
    JSONObject obj = new JSONObject(str);

 
    // get the first result
    JSONObject res = obj.getJSONArray("list").getJSONObject(0);
    JSONObject res2 = obj.getJSONArray("list").getJSONObject(1);
    word=res.getString("word");
    def1=res.getString("definition");
    ex1=res.getString("example");
    def2=res2.getString("definition");
    ex2=res2.getString("example");
    
    builder.appendField("Definition 1: ", def1, true);
    builder.appendField("Example 1: ", ex1, true);
    builder.appendField("Definition 2: ", def2, true);
    builder.appendField("Example 2: ", ex2, true);
    builder.withTitle("Urban Dictionary");
    builder.withDescription(word);
    RequestBuffer.request(() -> e.getChannel().sendMessage(builder.build()));
//    JSONObject loc =
//        res.getJSONObject("geometry").getJSONObject("location");
//    System.out.println("lat: " + loc.getDouble("lat") +
//                        ", lng: " + loc.getDouble("lng"));
        }catch(Exception err)
        {
            System.out.println(err.getMessage());
        }
    }
    
    static void translate(MessageReceivedEvent e, List<String> args)
    {
    {
        String[] a = new String[args.size()];
        a=args.toArray(a);
        String search = "";
        String text;
        String trans;
        String type;
        EmbedBuilder builder = new EmbedBuilder();
        
        for(int n=0;n<a.length;n++)
        {
            a[n]+="+";
            search+=a[n];
        }
        try{
                String s = "http://api.funtranslations.com/translate/yoda.json?text="+search;
   // s += URLEncoder.encode(search, "UTF-8");
    URL url = new URL(s);
 
    // read from the URL
    Scanner scan = new Scanner(url.openStream());
    String str = new String();
    while (scan.hasNext())
        str += scan.nextLine();
    scan.close();
 
    // build a JSON object
    JSONObject obj = new JSONObject(str);

 
    // get the first result
    JSONObject res = obj.getJSONObject("contents");
    text=res.getString("translated");
    trans=res.getString("text");
    type=res.getString("translation");
    
    builder.appendField("Original Text: ", text, false);
    builder.appendField("Translated Text: ", trans, false);
    builder.withTitle("Fun Translation");
    builder.withDescription(type);
    RequestBuffer.request(() -> e.getChannel().sendMessage(builder.build()));
//    JSONObject loc =
//        res.getJSONObject("geometry").getJSONObject("location");
//    System.out.println("lat: " + loc.getDouble("lat") +
//                        ", lng: " + loc.getDouble("lng"));
        }catch(Exception err)
        {
            System.out.println(err.getMessage());
        }
    }
    }
    static void musicSearch(MessageReceivedEvent e, List<String> args)
    {
        String[] a = new String[args.size()];
        a=args.toArray(a);
        String search = "";
        String def1, def2;
        String ex1, ex2;
        String word;
        EmbedBuilder builder = new EmbedBuilder();
        
        for(int n=0;n<a.length;n++)
        {
            a[n]+="+";
            search+=a[n];
        }
        try{
                String s = "http://ws.audioscrobbler.com/2.0/?method=track."
                        + "search&track="+search+"&api_key=904c046721b92c69e99136401c494013&format=json";
   // s += URLEncoder.encode(search, "UTF-8");
    URL url = new URL(s);
 
    // read from the URL
    Scanner scan = new Scanner(url.openStream());
    String str = new String();
    while (scan.hasNext())
        str += scan.nextLine();
    scan.close();
 
    // build a JSON object
    JSONObject obj = new JSONObject(str);

 
    // get the first result
    JSONObject res = obj.getJSONObject("results").getJSONObject("trackmatches").getJSONArray("track").getJSONObject(0);
    JSONObject res2 = obj.getJSONObject("results").getJSONObject("trackmatches").getJSONArray("track").getJSONObject(1);
    def1=res.getString("name");
    ex1=res.getString("artist");
    def2=res2.getString("name");
    ex2=res2.getString("artist");
    
    builder.appendField(def1, ex1, false);
    builder.appendField(def2, ex2, false);

    builder.withTitle("Last.FM Search");
    builder.withDescription(search);
    RequestBuffer.request(() -> e.getChannel().sendMessage(builder.build()));
//    JSONObject loc =
//        res.getJSONObject("geometry").getJSONObject("location");
//    System.out.println("lat: " + loc.getDouble("lat") +
//                        ", lng: " + loc.getDouble("lng"));
        }catch(Exception err)
        {
            System.out.println(err.getMessage());
        }
    }   
    static void resetalliq(MessageReceivedEvent e){
        EmbedBuilder builder = new EmbedBuilder();
        
        if(e.getAuthor().getLongID() == 124276661666578436L){
            MyEvents.abc.resetIQ();
            builder.withTitle("Everyone's IQ has been reset!");
            RequestBuffer.request(()-> e.getChannel().sendMessage(builder.build()));
        }else{
            MyEvents.abc.addIQ(e.getAuthor().getLongID(), -999);
            builder.withTitle("Nice try dumbass. -999 IQ");
            RequestBuffer.request(() -> e.getChannel().sendMessage(builder.build()));

        }
    }
}
 

