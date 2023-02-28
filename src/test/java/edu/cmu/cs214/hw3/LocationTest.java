package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class LocationTest {

    private Location loc1;
    private Location loc2;
    private Location loc3;
    
    @Before
    public void setUp() {
        this.loc1 = new Location(1, 1);
        this.loc2 = new Location(1, 1);
        this.loc3 = new Location(2, 2);
    }

    @Test
    public void testCheckValidLocation() {
        assertTrue(this.loc1.checkValidLocation());
        Location loc = new Location(-1, -1);
        assertFalse(loc.checkValidLocation());
        loc = new Location(-1, 10);
        assertFalse(loc.checkValidLocation());
        loc = new Location(10, 10);
        assertFalse(loc.checkValidLocation());
        loc = new Location(10, -1);
        assertFalse(loc.checkValidLocation());
        loc = new Location(0, 10);
        assertFalse(loc.checkValidLocation());
        loc = new Location(0, -1);
        assertFalse(loc.checkValidLocation());
        loc = new Location(-1, 0);
        assertFalse(loc.checkValidLocation());
        loc = new Location(10, 0);
        assertFalse(loc.checkValidLocation());
    }

    @Test
    public void testEquals() {
        setUp();
        assertTrue(this.loc1.equals(this.loc2));
        assertFalse(this.loc1.equals(this.loc3));
    }

    @Test
    public void testAdj() {
        setUp();
        assertTrue(this.loc1.adjacent(this.loc2));
        assertTrue(this.loc1.adjacent(this.loc3));
        Location loc = new Location(4, 1);
        assertFalse(this.loc1.adjacent(loc));
        loc = new Location(1, 4);
        assertFalse(this.loc1.adjacent(loc));
    }
}
