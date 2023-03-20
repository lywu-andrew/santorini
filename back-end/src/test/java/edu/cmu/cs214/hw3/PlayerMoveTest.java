package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class PlayerMoveTest {

    private Game game;
    private Player p1;
    private Player p2;

    @Before
    public void setUp() {
        game = new Game();
        p1 = game.getPlayer(1);
        p2 = game.getPlayer(2);
    }

    @Test
    public void testMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        Location newLoc = new Location(0, 1);
        assertFalse(grid.isOccupied(newLoc));
        p1.move(loc1, newLoc, grid);
        assertTrue(grid.isOccupied(newLoc));
        assertFalse(grid.isOccupied(loc1));
        Location w1pos = p1.getWorkerPosition(1);
        assertTrue(newLoc.equals(w1pos));
    }

    @Test
    public void testInvalidMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        Location newLoc = new Location(-1, -1);
        p1.move(loc1, newLoc, grid);
        assertTrue(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(newLoc));
        Location w1pos = p1.getWorkerPosition(1);
        assertTrue(loc1.equals(w1pos));
    }

    @Test
    public void testUnadjMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        Location newLoc = new Location(2, 2);
        p1.move(loc1, newLoc, grid);
        assertFalse(grid.isOccupied(newLoc));
        Location w1pos = p1.getWorkerPosition(1);
        assertTrue(loc1.equals(w1pos));
    }

    @Test
    public void testOccupiedMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        Location newLoc = new Location(1, 1);
        p1.move(loc1, newLoc, grid);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc3));
        Location w1pos = p1.getWorkerPosition(1);
        assertFalse(newLoc.equals(w1pos));
    }

    @Test
    public void testUnclimbableMove() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        Location newLoc1 = new Location(4, 3);
        Location newLoc2 = new Location(3, 2);
        Location towerLoc = new Location(0, 1);
        p1.move(loc2, newLoc1, grid);
        p1.build(towerLoc, grid);
        p2.move(loc4, newLoc2, grid);
        p2.build(towerLoc, grid);
        p1.move(loc1, towerLoc, grid);
        assertFalse(grid.isOccupied(towerLoc));
        Location w1pos = p1.getWorkerPosition(1);
        assertFalse(towerLoc.equals(w1pos));
    }
}
