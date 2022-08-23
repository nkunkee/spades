/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kunkee.spades;

import java.util.*;

/**
 * A simple implementation of a player for purposes of testing. Although,
 * this implementation also serves as an easy computer player.
 * @author nathan
 */
public class TestPlayer implements Player
{
    TreeSet<Card> myHand;
    String name;
    
    public String getName()
    {
        return name;
    }
    
    /**
     * Basic constructor that requires a player name.
     * @param newName the name of the player
     */
    public TestPlayer(String newName)
    {
        name = newName;
    }
 
    /**
     * Helper function to provide a friendly view of the test player's
     * state.
     * @return a string summarizing the test player's hand
     */
    @Override
    public String toString()
    {
        String s = "Player " + name + " has a hand of " + myHand + " right now.";
        return s;
    }
        
    public void assignHand(List<Card> hand)
    {
        myHand = new TreeSet<Card>(hand);
//        System.out.println(this.toString());
    }
    
    public Card playCard(Card [] trick, Card.SUIT lead)
    {
        Card selected;
        TreeSet<Card> available = new TreeSet<Card>();
        for(Card c: myHand)
        {
            if(c.getSuit()==lead)
            {
                available.add(c);
            }
        }
        
        if(available.size()>0)
        {
            selected=available.last();
        }
        else
        {
            selected = myHand.first();
        }
        
        myHand.remove(selected);
//        System.out.println("Player " + name + " Choosing to play " + selected);
//        System.out.println(this.toString());
        return selected;            
    }
    
    public int tricksBidToWin()
    {
        TreeSet<Card> available = new TreeSet<Card>();
        for(Card c: myHand)
        {
            if(c.getSuit()==Card.SUIT.SPADE)
            {
                available.add(c);
            }
        }
        if(available.size()>0)
        {
            return available.size();
        }
        
        available.clear();
        for(Card c: myHand)
        {
            if(c.getRank()>=12)
            {
                available.add(c);
            }
        }
        return (available.size()>0)?available.size():1 ;
    }
    
}
