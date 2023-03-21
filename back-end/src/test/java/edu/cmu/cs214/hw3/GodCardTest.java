package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.player.godcards.Demeter;
import edu.cmu.cs214.hw3.player.godcards.Minotaur;
import edu.cmu.cs214.hw3.player.godcards.Pan;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class GodCardTest {

    private Player pan;
    private Player demeter;
    private Player minotaur;
    private Player p;
    private Grid grid;

    @Before
    public void setUp() {
        pan = new Pan(1, new Worker(1), new Worker(2));
        demeter = new Demeter(1, new Worker(1), new Worker(2));
        minotaur = new Minotaur(1, new Worker(1), new Worker(2));
        p = new Player(2, new Worker(1), new Worker(2));
        grid = new Grid();
    }

    @Test
    public void testPan() {
        setUp();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        pan.placeWorkers(loc1, loc2, grid);
        Location step = new Location(1, 0);
        Location high = new Location(0, 1);
        pan.build(step, grid);
        pan.move(loc1, step, grid);
        String ret = pan.move(step, loc2, grid);
        assertFalse(ret == "success");
        pan.build(high, grid);
        pan.build(high, grid);
        ret = pan.move(loc2, high, grid);
        assertFalse(ret == "success");
        pan.move(step, high, grid);
        ret = pan.move(high, loc1, grid);
        assertTrue(ret == "success");
        assertTrue(pan.checkWin(grid));
    }

    @Test
    public void testDemeter() {
        setUp();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        demeter.placeWorkers(loc1, loc2, grid);
        Location b1 = new Location(0, 1);
        String ret = demeter.build(b1, grid);
        assertFalse(ret == "success");
        assertTrue(((Demeter) demeter).getInstruction() == "success");
        ret = demeter.build(b1, grid);
        assertFalse(ret == "success");
        ret = demeter.build(loc1, grid); // cancel optional build
        assertTrue(ret == "success");

        ret = demeter.build(b1, grid);
        assertFalse(ret == "success");
        ret = demeter.build(new Location(1, 0), grid);
        assertTrue(ret == "success");

        Location far = new Location(3, 3);
        ret = demeter.build(far, grid);
        assertFalse(ret == "success");
        assertTrue(((Demeter) demeter).getFirstBuild());
        assertFalse(((Demeter) demeter).getInstruction() == "success");
    }

    @Test
    public void testMinotaur() {
        setUp();
        Player.players.put(1, minotaur);
        Player.players.put(2, p);
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        minotaur.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(2, 2);
        Location loc4 = new Location(0, 1);
        p.placeWorkers(loc3, loc4, grid);
        String ret = minotaur.move(loc1, loc4, grid);
        assertTrue(ret == "success");
        assertTrue(grid.isOccupied(new Location(0, 2)));
        assertTrue(p.getAllPositions().contains(new Location(0, 2)));
        p.build(new Location(0, 3), grid);
        p.build(new Location(0, 3), grid);
        minotaur.move(loc1, loc4, grid);
        minotaur.move(loc4, new Location(0, 2), grid);
        assertTrue(grid.isOccupied(new Location(0, 3)));
        assertTrue(p.getAllPositions().contains(new Location(0, 3)));
        minotaur.move(loc2, loc3, grid);
        assertTrue(grid.isOccupied(new Location(3, 3)));
        minotaur.move(new Location(0, 2), new Location(1, 3), grid);
        ret = minotaur.move(new Location(1, 3), new Location(0, 3), grid);
        assertFalse(ret == "success");
    }
}
