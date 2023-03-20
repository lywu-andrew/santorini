package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.state.Tower;

import static org.junit.Assert.*;

public class PlayerBuildTest {

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
    public void testBuild() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        Location newLoc1 = new Location(0, 1);
        Location newLoc2 = new Location(1, 2);
        p1.move(loc1, newLoc1, grid);
        p1.build(loc1, grid);
        Tower tower1 = grid.getTower(loc1);
        assertTrue(tower1.getLevel() == 1);
        p2.move(loc3, newLoc2, grid);
        p2.build(loc3, grid);
        Tower tower2 = grid.getTower(loc3);
        assertTrue(tower2.getLevel() == 1);
    }

    @Test
    public void testBuildHighest() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(2, 2);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(4, 4);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        Location newLoc1 = new Location(0, 1);
        Location newLoc2 = new Location(4, 3);
        Location newLoc3 = new Location(1, 1);
        Location newLoc4 = new Location(3, 3);
        Tower tower1 = grid.getTower(loc1);
        Tower tower3 = grid.getTower(loc3);

        p1.move(loc1, newLoc1, grid);
        p1.build(loc1, grid); // (0, 0) level 1
        p2.move(loc3, newLoc2, grid);
        p2.build(loc3, grid); // (4, 4) level 1
        assertTrue(tower1.getLevel() == 1);
        assertTrue(tower3.getLevel() == 1);

        p1.move(newLoc1, newLoc3, grid);
        p1.build(loc1, grid); // (0, 0) level 2
        p2.move(newLoc2, newLoc4, grid);
        p2.build(loc3, grid); // (4, 4) level 2
        assertTrue(tower1.getLevel() == 2);
        assertTrue(tower3.getLevel() == 2);

        p1.move(newLoc3, newLoc1, grid);
        p1.build(loc1, grid); // (0, 0) level 3
        p2.move(newLoc4, newLoc2, grid);
        p2.build(loc3, grid); // (4, 4) level 3
        assertTrue(tower1.getLevel() == 3);
        assertTrue(tower3.getLevel() == 3);

        p1.move(newLoc1, newLoc3, grid);
        p1.build(loc1, grid); // (0, 0) has dome
        p2.move(newLoc2, newLoc4, grid);
        p2.build(loc3, grid); // (4, 4) has dome
        assertTrue(tower1.isDomed());
        assertTrue(tower3.isDomed());
        assertTrue(tower1.getLevel() == 3);
        assertTrue(tower3.getLevel() == 3);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc3));

        // tests building on domed tower
        p1.move(newLoc3, newLoc1, grid);
        p1.build(loc1, grid);
        assertTrue(tower1.getLevel() == 3);
        assertTrue(tower3.getLevel() == 3);
    }

    @Test
    public void testInvalidBuild() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(2, 2);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(4, 4);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        p1.move(loc1, new Location(0, 1), grid);
        p1.build(new Location(-1, -1), grid);
        for (Integer row = 0; row < 5; row++) {
            for (Integer col = 0; col < 5; col++) {
                Tower t = grid.getTower(new Location(row, col));
                assertTrue(t.getLevel() == 0);
            }
        }
    }

    @Test
    public void testUnadjBuild() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(2, 2);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(4, 4);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        p1.move(loc1, new Location(0, 1), grid);
        Location towerLoc = new Location(3, 4);
        p1.build(towerLoc, grid);
        Tower t = grid.getTower(towerLoc);
        assertTrue(t.getLevel() == 0);
    }

    @Test
    public void testOccupiedBuild() { // tests building on worker occupied space
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(2, 2);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(4, 4);
        Location loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        p1.move(loc2, new Location(2, 3), grid);
        p1.build(loc3, grid);
        Tower t = grid.getTower(loc3);
        assertTrue(t.getLevel() == 0);
        assertTrue(grid.isOccupied(loc3));
    }
}