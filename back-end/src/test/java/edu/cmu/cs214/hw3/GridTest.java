package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.state.Tower;

import static org.junit.Assert.*;

public class GridTest {

    private Grid grid;
    
    @Before
    public void setUp() {
        this.grid = new Grid();
    }

    @Test
    public void testHighest() {
        setUp();
        assertFalse(grid.highest(new Location(0, 0)));
        Location high = new Location(1, 1);
        Tower t = grid.getTower(high);
        t.build();
        t.build();
        t.build();
        assertTrue(grid.highest(high));
    }

    @Test
    public void testTryPlace() {
        setUp();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(0, 1);
        assertTrue(grid.tryPlace(loc1, loc2));
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc2));

        Location loc3 = new Location(1, 0);
        assertFalse(grid.tryPlace(loc1, loc3));
        assertFalse(grid.isOccupied(loc3));
        assertFalse(grid.tryPlace(loc3, loc1));
        assertFalse(grid.isOccupied(loc3));

        setUp();
        loc2 = new Location(0, 0);
        assertFalse(grid.tryPlace(loc1, loc2));
        assertFalse(grid.isOccupied(loc1));
        assertFalse(grid.isOccupied(loc2));
    }

    @Test
    public void testTryMove() {
        setUp();
        Location loc1 = new Location(2, 2);
        Location prev = new Location(0, 0);
        assertTrue(grid.tryPlace(loc1, prev));
        Location next = new Location(1, 1);
        assertTrue(grid.tryMove(prev, next));
        assertTrue(grid.isOccupied(next));
        assertFalse(grid.isOccupied(prev));

        Location loc2 = new Location(0, 1);
        Tower t1 = grid.getTower(loc2);
        t1.build();
        t1.build();
        assertFalse(grid.tryMove(next, loc2));
        assertTrue(grid.isOccupied(next));
        assertFalse(grid.isOccupied(loc2));

        assertFalse(grid.tryMove(loc1, next));
        assertTrue(grid.isOccupied(loc1));
        Location dome = new Location(1, 2);
        Tower t2 = grid.getTower(dome);
        t2.build();
        t2.build();
        t2.build();
        t2.build();
        assertFalse(grid.tryMove(loc1, dome));
        assertTrue(grid.isOccupied(loc1));
    }

    @Test
    public void testTryBuild() {
        setUp();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        assertTrue(grid.tryPlace(loc1, loc2));
        assertFalse(grid.tryBuild(loc1));

        Location build = new Location(2, 2);
        Tower t = grid.getTower(build);
        assertTrue(grid.tryBuild(build));
        assertTrue(t.getLevel() == 1);
        assertTrue(grid.tryBuild(build));
        assertTrue(t.getLevel() == 2);
        assertTrue(grid.tryBuild(build));
        assertTrue(t.getLevel() == 3);
        assertFalse(grid.isOccupied(build));
        assertTrue(grid.tryBuild(build));
        assertTrue(t.getLevel() == 3);
        assertTrue(grid.isOccupied(build)); // domed
    }

    @Test
    public void testIsOccupied() {
        setUp();
        Location loc1 = new Location(0, 4);
        Location loc2 = new Location(3, 2);
        assertTrue(grid.tryPlace(loc1, loc2));
        assertTrue(grid.isOccupied(loc1));
        assertTrue(grid.isOccupied(loc2));
        assertFalse(grid.isOccupied(new Location (0, 0)));
        assertFalse(grid.isOccupied(new Location (4, 4)));
    }
}
