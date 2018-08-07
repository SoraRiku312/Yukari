/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discord;

import java.util.HashMap;

/**
 *
 * @author sora
 */
public abstract class Pokemon {
    String type;
    int level;
    int exp;
    String name;
    

    
    public Pokemon()
    {
        level=0;
        exp=0;
    }
    
    public String getType()
    {
        return type;
    }
    
    public  int getLevel()
    {
        return level;
    }
    
    public void addExp(int exp)
    {
        this.exp+=exp;
    }
    public int getExp()
    {
        return exp;
    }
    
    public String getName()
    {
        return name;
    }

}
