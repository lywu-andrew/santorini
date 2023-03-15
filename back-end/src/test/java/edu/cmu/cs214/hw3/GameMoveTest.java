package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class GameMoveTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
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
        game.move(loc1, newLoc);
        assertTrue(grid.isOccupied(newLoc));
        assertFalse(grid.isOccupied(loc1));
        Player p1 = game.getPlayer(1);
        Location w1pos = p1.getWorkerPosition(1);
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
        game.move(loc1, newLoc);
        assertTrue(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(newLoc));
        Player p1 = game.getPlayer(1);
        Location w1pos = p1.getWorkerPosition(1);
        assertTrue(loc1.equals(w1pos));
    }

    @Test
    public void testUnadjMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        Location newLoc = new Location(2, 2);
        game.move(loc1, newLoc);
        assertFalse(grid.isOccupied(newLoc));
        Player p1 = game.getPlayer(1);
        Location w1pos = p1.getWorkerPosition(1);
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
        game.move(loc1, newLoc);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc3));
        Player p1 = game.getPlayer(1);
        Location w1pos = p1.getWorkerPosition(1);
        assertFalse(newLoc.equals(w1pos));
    }

    @Test
    public void testUnclimbableMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        Location newLoc1 = new Location(4, 3);
        Location newLoc2 = new Location(3, 2);
        Location towerLoc = new Location(0, 1);
        game.move(loc2, newLoc1);
        game.build(towerLoc);
        game.move(loc4, newLoc2);
        game.build(towerLoc);
        game.move(loc1, towerLoc);
        assertFalse(grid.isOccupied(towerLoc));
        Player p1 = game.getPlayer(1);
        Location w1pos = p1.getWorkerPosition(1);
        assertFalse(towerLoc.equals(w1pos));
    }
}
