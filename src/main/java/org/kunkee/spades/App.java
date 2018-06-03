package org.kunkee.spades;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * The main application and starting point for a game of spades.
 * This implementation uses the console keyboard and screen to communicate
 * with the players. It also passes the same console and keyboard
 * to a Player implementation designed to work in this setting.
 * This version of the game plays to 100 points.
 * Includes and implementation of SpadesEvents so that game events
 * are reported over the same channels.
 */
public class App implements SpadesEvents
{
    /**
     * Main entry point of the application. Determines the number of 
     * human players, creates Player objects for all players, 
     * and then starts the game.
     * @param args 
     */
    public static void main( String[] args )
    {
        App myApp;
        SpadesGame game;
        Scanner readInput = new Scanner(System.in);
        String stringNumberPlayers;
        int numberPlayers;
        List<Player> players = new ArrayList<Player>(4);
        String namePlayer;
        
        System.out.println("Please enter the number of human players (out of 4):");
        stringNumberPlayers = readInput.nextLine();
        numberPlayers = Integer.parseInt(stringNumberPlayers);
        System.out.println("There will be " + (4-numberPlayers) + " computer players added.");
        for(int i=0; i<numberPlayers; i++)
        {
            System.out.println("Please enter the name for player " + i);
            namePlayer = readInput.nextLine();
            players.add(new KeyboardPlayer(namePlayer.trim(), System.out, System.in) );
        }
        
        for(int j=numberPlayers; j<4; j++)
        {
            namePlayer = "Auto-player " + j;
            players.add( new TestPlayer(namePlayer) );
        }
        
        game = new SpadesGame(players, 100);
        myApp = new App(System.out, System.in);
        game.addEventListener(myApp);
        
        List playerScores;
        int dealer=0;
        while(!game.isGameOver())
        {
            game.playHand(dealer);
            dealer = (dealer+1)%4;
            
            playerScores = game.getAllScores();
            for(int k=0; k<players.size(); k++)
            {
                System.out.println(players.get(k).getName() + " has a score of " 
                                   + playerScores.get(k));
            }
        }
        
    }

    private PrintStream output;
    private InputStream input;

    /**
     * Constructor for the application instance that handles events from 
     * the game progress.
     * @param out an output stream to write events to
     * @param in an input stream in case input is needed
     */
    public App(PrintStream out, InputStream in)
    {
        input=in;
        output=out;
    }

    /**
     * Implementation of the event listener for when cards are played
     * during the game. In this case, write the information to the
     * given output stream.
     * @param player the player who just played a card
     * @param card the card just played
     */
    public void eventCardPlayed(Player player, Card card)
    {
        output.println("==== Player " + player.getName() + " played " + card);
    }

    /**
     * Implementation of the event listener for when a bid is announced.
     * 
     * @param player the player who just bid
     * @param bid the bid of the player
     */
    public void eventMakeBid(Player player, int bid)
    {
        output.println("==== Player " + player.getName() + " made a bid of " + bid);
    }

    /**
     * Implementation of the event listener for when a player wins a trick.
     * @param player the player who won the trick
     */
    public void eventWinTrick(Player player)
    {
        output.println("==== Player " + player.getName() + " won the hand. ====");
    }
}
