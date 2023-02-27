package edu.cmu.cs214.hw3;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Location;

/**
 * implementation is assuming the players are switching off when interacting with the system
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Worker w1 = new Worker(1);
        Worker w2 = new Worker(2);
        Worker w3 = new Worker(1);
        Worker w4 = new Worker(2);
        Player p1 = new Player(1, w1, w2);
        Player p2 = new Player(2, w3, w4);
        Game game = new Game(p1, p2);

        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);

        // are we supposed to do anything here?

    }
}
