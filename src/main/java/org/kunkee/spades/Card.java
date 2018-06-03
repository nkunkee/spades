/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kunkee.spades;

/**
 * Represents a card from a deck. Numbers are skewed for ace high, so
 * numerically from 2 (deuce) to 14 (ace high). A public enum is used for the 
 * suit. Comparison, equality, and hash codes are provided.
 * @author nathan
 */
public class Card implements Comparable<Card> {

    /**
     * Accessor for the rank. A number from 2 to 14 (ace high).
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * The suit of the card as given by the Card.SUIT enum.
     * @return the suit
     */
    public SUIT getSuit() {
        return suit;
    }

    /**
     * Enumerated type listing the suits available for cards. Sets diamond 
     * at 0 lowest and spades at 3 highest.
     */
    public static enum SUIT {
        /**
         * The suit of diamonds.
         */
        DIAMOND,
        /**
         * The suit of clubs.
         */
        CLUB,
        /**
         * The suit of hearts.
         */
        HEART,
        /**
         * The suit of spades. Highest as trump suit.
         */
        SPADE
    };
    
    /**
     * The rank of a card. [2,14] only.
     */
    private int rank;
    
    /**
     * The suit of a card as given by the enum.
     */
    private SUIT suit;
    
    /**
     * Comparable interface implementation allowing cards to be compared and
     * sorted by classlib utilities. Passing next as null will cause an 
     * exception to be thrown.
     * @param next that which to compare with
     * @return 0 for the same, -1 for 'less', 1 for 'greater'
     */
    public int compareTo(Card next)
    {
        if(next == null)
        {
            throw new NullPointerException();
        }
        
        if(next.getSuit() == this.getSuit())
        {
            if(next.getRank()<this.getRank())
            {
                return 1;
            }
            else if(next.getRank()>this.getRank())
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return this.getSuit().ordinal()-next.getSuit().ordinal();
        }
    }
    
    /**
     * Implementation of equals for use by classlib utilities.
     * @param next object to which to compare
     * @return true if they are the same, false otherwise
     */
    @Override
    public boolean equals(Object next)
    {
        if(next == null)
        {
            return false;
        }
        
        if( !(next instanceof Card) )
        {
            return false;
        }
        Card target = (Card)next;
        
        if( (target.getRank()==this.getRank()) && (target.getSuit()==this.getSuit()) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Implementation of pretty printing of a card. Useful for debugging and 
     * classlib utilities.
     * @return a string version of the card
     */
    @Override
    public String toString()
    {
        String prettyRank;
        switch(this.getRank())
        {
            case 11: prettyRank = "Jack"; break;
            case 12: prettyRank = "Queen"; break;
            case 13: prettyRank = "King"; break;
            case 14: prettyRank = "Ace"; break;
            default: prettyRank = Integer.toString(getRank());
        }
        return this.getSuit().name() + " " + prettyRank;
    }
    
    /**
     * Public constructor that takes a rank and suit.
     * @param r rank of the new card; [2,14] inclusive
     * @param s suit of the new card
     */
    public Card(int r, SUIT s)
    {
        if( (r>=2) && (r<=14))
        {
            this.rank = r;
        }
        else
        {
            throw new IllegalArgumentException("Rank of a card must be between 2 (deuce) and 14 (ace)");
        }
        this.suit = s;
    }
    
    /**
     * Implementation of a hash value function for cards to be used with 
     * classlib utilities.
     * @return numeric value suitable for hashing structures
     */
    @Override
    public int hashCode()
    {
        return (100*suit.ordinal() + rank);
    }
}
