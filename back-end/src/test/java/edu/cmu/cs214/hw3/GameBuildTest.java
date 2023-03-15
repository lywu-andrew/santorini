package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.state.Tower;

import static org.junit.Assert.*;

public class GameBuildTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testBuild() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(4, 4);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(1, 1);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        Location newLoc1 = new Location(0, 1);
        Location newLoc2 = new Location(1, 2);
        game.move(1, newLoc1);
        game.build(loc1);
        Tower tower1 = grid.getTower(loc1);
        assertTrue(tower1.getLevel() == 1);
        game.move(1, newLoc2);
        game.build(loc3);
        Tower tower2 = grid.getTower(loc3);
        assertTrue(tower2.getLevel() == 1);
    }

    @Test
    public void testBuildHighest() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(2, 2);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(4, 4);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        Location newLoc1 = new Location(0, 1);
        Location newLoc2 = new Location(4, 3);
        Location newLoc3 = new Location(1, 1);
        Location newLoc4 = new Location(3, 3);
        Tower tower1 = grid.getTower(loc1);
        Tower tower3 = grid.getTower(loc3);

        game.move(1, newLoc1);
        game.build(loc1); // (0, 0) level 1
        game.move(1, newLoc2);
        game.build(loc3); // (4, 4) level 1
        assertTrue(tower1.getLevel() == 1);
        assertTrue(tower3.getLevel() == 1);

        game.move(1, newLoc3);
        game.build(loc1); // (0, 0) level 2
        game.move(1, newLoc4);
        game.build(loc3); // (4, 4) level 2
        assertTrue(tower1.getLevel() == 2);
        assertTrue(tower3.getLevel() == 2);

        game.move(1, newLoc1);
        game.build(loc1); // (0, 0) level 3
        game.move(1, newLoc2);
        game.build(loc3); // (4, 4) level 3
        assertTrue(tower1.getLevel() == 3);
        assertTrue(tower3.getLevel() == 3);

        game.move(1, newLoc3);
        game.build(loc1); // (0, 0) has dome
        game.move(1, newLoc4);
        game.build(loc3); // (4, 4) has dome
        assertTrue(tower1.isDomed());
        assertTrue(tower3.isDomed());
        assertTrue(tower1.getLevel() == 3);
        assertTrue(tower3.getLevel() == 3);
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc3));

        // tests building on domed tower
        game.move(1, newLoc1);
        game.build(loc1);
        assertTrue(tower1.getLevel() == 3);
        assertTrue(tower3.getLevel() == 3);
    }

    @Test
    public void testInvalidBuild() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(2, 2);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(4, 4);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        game.move(1, new Location(0, 1));
        game.build(new Location(-1, -1));
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
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(4, 4);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        game.move(1, new Location(0, 1));
        Location towerLoc = new Location(3, 4);
        game.build(towerLoc);
        Tower t = grid.getTower(towerLoc);
        assertTrue(t.getLevel() == 0);
    }

    @Test
    public void testOccupiedBuild() { // tests building on worker occupied space
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(2, 2);
        game.placeWorkers(loc1, loc2);
        Location loc3 = new Location(4, 4);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        game.move(2, new Location(2, 3));
        game.build(loc3);
        Tower t = grid.getTower(loc3);
        assertTrue(t.getLevel() == 0);
        assertTrue(grid.isOccupied(loc3));
    }
}