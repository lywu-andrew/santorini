package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class GameTest {

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
    public void testPlaceWorkers() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc2));
        Location[] p1wpos = p1.getWorkerPositions();
        assertTrue(p1wpos[0].equals(loc1));
        assertTrue(p1wpos[1].equals(loc2));
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        assertTrue(grid.isOccupied(loc3));
        assertTrue(grid.isOccupied(loc4));
        Location[] p2wpos = p2.getWorkerPositions();
        assertTrue(p2wpos[0].equals(loc3));
        assertTrue(p2wpos[1].equals(loc4));
    }

    @Test
    public void testRepeatPlaceWorkers() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(0, 0);
        game.placeWorkers(loc1, loc2);
        assertFalse(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(loc2));
    }

    @Test
    public void testOccupiedPlaceWorkers() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc2));
        Location[] p1wpos = p1.getWorkerPositions();
        assertTrue(p1wpos[0].equals(loc1));
        assertTrue(p1wpos[1].equals(loc2));
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(0, 0);
        game.placeWorkers(loc3, loc4);
        assertTrue(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(loc3));
    }

    @Test
    public void testInvalidPlaceWorkers() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(-1, -1);
        Location loc2 = new Location(0, 0);
        game.placeWorkers(loc1, loc2);
        assertFalse(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(loc2));
        setUp();
        loc1 = new Location(0, 0);
        loc2 = new Location(-1, -1);
        game.placeWorkers(loc1, loc2);
        assertFalse(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(loc2));
    }

    @Test
    public void testMove() {
        setUp();
    }
}
