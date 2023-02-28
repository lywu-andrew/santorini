package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.player.Worker;

import static org.junit.Assert.*;

public class WorkerTest {

    private Worker w1;
    
    @Before
    public void setUp() {
        this.w1 = new Worker(1);
    }

    @Test
    public void testMove() {
        Location next = new Location(0, 0);
        w1.move(next);
        Location pos = w1.getPosition();
        assertTrue(pos.equals(next));
    }
}
