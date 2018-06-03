/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kunkee.spades;

import java.util.List;

/**
 * Interface description of a player. This establishes the contract for how
 * a player interacts with the game. Implementation details of local/remote,
 * interactive, automated, are left to the developer.
 * @author nathan
 */
public interface Player {
    
    /**
     * Stores the set of cards in the player's hand. And displays it for good 
     * measure.
     * @param hand A list contains the cards for this player
     */    
    public void assignHand(List<Card> hand);
    
    /**
     * Method used to get the bid or bargain for the number of tricks this
     * player expects to win.
     * Called after assignHand.
     * @return number of tricks this player expects to win; not allowed to be 0
     */
    public int tricksBidToWin();
    
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
    public Card playCard(Card [] trick, Card.SUIT lead);
  
    /**
     * Return the name of this player.
     * @return Name of player
     */
    public String getName();
   
}
