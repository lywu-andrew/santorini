package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.state.Tower;
import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class TowerTest {

    private Tower t1;
    private Tower t2;

    @Before
    public void setUp() {
        Location loc1 = new Location(0, 0);
        this.t1 = new Tower(loc1);
        Location loc2 = new Location(1, 1);
        this.t2 = new Tower(loc2);
    }

    @Test
    public void testBuild() {
        setUp();
        t1.build();
        assertTrue(t1.getLevel() == 1);
        t1.build();
        assertTrue(t1.getLevel() == 2);
        t1.build();
        assertTrue(t1.getLevel() == 3);
        t1.build();
        assertTrue(t1.getLevel() == 3);
        assertTrue(t1.isDomed());
    }

    @Test
    public void testIsClimbable() {
        setUp();
        assertTrue(t1.isClimbable(t2));
        t1.build();
        assertTrue(t1.isClimbable(t2));
        t1.build();
        assertFalse(t1.isClimbable(t2));
    }
}
