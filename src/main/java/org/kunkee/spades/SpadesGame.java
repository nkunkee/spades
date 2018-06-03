/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kunkee.spades;

import java.util.*;

/**
 * This class provides the mechanics of running a spades game. It deals with 
 * shuffling cards, dealing them, collecting the bids from each player, 
 * running each hand, then collecting tricks and scores. It uses the Player
 * interface to manage talking to player entities, allowing their implementation
 * to interact with the user. It uses the event notifications to communicate
 * significant game state changes, again allowing implementations of them 
 * to handle the details.
 * @see org.kunkee.spades.Player
 * @see org.kunkee.spades.SpadesEvents
 * @author nathan
 */
public class SpadesGame 
{
    private List<Player> players;
    private int [] playerTricks;
    private Integer [] playerScores;
    private int [] playerBids;
    private int winningScore;
    private Set<SpadesEvents> eventSinks;
    
    /**
     * Initialize a game by setting the players and target winning score. 
     * Currently limited to 4 players.
     * @param players A list of the players of the game
     * @param winningScore The score to which the game shall be played
     */
    public SpadesGame(List<Player> players, int winningScore)
    {
        this.players = new ArrayList<Player>(players);
        this.playerScores = new Integer[players.size()];
        this.playerTricks = new int[players.size()];
        this.playerBids = new int[players.size()];
        this.winningScore = winningScore;
        this.eventSinks = new HashSet<SpadesEvents>();
        
        for(int i=0; i<players.size(); i++)
        {
            playerScores[i] = 0;
        }
    }
    
    /**
     * Report if any player has reached the winning score.
     * @return true if a player has reached the winning score
     */
    public boolean isGameOver()
    {
        for(Integer i: playerScores)
        {
            if(i>=winningScore)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Have the game start the playing of a hand. 
     * @param dealer number of the player who is the dealer for this hand, [0-3]
     */
    public void playHand(int dealer)
    {
        int leads = dealer;
        
        for(int j=0; j<players.size(); j++)
        {
            playerBids[j]=0;
            playerTricks[j]=0;
        }
        
        shuffleAndDealCards();
        
        for(int i=0; i<13; i++)
        {
            leads = playTrick(leads);
        }
        computeScore();
    }
    
    /**
     * Accessor to provide the scores of all players.
     * @return list of all player scores
     */
    public List<Integer> getAllScores()
    {
        return Arrays.asList(playerScores);
    }
    
    /**
     * Create the deck of cards, shuffle them, deal them to each player,
     * then collect the bids of each player.
     */
    private void shuffleAndDealCards()
    {
        ArrayList<Card> fullDeck = new ArrayList<Card>(52);
        // populate a full deck
        for(Card.SUIT mySuit: Card.SUIT.values())
        {
            for(int i=2; i<=14; i++)
            {
                fullDeck.add(new Card(i,mySuit));
            }
        }
        
        Card temp;
        int nextCut=0;
        int nextSwap=0;
        // shuffle
        for(int j=0; j<50; j++)
        {
            nextCut = (int)Math.round( (Math.random()*51+nextCut)%51 );
            nextSwap = (nextCut*2+nextSwap)%51;
            temp = fullDeck.get(nextCut);
            fullDeck.set( nextCut, fullDeck.get( nextSwap ));
            fullDeck.set( nextSwap, temp);
        }
        
        // deal
        for(int k=0; k<players.size(); k++)
        {
            players.get(k).assignHand(fullDeck.subList(k*13, (k+1)*13) );
            playerBids[k] = players.get(k).tricksBidToWin();
            for(SpadesEvents e: eventSinks)
            {
                e.eventMakeBid(players.get(k), playerBids[k]);
            }
        }
    }
    
    /**
     * Based on the bids and tricks taken, compute each player's score. A 
     * player gets 10 points for each trick taken in support of a bid. A player 
     * loses 10 points for each trick bid if they do not make at least their 
     * bid. Any tricks beyond the bid are worth 1 point.
     * TODO: sandbagging
     * TODO: nil bids
     */
    private void computeScore()
    {
        for(int i=0; i<players.size(); i++)
        {
            if(playerTricks[i]<playerBids[i])
            {
                playerScores[i] = playerScores[i] - (playerBids[i] * 10);
            }
            else
            {
                playerScores[i] = playerScores[i] + (playerBids[i] * 10);
                playerScores[i] = playerScores[i] + (playerTricks[i]-playerBids[i]);
            }
        }
    }
    
    /**
     * Based on the leader player given, cycle through each player, collect
     * their cards played, and compute the winner.
     * @param leader the player who starts the hand and chooses the default suit
     * @return the player who won the hand and leads the next one
     */
    private int playTrick(int leader)
    {
        Card.SUIT leaderSuit = null;
        Card [] myTrick = new Card[4];
        int current;
        int winner;
        Card winnerCard;
        Card myCard;
        
        for(int i=0; i<players.size(); i++)
        {
            current = (i+leader)%players.size();
            
            // note that leadSuit defaults to null, saying that player leads
            myTrick[current] = players.get(current).playCard(myTrick, leaderSuit );
            if(i==0)
            {
                leaderSuit = myTrick[current].getSuit();
            }
            for(SpadesEvents e: eventSinks)
            {
                e.eventCardPlayed(players.get(current), myTrick[current]);
            }     
            // TODO: determine if spades have been broken before they can lead
        }
        
        winner=leader;
        winnerCard=myTrick[winner];
        
        for(int j=leader+1; j<(players.size()+leader); j++)
        {
            // get next card that was played
            myCard=myTrick[j%players.size()];
            
            // if the current card is SPADE or matches the lead suit, compare
            // TODO: detect illegal moves by playing out of lead suit when not required
            if( (myCard.getSuit()==Card.SUIT.SPADE) || (myCard.getSuit()==leaderSuit) )
            {
                if(myCard.compareTo(winnerCard) > 0)
                {
                    winner = j%players.size();
                    winnerCard=myTrick[winner];
                }
            }
            // else
            //   always lower, discard
        }
        
        playerTricks[winner]++;
        for(SpadesEvents e: eventSinks)
        {
            e.eventWinTrick(players.get(winner));
        }        
        return winner;
    }
    
    /**
     * Add the indicated object as a recipient of events during game play.
     * @param s an implementation of SpadesEvents that will be notified
     * of events
     */
    public void addEventListener(SpadesEvents s)
    {
        eventSinks.add(s);
    }
}
