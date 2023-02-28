package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player p1;
    private Player p2;
    private Worker w1;
    private Worker w2;
    private Worker w3;
    private Worker w4;

    @Before
    public void setUp() {
        this.w1 = new Worker(1);
        Location loc1 = new Location(0, 0);
        this.w1.move(loc1);
        this.w2 = new Worker(2);
        Location loc2 = new Location(1, 1);
        this.w2.move(loc2);
        this.w3 = new Worker(1);
        Location loc3 = new Location(2, 2);
        this.w3.move(loc3);
        this.w4 = new Worker(2);
        Location loc4 = new Location(3, 3);
        this.w4.move(loc4);
        this.p1 = new Player(1, w1, w2);
        this.p2 = new Player(2, w3, w4);
    }

    @Test
    public void testIsAdjLocation() {
        setUp();
        Location pos2 = w2.getPosition();
        int row = pos2.getRow();
        int col = pos2.getCol();
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                Location loc = new Location(row + i, col + j);
                assertTrue(p1.isAdjLocation(w2.getID(), loc));
            }
        }
        assertFalse(p1.isAdjLocation(w2.getID(), new Location(0, 3)));
        assertFalse(p1.isAdjLocation(w2.getID(), new Location(3, 3)));
        assertFalse(p1.isAdjLocation(w2.getID(), new Location(3, 0)));
    }

    @Test
    public void testisAdj() {
        setUp();
        Location loc1 = new Location(4, 4);
        assertTrue(p2.isAdj(loc1));
        Location loc2 = new Location(0, 4);
        assertFalse(p2.isAdj(loc2));
    }

    @Test
    public void testPlace() {
        setUp();
        Location loc = new Location(0, 1);
        p1.place(w1.getID(), loc);
        Location w1pos = p1.getWorkerPosition(w1.getID());
        assertTrue(w1pos.equals(loc));
    }
}
