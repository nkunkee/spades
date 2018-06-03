/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kunkee.spades;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testing against the public interface of the spades game to ensure
 * that things are all working.
 * @author nathan
 */
public class SpadesGameTest implements SpadesEvents
{
    private int countCards=0;
    private int countHands=0;
    
    /**
     * Tests the setup and execution of the game through one hand.
     * Indirectly, this asserts that shuffling and dealing are working.
     * Directly, this checks that all players have a score when done,
     * indicating that they made a bid and either met it or did not. It
     * is not possible for a player's score to remain at 0 after playing
     * a hand.
     * Directly, this checks that all 52 cards have been played.
     * Directly, this checks that 13 hands have been played.
     */
    @Test
    public void playGame()
    {
        List<Integer> scores;
        ArrayList<Player> testPlayers = new ArrayList<Player>(4);
        testPlayers.add(new TestPlayer("TestPlayer1"));
        testPlayers.add(new TestPlayer("TestPlayer2"));
        testPlayers.add(new TestPlayer("TestPlayer3"));
        testPlayers.add(new TestPlayer("TestPlayer4"));
        
        SpadesGame s = new SpadesGame(testPlayers, 100);
        s.addEventListener(this);
        
        s.playHand(0);
        
        scores = s.getAllScores();
        // now assert that everyone has a score, so none stayed 0
        for(Integer i: scores)
        {
            Assert.assertFalse(i==0);
        }
        Assert.assertEquals(countCards, 52);
        Assert.assertEquals(countHands, 13);
    }

    public void eventCardPlayed(Player player, Card card)
    {
        System.out.println("Player " + player.getName() + " just played card " + card);
        countCards++;
    }

    public void eventMakeBid(Player player, int bid)
    {
        System.out.println("Player " + player.getName() + " just bid " + bid);
    }

    public void eventWinTrick(Player player)
    {
        System.out.println("Player " + player.getName() + " won the hand.");
        countHands++;
    }
}
