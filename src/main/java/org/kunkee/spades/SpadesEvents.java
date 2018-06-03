/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kunkee.spades;

import java.util.List;

/**
 * Implement this interface and pass it into the constructor of the SpadesGame
 * in order to be notified when game events transpire.
 * @author nathan
 */
public interface SpadesEvents
{
    /**
     * This event is fired when the indicated player plays the indicated card.
     * @param card The card just played
     * @param player The player who played the card
     */
    public void eventCardPlayed(Player player, Card card);
    
    /**
     * This event is fired when the player announces their bid, or intended
     * number of tricks.
     * @param player The player who announces this bid
     * @param bid The bid announced
     */
    public void eventMakeBid(Player player, int bid);
    
    /**
     * This event is fired when a player wins the hand. 
     * @param player The player who won this hand
     */
    public void eventWinTrick(Player player);
            
}

