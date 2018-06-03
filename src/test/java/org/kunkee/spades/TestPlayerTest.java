/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kunkee.spades;

import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Test;

/**
 * This class exercises the TestPlayer to see that it conforms to 
 * requirements (which are to be simple but able to play a valid game).
 * @author nathan
 */
public class TestPlayerTest
{
    /**
     * Test that the bid is related to the number of spade cards.
     */
    @Test
    public void testBid()
    {
        TestPlayer tp = new TestPlayer("testBid");
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(10, Card.SUIT.CLUB));
        hand.add(new Card(10, Card.SUIT.SPADE));
        
        tp.assignHand(hand);
        Assert.assertEquals(1, tp.tricksBidToWin());
    }
    
    /**
     * Test that the bid counts the spade cards, when present.
     */
    @Test
    public void testSpadeBid()
    {
        TestPlayer tp = new TestPlayer("testSpadeBid");
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(10, Card.SUIT.SPADE));
        hand.add(new Card(11, Card.SUIT.SPADE));
        hand.add(new Card(12, Card.SUIT.SPADE));
        hand.add(new Card(13, Card.SUIT.SPADE));
        hand.add(new Card(14, Card.SUIT.SPADE));
        
        tp.assignHand(hand);
        Assert.assertEquals(5, tp.tricksBidToWin());
    }
    
    /**
     * Test that non-spade hands count the queens and higher for a bid.
     */
    @Test
    public void testHeartBid()
    {
        TestPlayer tp = new TestPlayer("testHeartBid");
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(10, Card.SUIT.HEART));
        hand.add(new Card(11, Card.SUIT.HEART));
        hand.add(new Card(12, Card.SUIT.HEART));
        hand.add(new Card(13, Card.SUIT.HEART));
        hand.add(new Card(14, Card.SUIT.HEART));
        
        tp.assignHand(hand);
        Assert.assertEquals(3, tp.tricksBidToWin());
    }

    /**
     * Test that the first card in the hand is played when leading. This also
     * covers the case when there are no cards of the suit lead in the hand
     * to play.
     */
    @Test
    public void testHeartPlayLead()
    {
        Card target = new Card(10, Card.SUIT.HEART);
        Card got;
        TestPlayer tp = new TestPlayer("testHeartPlayLead");
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(10, Card.SUIT.HEART));
        hand.add(new Card(11, Card.SUIT.HEART));
        hand.add(new Card(12, Card.SUIT.HEART));
        hand.add(new Card(13, Card.SUIT.HEART));
        hand.add(new Card(14, Card.SUIT.HEART));
        
        tp.assignHand(hand);
        Card [] trick = {null, null, null, null};
        got = tp.playCard(trick, null);
        Assert.assertEquals(got, target);
    }
        
    /**
     * Test that when playing in the suit lead, play the highest available card.
     */
    @Test
    public void testHeartPlayFollow()
    {
        Card target = new Card(14, Card.SUIT.HEART);    
        TestPlayer tp = new TestPlayer("testHeartPlayFollow");
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(new Card(10, Card.SUIT.HEART));
        hand.add(new Card(11, Card.SUIT.HEART));
        hand.add(new Card(12, Card.SUIT.HEART));
        hand.add(new Card(13, Card.SUIT.HEART));
        hand.add(new Card(14, Card.SUIT.HEART));
        
        tp.assignHand(hand);
        Card [] trick = {new Card(2, Card.SUIT.HEART), null, null, null};
        Assert.assertEquals(tp.playCard(trick, Card.SUIT.HEART), target);
    }        
}
