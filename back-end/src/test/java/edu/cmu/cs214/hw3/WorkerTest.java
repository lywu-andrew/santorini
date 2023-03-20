package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.player.Worker;

import static org.junit.Assert.*;

public class WorkerTest {

    private Worker w1;
    private Worker w2;
    
    @Before
    public void setUp() {
        this.w1 = new Worker(1);
        this.w2 = new Worker(2);
    }

    @Test
    public void testMove() {
        setUp();
        Location next = new Location(0, 0);
        w1.move(next);
        Location pos = w1.getPosition();
        assertTrue(pos.equals(next));
    }

    @Test
    public void testIsAdjLocation() {
        setUp();
        Location loc2 = new Location(1, 1);
        w2.move(loc2);
        Location pos = w2.getPosition();
        int row = pos.getRow();
        int col = pos.getCol();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Location loc = new Location(row + i, col + j);
                assertTrue(w2.isAdjLocation(loc));
            }
        }
        assertFalse(w2.isAdjLocation(new Location(0, 3)));
        assertFalse(w2.isAdjLocation(new Location(3, 3)));
        assertFalse(w2.isAdjLocation(new Location(3, 0)));
    }
}
