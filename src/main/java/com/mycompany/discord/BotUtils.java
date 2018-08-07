/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discord;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;
import java.time.Instant;
import java.time.LocalDateTime;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IGuild;
import java.awt.Color;
import java.util.concurrent.TimeUnit;
import sx.blah.discord.api.internal.json.requests.PresenceUpdateRequest;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import static sx.blah.discord.handle.obj.StatusType.ONLINE;
class BotUtils {

    // Constants for use throughout the bot
    static String BOT_PREFIX = "!";
    static int num1=0, iq=0, addIQ;
    static boolean iqFlag=false;
    static IMessage a, b;
    // Handles the creation and getting of a IDiscordClient object for a token
    static IDiscordClient getBuiltDiscordClient(String token){

        // The ClientBuilder object is where you will attach your params for configuring the instance of your bot.
        // Such as withToken, setDaemon etc
        return new ClientBuilder()
                .withToken(token)
                .build();

    }

    // Helper functions to make certain aspects of the bot easier to use.
    static void sendMessage(IChannel channel, String message){

        // This might look weird but it'll be explained in another page.
        RequestBuffer.request(() -> {
            try{
                channel.sendMessage(message);
            } catch (DiscordException e){
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }
    
    static void russianRoulette(MessageReceivedEvent e){
        IChannel channel = e.getChannel();
        String name = e.getAuthor().getName();
        long ID = e.getAuthor().getLongID();
        RequestBuffer.request(() -> {
            try{
                if(num1==0)
                    num1=(int)(Math.random()*6+1);
                if(num1==6){
                    channel.sendMessage("...");
                    channel.sendMessage(name + " pulled the trigger. 🔫");
                    channel.sendMessage( name + " blew their brains out and lost 10 IQ! RIP💀 ");
                    addIQ=-10;
                    addIQ(channel, ID);
                    num1=0;
                } else{
                    channel.sendMessage("...");
                    channel.sendMessage(name + " pulled the trigger. 🔫");
                    channel.sendMessage(name + " survives!👼 +2 IQ");
                    addIQ=2;
                    addIQ(channel, ID);
                    num1++;
                }
            }catch(DiscordException a){
                System.err.println("Message could not be sent with error: ");
                a.printStackTrace();
            }
        });
    }
    
    
    static void addReaction(IChannel channel, IMessage message){
        RequestBuffer.request(() -> {
            try{
                ReactionEmoji reaction = ReactionEmoji.of("FeelsChubbyMan", 372916629488795649L);
                message.addReaction(reaction);
            } catch(DiscordException e){
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }
    
    static void intelligence(MessageReceivedEvent e){
        RequestBuffer.request(() -> {
            
                IChannel channel = e.getChannel();
                String name = e.getAuthor().getName();
                long ID = e.getAuthor().getLongID();
                int newIQ=MyEvents.abc.getIQ(ID);

                    channel.sendMessage("Your IQ is " + newIQ+"!");
                    if(newIQ<=50)
                     channel.sendMessage("https://cdn.discordapp.com/emojis/281152294509084672.png");
                    if(newIQ>50&&newIQ<200)
                        channel.sendMessage("https://cdn.discordapp.com/emojis/302583814977355776.png");
                    if(newIQ>=200)
                        channel.sendMessage("https://image.prntscr.com/image/8P17xR18SJOFJem4yomtIg.png");
            
           
   /*     int id_col=rs.getInt("USERID");
        int iq_col=rs.getInt("IQ");

        channel.sendMessage(id_col + " " + iq_col);
*/

        
            
        });
    }

    static void foundIQ(IChannel channel, String name){
        RequestBuffer.request(() -> {
            try{
                int foundChance=(int)(Math.random()*100+1);
                if(foundChance<80){
                a =    channel.sendMessage("https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Mozgovity_koral.jpg/240px-Mozgovity_koral.jpg " +
                     " \nYou found a brain coral!");
                    addIQ=10;
                }
                if(foundChance>80&&foundChance<101){
                 a=   channel.sendMessage("https://images-na.ssl-images-amazon.com/images/I/91%2BpVzw8qEL._SX342_.jpg "
                    +"\nYou found a copy of Rick and Morty on Blu-Ray!");
                    addIQ=20;
                }
                b=    channel.sendMessage("type !evolve to raise your IQ!");
                    iqFlag=true;
            } catch (DiscordException e){
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }
    
    static void newAddIQ(IChannel channel, String name, long ID){
        try{
        boolean idFlag=false;
        String host = "jdbc:derby://localhost:1527/DiscordBot";
        String uName = "sorariku312";
        String uPass = "Burchw1008";
        Connection con = DriverManager.getConnection(host, uName, uPass);
        Statement stmt = con.createStatement();
        String SQL = "SELECT * FROM IQ_TABLE";
        ResultSet rs = stmt.executeQuery(SQL);
        while(rs.next())
        {
            if(rs.getLong("USERID")==ID)
            {
                channel.sendMessage("ID Found");
             idFlag=true;
            }
        }
        if(!idFlag)
        {
            channel.sendMessage("Making new ID...");
            String SQL2 = "INSERT INTO IQ_TABLE(USERID, IQ) VALUES ("+ID+", 50)" ;
            PreparedStatement pstmt = con.prepareStatement(SQL2);
            pstmt.executeUpdate();
             pstmt.close();
        }
        
        stmt.close();
        rs.close();
   /*     int id_col=rs.getInt("USERID");
        int iq_col=rs.getInt("IQ");

        channel.sendMessage(id_col + " " + iq_col);
*/
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    static void evolution(MessageReceivedEvent e){
                RequestBuffer.request(() -> {
                    
                    MyEvents.abc.addIQ(e.getAuthor().getLongID(), addIQ);
                    a.delete();
                    b.delete();
                    e.getChannel().sendMessage("WUBBA LUBBA DUB DUB <:100YearsRick:375367252985380864>\n"
                    +e.getAuthor().getName()+", your IQ is now " + MyEvents.abc.getIQ(e.getAuthor().getLongID()) +"!");
                   
                });
    }
    static void test(MessageReceivedEvent e, List<String> args)
    {
        IChannel channel = e.getChannel();
        channel.sendMessage("your args are " + args);
        channel.sendMessage("Your first arg is " +args.get(0));
        channel.sendMessage("Your 2nd arg is " + args.get(1));
        channel.sendMessage("there are " + args.size() +" elements in the list");
    }


    static void addIQ(IChannel channel, long ID){
                        RequestBuffer.request(() -> {

    try{

        boolean cont=true;
        boolean idFlag=false;
        String host = "jdbc:derby://localhost:1527/DiscordBot";
        String uName = "sorariku312";
        String uPass = "Burchw1008";
        Connection con = DriverManager.getConnection(host, uName, uPass);
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        String SQL = "SELECT * FROM IQ_TABLE";
        ResultSet rs;
        do{
            rs = stmt.executeQuery(SQL);

            rs.beforeFirst();
            while(rs.next())
            {
                if(rs.getLong("USERID")==ID)
                {
                    int newIQ = rs.getInt("IQ") + addIQ;
                    rs.updateInt("IQ", newIQ);
                    rs.updateRow();
                 idFlag=true;
                 cont=false;
                }
            }
            if(!idFlag)
            {
                channel.sendMessage("Making new ID...");
                String SQL2 = "INSERT INTO IQ_TABLE(USERID, IQ) VALUES ("+ID+", 50)" ;
                PreparedStatement pstmt = con.prepareStatement(SQL2);
                pstmt.executeUpdate();
                 pstmt.close();
                 cont=true;
            }
        }while(cont);
        stmt.close();
        rs.close();
   /*     int id_col=rs.getInt("USERID");
        int iq_col=rs.getInt("IQ");

        channel.sendMessage(id_col + " " + iq_col);
*/
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
                        });

    
}
    
    static void shoot(MessageReceivedEvent e, List<String> args)
    {
        IChannel channel = e.getChannel();
        int chance;
        
        if(args.isEmpty())
            channel.sendMessage("You need a target to shoot, baka");
        channel.sendMessage("You shot " + args.get(0)+ "!");
        chance=(int)(Math.random()*100+1);
        if(chance>90)
            channel.sendMessage("You killed them! 💀");
        if(chance>30 && chance <91)
        {
            channel.sendMessage("You shot them but missed any vital organs");
        }
        if(chance<31)
        {
            channel.sendMessage("You missed... -2 IQ");
            addIQ=-2;
            addIQ(channel, e.getAuthor().getLongID());
        }

    }

    static void gamble (MessageReceivedEvent e, List<String> args)
    {
                   RequestBuffer.request(() -> {
        IChannel channel = e.getChannel();
        int num = Integer.parseInt(args.get(0));
        String coin = args.get(1);
        int toss;
        long ID = e.getAuthor().getLongID();
        String name = e.getAuthor().getName();
        
                    if(num<=0||num>MyEvents.abc.getIQ(ID)||!(coin.equalsIgnoreCase("heads") || coin.equalsIgnoreCase("tails")))
                    {
                        channel.sendMessage("Invalid input... -2 IQ");
                        MyEvents.abc.addIQ(ID, 2-2-2);
                        channel.sendMessage("You must have enough IQ to gamble. Your current IQ is " + MyEvents.abc.getIQ(ID));            
                        return;
                    }
                    toss=(int)(Math.random()*100+1);
                    if(toss<=50)
                    {
                        channel.sendMessage("<:pennyhead:385264628550336513>");
                        if(coin.equalsIgnoreCase("heads"))
                                {
                                    MyEvents.abc.addIQ(ID, num);
                                    channel.sendMessage("You won! Your IQ is now " + MyEvents.abc.getIQ(ID) +"!");
                                    return;
                                }
                        else{                    
                                    MyEvents.abc.addIQ(ID, num-num-num);
                            channel.sendMessage("You lost! Your IQ is now " + MyEvents.abc.getIQ(ID) +"!");
                            return;
                        }
                    }
                    else{
                        channel.sendMessage("<:pennytail:385264665258885160>");
                                                if(coin.equalsIgnoreCase("tails"))
                                {
                                    MyEvents.abc.addIQ(ID, num);
                                    channel.sendMessage("You won! Your IQ is now " + MyEvents.abc.getIQ(ID) +"!");
                                    return;
                                }
                        else{                    
                                    MyEvents.abc.addIQ(ID, num-num-num);
                            channel.sendMessage("You lost! Your IQ is now " + MyEvents.abc.getIQ(ID) +"!");
                            return;
                        }
                    }
            });
    }

    static void starter(MessageReceivedEvent e)
    {
        IChannel channel = e.getChannel();
        channel.sendMessage("Starters: Charmander / Bulbasaur / Squirtle");
    }
    static void starter(MessageReceivedEvent e, List<String>args)
    {
        if(args.isEmpty())
        {
            starter(e);
            return;
        }
        IChannel channel = e.getChannel();
        String starter = args.get(0);
        Connector c = new Connector();
        c.connect();

        switch(starter)
        {
            case "charmander":
            case "Charmander":
                Fire f = new Fire("Charmander");
                c.newStarter(e.getAuthor().getLongID(), f);
                break;
            case "Squirtle":
            case "squirtle":
                Water w = new Water("Squirtle");
                c.newStarter(e.getAuthor().getLongID(), w);
                break;   
            case "Bulbasaur":
            case "bulbasaur":
                Grass g = new Grass("Bulbasaur");
                c.newStarter(e.getAuthor().getLongID(), g);
                break;
            default:
                channel.sendMessage("Please choose a valid Pokemon.");
                break;

        }
    channel.sendMessage("Your Pokemon is " + c.getPoke(e.getAuthor().getLongID()));
                c.disConnect();
    }
    
    static void tester(MessageReceivedEvent e)
    {
        IChannel channel = e.getChannel();
        Connector c = new Connector();
        c.connect();
        c.addIQ(e.getAuthor().getLongID(), 1);
        c.disConnect();
        channel.sendMessage("Testing... +1 IQ?");
    }
    
    static void squirtTest(MessageReceivedEvent e)
    {
    EmbedBuilder builder = new EmbedBuilder();

    builder.appendField("Type", "Water :droplet: ", true);
    builder.appendField("Level", "level_variable", true);
    builder.appendField("EXP", "0/100", true);
    builder.withAuthorName("Yukari");
    builder.withAuthorIcon("http://i.imgur.com/PB0Soqj.png");
    builder.withAuthorUrl("http://i.imgur.com/oPvYFj3.png");

    builder.withColor(0,0,255);
    builder.withDesc("withDesc");
    builder.withDescription("Tiny Turtle Pokémon");
    builder.withTitle("Squirtle");
    builder.withTimestamp(LocalDateTime.now());
    builder.withUrl("https://www.serebii.net/pokedex-xy/007.shtml");
    builder.withImage("http://25.media.tumblr.com/bfc020e0c9ff3c5f5dd6b990eb8f07fb/tumblr_mx8x86CRQK1qm4353o3_250.gif");

    builder.withFooterIcon("http://i.imgur.com/Ch0wy1e.png");
    builder.withFooterText("footerText");
    builder.withFooterIcon("http://i.imgur.com/TELh8OT.png");
    builder.withThumbnail("https://upload.wikimedia.org/wikipedia/en/3/39/Pokeball.PNG");

  //  builder.appendDesc(" + appendDesc");
  //  builder.appendDescription(" + appendDescription");

    RequestBuffer.request(() -> e.getChannel().sendMessage(builder.build()));
    }
   
    static void tempMessage(MessageReceivedEvent e)
    {
        IChannel channel = e.getChannel();
        
        IMessage a = channel.sendMessage("test test ...");
        
        deleteMessage(e, a);
    }
    
    static void deleteMessage(MessageReceivedEvent e, IMessage a)
    {
        
        IChannel channel = e.getChannel();
        
        channel.sendMessage("deleted");
        a.delete();
    }
    
    static void bulbTest(MessageReceivedEvent e)
    {
    EmbedBuilder builder = new EmbedBuilder();

    builder.appendField("Bulbasaur", "Seed Pokémon", true);

    builder.withAuthorName("Yukari");
    builder.withAuthorIcon("http://i.imgur.com/PB0Soqj.png");
    builder.withAuthorUrl("http://i.imgur.com/oPvYFj3.png");

    builder.withColor(0,255,0);
    builder.withDesc("withDesc");
    builder.withDescription("withDescription");
    builder.withTitle("withTitle");
    builder.withTimestamp(LocalDateTime.now());
    builder.withUrl("http://i.imgur.com/IrEVKQq.png");
    builder.withImage("https://vignette.wikia.nocookie.net/pokesprites/images/8/85/Bulbasaur_XY_Attack_1.gif");

    builder.withFooterIcon("http://i.imgur.com/Ch0wy1e.png");
    builder.withFooterText("footerText");
    builder.withFooterIcon("http://i.imgur.com/TELh8OT.png");
    builder.withThumbnail("http://i.imgur.com/7heQOCt.png");

  //  builder.appendDesc(" + appendDesc");
  //  builder.appendDescription(" + appendDescription");

    RequestBuffer.request(() -> e.getChannel().sendMessage(builder.build()));    
    }
    
    static void search (MessageReceivedEvent e, List<String> args)
    {
       String[] a = new String[args.size()];
       a = args.toArray(a);
       String search="";
        for(int n=0;n<a.length;n++)
        {
            a[n]+="+";
            search+=a[n];
        }
        e.getChannel().sendMessage("https://www.google.com/search?q="+search);
    
 /*   static void poke(MessageReceivedEvent e)
    {
        Connector c = new Connector();
        ();
        //set type in database, build pokemon mon based on if statements of type, use mon to get data and put in embed. type classes for dif moves
        PokeBuilder pb = new PokeBuilder();
        c.connect();
        pb
        c.disConnect();
    }
*/
    }
    
    static void profile (MessageReceivedEvent e)
    {
        long id = e.getAuthor().getLongID();
        String poke;
        int iq;
        String avatar=e.getAuthor().getAvatarURL();
        Color color =e.getAuthor().getColorForGuild(e.getGuild());
        String displayName = e.getAuthor().getDisplayName(e.getGuild());
        String realName=e.getAuthor().getName();
        List<IRole>roles=e.getAuthor().getRolesForGuild(e.getGuild());
        String mainRole=roles.get(0).getName();
        iq = MyEvents.abc.getIQ(id);
        poke = MyEvents.abc.getPoke(id);
        
    EmbedBuilder builder = new EmbedBuilder();

    builder.appendField("Intelligence Quotient", Integer.toString(iq), true);
    builder.appendField("Owned Pokemon", poke, false);

    builder.withAuthorName(displayName);
    builder.withAuthorIcon("http://i.imgur.com/oPvYFj3.png");
    builder.withAuthorUrl("http://i.imgur.com/oPvYFj3.png");

    builder.withColor(color);
    builder.withDesc("withDesc");
    builder.withDescription(mainRole);
    builder.withTitle(realName);
    builder.withTimestamp(LocalDateTime.now());
    builder.withUrl(avatar);
    builder.withImage(avatar);

    builder.withFooterIcon("http://i.imgur.com/Ch0wy1e.png");
    builder.withFooterText("ID: " + id);
    builder.withFooterIcon("http://i.imgur.com/TELh8OT.png");
    builder.withThumbnail("http://i.imgur.com/7heQOCt.png");

  //  builder.appendDesc(" + appendDesc");
   // builder.appendDescription(" + appendDescription");

    RequestBuffer.request(() -> e.getChannel().sendMessage(builder.build()));        
    }
    
    static void help (MessageReceivedEvent e)
    {
        String help = "!shoot [@name] - shoot someone\n"
                + "!roulette - Play Russian Roulette!\n"
                + "!flip [num] [heads/tails] - bet IQ and flip a coin!\n"
                + "!starter - see list of pokemon\n"
                + "!starter [pokemon] - choose a starter\n"
                + "!search [string] - search google for somethin\n"
                + "!talk - make a bot say something random using Markov Chains\n"
                + "!help - see this help message\n"
                + "!profile - see your profile"
                + "!addquote [command] [quote] - add a quote!"
                + "!quote [command] - displays a quote";
        EmbedBuilder builder = new EmbedBuilder();
            builder.appendField("Fun",
                    "!shoot\n!flip\n!starter\n!roulette\n!talk", true);
        
        builder.appendField("Utility", "!profile\n!search\n!help", true);
        builder.appendField("Quotes", "!addquote\n!quote", true);

        builder.withAuthorName(e.getAuthor().getName());
        builder.withAuthorIcon("");
        builder.withAuthorUrl("");

        builder.withColor(0,0,255);
        builder.withDesc("withDesc");
        builder.withDescription("");
        builder.withTitle("");
        builder.withTimestamp(LocalDateTime.now());
        builder.withUrl("");
        builder.withImage("");

        builder.withFooterIcon("");
        builder.withFooterText("Help");
        builder.withFooterIcon("");
        builder.withThumbnail("");
        
        e.getChannel().sendMessage(builder.build());
    }
    
    static void xkcd(MessageReceivedEvent e)
    {
        int chance = (int)(Math.random()*1919);
        
        e.getChannel().sendMessage("https://xkcd.com/"+chance+"/#");
    }
    
    static void test (MessageReceivedEvent e)
    {

        MyEvents.abc.addIQ(e.getAuthor().getLongID(), 1);
         RequestBuffer.request(() ->
                  e.getChannel().sendMessage("Your IQ is "+ MyEvents.abc.getIQ(e.getAuthor().getLongID())));

    }
    static void give(MessageReceivedEvent e, List<String>args)
    {
        System.out.println("arg 1: "+args.get(0) + "\narg 2: "+args.get(1));
        System.out.println(Long.parseLong(args.get(1).replaceAll("\\D+", "")));
        long bID = Long.parseLong(args.get(1).replaceAll("\\D+", ""));
        long aID = e.getAuthor().getLongID();
        
        int iq = Integer.parseInt(args.get(0));
        
        MyEvents.abc.addIQ(aID, iq - iq - iq);
        MyEvents.abc.addIQ(bID, iq);
        
        e.getChannel().sendMessage(e.getAuthor().getName()+" gave "+iq+" IQ to "+args.get(1)+"!");
        
    }
    static void profile (MessageReceivedEvent e, List<String>args)
    {
        if(args.isEmpty())
        {
            profile(e);
            return;
        }
        long id = getIDFromMention(args.get(0));
        IUser user = e.getGuild().getUserByID(id);
        String poke;
        int iq;
        String avatar=user.getAvatarURL();
        Color color =user.getColorForGuild(e.getGuild());
        String displayName = user.getDisplayName(e.getGuild());
        String realName=user.getName();
        List<IRole>roles=user.getRolesForGuild(e.getGuild());
        String mainRole=roles.get(0).getName();
        iq = MyEvents.abc.getIQ(id);
        poke = MyEvents.abc.getPoke(id);
        
    EmbedBuilder builder = new EmbedBuilder();

    builder.appendField("Intelligence Quotient", Integer.toString(iq), true);
    builder.appendField("Owned Pokemon", poke, false);


    builder.withAuthorName(displayName);
    builder.withAuthorIcon("http://i.imgur.com/oPvYFj3.png");
    builder.withAuthorUrl("http://i.imgur.com/oPvYFj3.png");

    builder.withColor(color);
    builder.withDesc("withDesc");
    builder.withDescription(mainRole);
    builder.withTitle(realName);
    builder.withTimestamp(100);
    builder.withUrl(avatar);
    builder.withImage(avatar);

    builder.withFooterIcon("http://i.imgur.com/Ch0wy1e.png");
    builder.withFooterText("ID: " + id);
    builder.withFooterIcon("http://i.imgur.com/TELh8OT.png");
    builder.withThumbnail("http://i.imgur.com/7heQOCt.png");

  //  builder.appendDesc(" + appendDesc");
   // builder.appendDescription(" + appendDescription");

    RequestBuffer.request(() -> e.getChannel().sendMessage(builder.build()));        
    }
    
    static void spam(MessageReceivedEvent e, List <String>args)
    {
        String joined = String.join(" ",args);
        for(int n=0;n<5;n++)
        {
         RequestBuffer.request(() -> {e.getChannel().sendMessage(joined);
         });
        }
    }
    
    static long getIDFromMention(String id)
    {
     return Long.parseLong(id.replaceAll("\\D+", ""));   
    }
    static String getNameFromID(MessageReceivedEvent e, long id)
    {
        return(e.getGuild().getUserByID(id)).getName();
    }
    static void addQuote(MessageReceivedEvent e, List<String>args)
    {
        String com = args.get(0);
        args.remove(0);
        String quote = String.join(" ", args);
        
        MyEvents.abc.addQuote(com, quote);
        e.getChannel().sendMessage("Quote successfully added!");
        MyEvents.abc.iqTable();
    }
    static void seeQuote(MessageReceivedEvent e, List<String> args)
    {
        String com = args.get(0);
        String quote = MyEvents.abc.getQuote(com);
        
        e.getChannel().sendMessage(quote);
        MyEvents.abc.iqTable();
    }
    
    static void waitTest(MessageReceivedEvent e)
    {
        try{
            for(int n=0;n<3;n++)
            {
                e.getChannel().sendMessage(n+1 +" seconds...");
                TimeUnit.SECONDS.sleep(1);
            }
        }catch(InterruptedException err)
        {
            System.out.println(err.getMessage());
        }
    }
    
    static void editTest(MessageReceivedEvent e)
    {
        IMessage a = e.getChannel().sendMessage("a");
        try{
         TimeUnit.SECONDS.sleep(2);
        }catch(InterruptedException err){
            System.out.println(err.getMessage());
        }
        a.edit("b");
    }
    
    static void race(MessageReceivedEvent e, List<String>args)
    {
        String p1 = args.get(0);
        IMessage racer = e.getChannel().sendMessage(p1+" - - - - - - |");
        int racer1=0;
        int chance;
        while(racer1<14)
        {
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException err)
            {
                System.out.println(err.getMessage());
            }
            chance=(int)(Math.random()*100);
            if(chance<40)
            {
                char[] racerArray = racer.toString().toCharArray();
                char temp=racerArray[racer1];
                racerArray[racer1]=racerArray[racer1+2];
                racerArray[racer1+2]=temp;
                racer1+=2;
                String r = new String(racerArray);
                racer.edit(r);
            }

        }
    }
    
    public static void portalBegin(MessageReceivedEvent e)
    {
        e.getChannel().sendMessage("You find a strange gun on the ground. It has a small knob underneath a display and seems to be powered by a small orb in a glass vial.\n"
                + "Pick it up? [!portal yes / !portal no");
        MyEvents.abc.trueFlag(e.getAuthor().getLongID(), "START");

    }
    public static void portalChecker(MessageReceivedEvent e, List<String> args)
    {
        if((MyEvents.abc.checkFlag(e.getAuthor().getLongID(), "START"))==false)
            portalBegin(e);
        else{
            portal1(e,args);
        }
        
    }
    public static void portal1(MessageReceivedEvent e, List<String> args)
    {
        String choice = args.get(0);
        if(choice.equalsIgnoreCase("Yes"))
        {
            e.getChannel().sendMessage("k");
        }
        if(choice.equalsIgnoreCase("No"))
            e.getChannel().sendMessage("kkkkk");
    }
    public static void status(MessageReceivedEvent e, List<String> args)
    {
        String game = String.join(" ", args);
        
        PresenceUpdateRequest a = new PresenceUpdateRequest(ONLINE, game,"");
        
    }
            
}
//    static void logIn(ReadyEvent event)
//    {
//        event.
//        IChannel channel = guild.getDefaultChannel();
//        
//        channel.sendMessage("Logged in.");
//    }
//}