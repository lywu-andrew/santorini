package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class GameMoveTest {

    private Game game;
    private Worker w1;
    private Worker w2;
    private Worker w3;
    private Worker w4;
    private Player p1;
    private Player p2;

    @Before
    public void setUp() {
        w1 = new Worker(1);
        w2 = new Worker(2);
        p1 = new Player(1, w1, w2);
        w3 = new Worker(1);
        w4 = new Worker(2);
        p2 = new Player(2, w3, w4);
        game = new Game(p1, p2);
    }

    @Test
    public void testMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        Location newLoc = new Location(0, 1);
        assertFalse(grid.isOccupied(newLoc));
        game.move(w1.getID(), newLoc);
        assertTrue(grid.isOccupied(newLoc));
        assertFalse(grid.isOccupied(loc1));
        Location w1pos = p1.getWorkerPosition(w1.getID());
        assertTrue(newLoc.equals(w1pos));
    }

    @Test
    public void testInvalidMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        Location newLoc = new Location(-1, -1);
        game.move(w1.getID(), newLoc);
        assertTrue(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(newLoc));
        Location w1pos = p1.getWorkerPosition(w1.getID());
        assertTrue(loc1.equals(w1pos));
    }

    @Test
    public void testUnadjacentMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        Location newLoc = new Location(2, 2);
        game.move(w1.getID(), newLoc);
        assertFalse(grid.isOccupied(newLoc));
        Location w1pos = p1.getWorkerPosition(w1.getID());
        assertTrue(loc1.equals(w1pos));
    }

    @Test
    public void testOccupiedMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        Location newLoc = new Location(1, 1);
        game.move(w1.getID(), newLoc);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc3));
        Location w1pos = p1.getWorkerPosition(w1.getID());
        assertFalse(newLoc.equals(w1pos));
    }
}
