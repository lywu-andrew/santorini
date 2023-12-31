package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

import java.util.Map;

import static org.junit.Assert.*;

public class PlayerPlaceWorkersTest {

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
    public void testPlaceWorkers() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        p1.placeWorkers(loc1, loc2, grid);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc2));
        Map<Integer, Location> p1wpos = p1.getWorkerPositions();
        Location p1w1pos = p1wpos.get(1);
        Location p1w2pos = p1wpos.get(2);
        assertTrue(p1w1pos.equals(loc1));
        assertTrue(p1w2pos.equals(loc2));
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        assertTrue(grid.isOccupied(loc3));
        assertTrue(grid.isOccupied(loc4));
        Map<Integer, Location> p2wpos = p2.getWorkerPositions();
        Location p2w3pos = p2wpos.get(1);
        Location p2w4pos = p2wpos.get(2);
        assertTrue(p2w3pos.equals(loc3));
        assertTrue(p2w4pos.equals(loc4));
    }

    @Test
    public void testInvalidPlaceWorkers() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(-1, -1);
        Location loc2 = new Location(0, 0);
        p1.placeWorkers(loc1, loc2, grid);
        assertFalse(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(loc2));
        setUp();
        loc1 = new Location(0, 0);
        loc2 = new Location(-1, -1);
        p1.placeWorkers(loc1, loc2, grid);
        assertFalse(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(loc2));
    }

    @Test
    public void testRepeatPlaceWorkers() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(0, 0);
        p1.placeWorkers(loc1, loc2, grid);
        assertFalse(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(loc2));
    }

    @Test
    public void testOccupiedPlaceWorkers() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        p1.placeWorkers(loc1, loc2, grid);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc2));
        Map<Integer, Location> p1wpos = p1.getWorkerPositions();
        Location p1w1pos = p1wpos.get(1);
        Location p1w2pos = p1wpos.get(2);
        assertTrue(p1w1pos.equals(loc1));
        assertTrue(p1w2pos.equals(loc2));
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(0, 0);
        p2.placeWorkers(loc3, loc4, grid);
        assertTrue(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(loc3));
    }
}
