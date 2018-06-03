/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kunkee.spades;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * An implementation of a player that uses the provided InputStream and
 * PrintStream for communication. Ideal for single player games or hotseat
 * games with one keyboard.
 * @author nathan
 */
public class KeyboardPlayer implements Player
{
    private String myName;
    private ArrayList<Card> myHand;
    private PrintStream myOutput;
    private InputStream myInput;
    private Scanner inputScanner;
    
    /**
     * Stores the set of cards in the player's hand. And displays it for good 
     * measure.
     * @param hand A list contains the cards for this player
     */
    public void assignHand(List<Card> hand)
    {
        myHand = new ArrayList<Card>(hand);
        Collections.sort(myHand);
        myOutput.print("*** You have been dealt ");
        for(Card c: myHand)
        {
            myOutput.print(c + "; ");
        }
        myOutput.println();
    }

    /**
     * This method is called for this player to choose which card they wish
     * to play in the current hand. If the current player is to lead with the 
     * first card, lead is null. Not all players may have taken a turn by now,
     * so trick may have null values inside.
     * @param trick an array of the cards played so far for this hand. Null 
     * values indicate players who have not played yet.
     * @param lead The suit of the card that was lead for this hand. Null 
     * means that the current player is leading.
     * @return The card that is played in this hand. It is assumed that the 
     * player tracks this and removes it from any internal lists.
     */
    public Card playCard(Card [] trick, Card.SUIT lead)
    {       
        String choiceString;
        int choiceInt=-1;
        Set<Card> ofLeadSuit = new TreeSet<Card>();
        
        if(lead==null)
        {
            myOutput.println("*** You play the lead");
        }
        else
        {
            myOutput.println("*** The lead suit is " + lead);
            myOutput.print("*** So far these cards have been played:");
            for(int j=0; j<trick.length; j++)
            {
                if(trick[j]!=null)
                {
                    myOutput.print("Player " + j + " played " + trick[j]);
                    myOutput.print( (j<trick.length-1)?", ":"." ) ;
                }
                else
                {
                    myOutput.print("NONE ");
                }
            }
            myOutput.println();
        }
        
        myOutput.println("*** Your hand consists of ");
        for(int i=0; i<myHand.size(); i++)
        {
            myOutput.println(i + ": " + myHand.get(i));
            if(myHand.get(i).getSuit()==lead)
            {
                ofLeadSuit.add(myHand.get(i));
            }
        }
        myOutput.println();

        myOutput.println("*** Please enter the number of the card which you wish to play:");
        do
        {
            try
            {
                choiceString = inputScanner.nextLine();
                choiceInt = Integer.parseInt(choiceString.trim());
                if( (!ofLeadSuit.isEmpty()) && myHand.get(choiceInt).getSuit()!=lead)
                {
                    choiceInt = -1; // reset to unknown value to repeat loop
                    throw new Exception("you have cards in the suit lead; you must play one of them");
                }
                if(choiceInt>=myHand.size())
                {
                    choiceInt = -1; // reset to unknown value to repeat loop
                    throw new Exception("you do not have that many cards");
                }
            }
            catch(Exception e)
            {
                myOutput.println("*** I couldn't understand your response due to " + e.getLocalizedMessage());
                myOutput.println("*** Please try again");
            }
        }
        while( (choiceInt<0) && (choiceInt<myHand.size()) );
        
        myOutput.println("*** Playing the " + myHand.get(choiceInt));
        return myHand.remove(choiceInt);
    }

    /**
     * Return the name of this player.
     * @return Name of player
     */    
    public String getName()
    {
        return myName;
    }
    
    /**
     * Create a keyboard based player. This implementation expects for the 
     * player to be able to enter card choices at the keyboard during game play.
     * @param name name of the player
     * @param output mechanism for the implementation to output to the player
     * @param input mechanism for the implementation to input from the player
     */
    public KeyboardPlayer(String name, PrintStream output, InputStream input)
    {
        myName = name;
        myHand = new ArrayList<Card>(13);
        myOutput = output;
        myInput = input;
        inputScanner = new Scanner(myInput);
    }
    
    /**
     * Query the user to find out how many tricks to bid, or are expected to 
     * be won.
     * TODO: nil bids
     * @return the bid or number of expected tricks 
     */
    public int tricksBidToWin()
    {
        String choiceString;
        int choiceInt=-1;
        
        myOutput.println("*** Your hand consists of ");
        for(int i=0; i<myHand.size(); i++)
        {
            myOutput.println(i + ": " + myHand.get(i));
        }
        myOutput.println();

        myOutput.println("*** Please enter the bid or number of tricks you expect to win:");
        do
        {
            try
            {
                choiceString = inputScanner.nextLine();
                choiceInt = Integer.parseInt(choiceString.trim());
            }
            catch(Exception e)
            {
                myOutput.println("*** I couldn't understand your response due to " + e.getLocalizedMessage());
                myOutput.println("*** Please try again");
            }
        }
        while( (choiceInt<1) && (choiceInt>14) );
        
        return choiceInt;
    }
    
}
