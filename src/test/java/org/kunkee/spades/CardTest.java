/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kunkee.spades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test the primary functionality of cards.
 * @author nathan
 */
@RunWith(JUnit4.class)
public class CardTest {
    
    /**
     * Test that boundary conditions for creating cards are honored.
     */
    @Test(expected= IllegalArgumentException.class)
    public void newIllegalCard()
    {
        Card illegal = new Card(0,Card.SUIT.HEART);
    }
    
    /**
     * Test that cards may be added to a collection and then sorted. Exercises
     * the legal boundaries of creating cards, and exercises the compreTo
     * function during the sort, and exercises the equals function to validate
     * the results.
     */
    @Test
    public void sortsInOrder()
    {
        List sortMe = new ArrayList();
        sortMe.add(new Card(2, Card.SUIT.SPADE));
        sortMe.add(new Card(2, Card.SUIT.HEART));
        sortMe.add(new Card(2, Card.SUIT.DIAMOND));
        sortMe.add(new Card(14,Card.SUIT.HEART));
        sortMe.add(new Card(14,Card.SUIT.SPADE));
        sortMe.add(new Card(14,Card.SUIT.CLUB));
        sortMe.add(new Card(14,Card.SUIT.DIAMOND));
        sortMe.add(new Card(2, Card.SUIT.CLUB));
        System.out.println(sortMe);
        Collections.sort(sortMe);
        System.out.println(sortMe);
        
        Card lowest = new Card(2, Card.SUIT.DIAMOND);
        Card highest = new Card(14,Card.SUIT.SPADE);
        Assert.assertTrue("lowest card is not in its place", lowest.equals(sortMe.get(0)));
        Assert.assertTrue("higest card is not in its place", highest.equals(sortMe.get(sortMe.size()-1)));
    }
    
    /**
     * Test that cards are the same when created as the same. Tests the 
     * equals and hashCode methods.
     */
    @Test
    public void sameness()
    {
        Card first = new Card(3, Card.SUIT.CLUB);
        Card second = new Card(13, Card.SUIT.HEART);
        Assert.assertFalse("cards incorrectly marked equal", first.equals(second));
        Assert.assertNotSame(second.hashCode(), first.hashCode());
        
        Card third = new Card(3, Card.SUIT.CLUB);
        Assert.assertTrue(first.equals(third));
        Assert.assertSame(first.hashCode(),third.hashCode());
    }
}
