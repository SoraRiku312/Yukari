/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author sora
 */
public class Connector {
        static String host = "jdbc:derby://localhost:1527/Discord";
        static String uName = "sorariku312";
        static String uPass = "willie1";
        static ResultSet rs;
        static Connection con;
        static Statement stmt;
        static String SQL;
        public void connect()
        {
            try{
            con = DriverManager.getConnection(host, uName, uPass);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            SQL = "SELECT * FROM USERS";
            rs = stmt.executeQuery(SQL);
            } catch(SQLException a)
            {
                 System.out.println(a.getMessage());
            }
            
        }
        
        public void disConnect()
        {
            try{
            stmt.close();
            rs.close();
            } catch(SQLException a)
            {
                System.out.println(a.getMessage());
            }
        }
        public void resetIQ(){
            try{
                do{rs = stmt.executeQuery(SQL);
                rs.beforeFirst();
                while(rs.next())
                {
                   int newIQ = 50;
                   rs.updateInt("USER_IQ", newIQ);
                   rs.updateRow();
                   
                }
                }while(rs.isLast());
            }catch(SQLException a){
                System.out.println(a.getMessage());
            }
        }
        public void addIQ(long ID, int change)
        {
            boolean idFlag=false;
            boolean cont=true;
            try{
               do{
                   
            rs = stmt.executeQuery(SQL);
                            rs.beforeFirst();
            while(rs.next())
            {
                if(rs.getLong("USER_ID")==ID)
                {
                    int newIQ = rs.getInt("USER_IQ") + change;
                    rs.updateInt("USER_IQ", newIQ);
                    rs.updateRow();
                 idFlag=true;
                 cont=false;
                }
            }
            if(!idFlag)
            {
                newID(ID);
                 cont=true;
            }
        }while(cont);
               
            }catch(SQLException a)
            {
                System.out.println(a.getMessage());
            }
        }
        
        public void newID(long ID)
        {
            try{
                    String SQL2 = "INSERT INTO USERS(USER_ID, USER_IQ) VALUES ("+ID+", 50)" ;
                PreparedStatement pstmt = con.prepareStatement(SQL2);
                pstmt.executeUpdate();
                pstmt.close();
                iqTable();
            }catch(SQLException a)
            {
                System.out.println(a.getMessage());
            }
        }
        
        public void newStarter(long ID, Pokemon mon)
        {
            boolean cont=true;
            try{
                do{
                    
                    rs.beforeFirst();
                    while(rs.next())
                    {
                        if(rs.getLong("USER_ID")==ID)
                        {
                            if(rs.getString("POKE").equals("None"))
                            {
                                rs.updateString("POKE", mon.getName());
                                rs.updateRow();
                                cont=false;
                                System.out.println("test 2");
                                return;                            
                            }
                            else
                            {
                                System.out.println("test 1");
                                return;
                            }

                        }

                    }
                    newID(ID);
                    System.out.println("test 3");
                }while(cont);
                
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }
        }
        
        public String getPoke(long ID)
        {
            try{
                rs.beforeFirst();
                while(rs.next())
                {
                    if(rs.getLong("USER_ID")==ID)
                    {
                        return rs.getString("POKE");
                    }
                }
            } catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }
            return "None";
        }

        public int getIQ(long ID)
        {
            boolean cont=true;
            try{
                do{
                    rs.beforeFirst();
                    while(rs.next())
                    {
                        if(rs.getLong("USER_ID")==ID)
                        {
                            return rs.getInt("USER_IQ");
                        }
                    }
                    newID(ID);
                }while(cont);
            } catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }
            return -999;
        }
        public void iqTable()
        {
            try{
            SQL = "SELECT * FROM USERS";
            rs = stmt.executeQuery(SQL);
            } catch(SQLException a)
            {
                 System.out.println(a.getMessage());
            }       
        }
        
        public void addQuote(String com, String quote)
        {
            try{SQL = "SELECT * FROM QUOTES";
                rs = stmt.executeQuery(SQL);
                
                String SQL2 = "INSERT INTO QUOTES(COMMAND, RETURN) VALUES ('"+com+"','"+quote+"')" ;
                PreparedStatement pstmt = con.prepareStatement(SQL2);
                pstmt.executeUpdate();
                pstmt.close();
                }catch(SQLException a)
                {
                     System.out.println(a.getMessage());
                }
            
        }
        public String getQuote(String com)
        {
            try{
                SQL = "SELECT * FROM QUOTES";
                rs = stmt.executeQuery(SQL);
                
                rs.beforeFirst();
                while(rs.next())
                {
                    if(rs.getString("COMMAND").equalsIgnoreCase(com))
                    {
                        return rs.getString("RETURN");
                    }
                }
                return("Quote not found!");
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
                return("Error");
            }
        
    }
        public void falseFlag(long ID, String col)
        {
            try{
                rs.beforeFirst();
                while(rs.next())
                {
                    if(rs.getLong("USER_ID")==ID)
                    {
                        if(rs.getBoolean(col)==true);
                        {
                            rs.updateBoolean(col, false);
                            rs.updateRow();
                        }
                    }
                }
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }
        }
        public void trueFlag(long ID, String col)
        {
            try{
                rs.beforeFirst();
                while(rs.next())
                {
                    if(rs.getLong("USER_ID")==ID)
                    {
                        if(rs.getBoolean(col)==false);
                        {
                            rs.updateBoolean(col, true);
                            rs.updateRow();
                        }
                    }
                }
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }
        }
        public boolean checkFlag(long ID, String col)
        {
            try{
                rs.beforeFirst();
                while(rs.next())
                {
                    if(rs.getLong("USER_ID")==ID)
                    {
                        return rs.getBoolean(col);
                    }
                }
            }catch(SQLException err)
            {
                System.out.println(err.getMessage());
            }
            return false;
        }
        
}
