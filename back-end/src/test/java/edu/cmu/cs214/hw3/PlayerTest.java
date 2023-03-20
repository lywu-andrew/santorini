package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.state.Tower;

import static org.junit.Assert.*;

public class PlayerTest {

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
    public void testNormalActions() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        String res = p1.placeWorkers(loc1, loc2, grid);
        assertTrue(res == "success");
        Location loc3 = new Location(2, 2);
        Location loc4 = new Location(3, 3);
        res = p2.placeWorkers(loc3, loc4, grid);
        assertTrue(res == "success");
        res = p1.move(loc1, new Location(0, 1), grid);
        assertTrue(res == "success");
        res = p1.build(loc1, grid);
        assertTrue(res == "success");
        res = p2.move(loc3, new Location(2, 3), grid);
        assertTrue(res == "success");
        res = p2.build(loc3, grid);
        assertTrue(res == "success");
        p1.move(new Location(0, 1), loc1, grid);
        Location step1 = new Location(1, 0);
        p1.build(step1, grid);
        p2.move(new Location(2, 3), loc3, grid);
        Location step2 = new Location(3, 2);
        p2.build(step2, grid);
        p1.move(loc2, new Location(0, 1), grid);
        p1.build(step1, grid);
        p2.move(loc4, new Location(2, 3), grid);
        p2.build(step2, grid);
        p1.move(loc1, step1, grid);
        p1.build(loc2, grid);
        p2.move(loc3, step2, grid);
        p2.build(loc4, grid);
        p1.move(new Location(0, 1), new Location(0, 2), grid);
        p1.build(loc2, grid);
        p2.move(new Location(2, 3), new Location(2, 4), grid);
        p2.build(loc4, grid);
        p1.move(new Location(0, 2), new Location(0, 1), grid);
        p1.build(loc2, grid);
        p2.move(new Location(2, 4), new Location(2, 3), grid);
        p2.build(loc4, grid);
        p1.move(step1, loc2, grid);
        p1.build(new Location(0, 2), grid);
        assertTrue(p1.checkWin(grid));
        Location endMove = new Location(2, 4);
        p2.move(new Location(2, 3), endMove, grid);
        assertTrue(grid.isOccupied(endMove));
        Location endBuild = new Location(1, 4);
        p2.build(endBuild, grid);
        Tower t = grid.getTower(endBuild);
        assertTrue(t.getLevel() == 1);
        Location loc5 = new Location(0, 4);
        Location loc6 = new Location(4, 0);
        p2.placeWorkers(loc5, loc6, grid);
        assertTrue(grid.isOccupied(loc5));
        assertTrue(grid.isOccupied(loc6));
    }

    @Test
    public void testActionsWithMistakes() {
        setUp();
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        p1.placeWorkers(loc1, loc2, grid);
        Location loc3 = new Location(2, 2);
        Location loc4 = new Location(0, 0);
        String res = p2.placeWorkers(loc3, loc4, grid);
        assertFalse(res == "success");
        loc4 = new Location(3, 3);
        p2.placeWorkers(loc3, loc4, grid);
        p1.move(loc1, new Location(0, 1), grid);
        res = p1.build(new Location(-1, -1), grid);
        assertFalse(res == "success");
        p1.build(loc1, grid);
        res = p2.move(loc3, new Location(3, 3), grid);
        assertFalse(res == "success");
        p2.move(loc3, new Location(3, 2), grid);
        p2.build(new Location(4, 2), grid);
        p1.move(loc2, loc1, grid);
        res = p1.build(loc1, grid);
        assertFalse(res == "success");
        res = p1.build(loc3, grid);
        assertFalse(res == "success");
        p1.build(new Location(1, 0), grid);
        p2.move(new Location(3, 2), new Location(3, 1), grid);
        p2.build(new Location(4, 2), grid);
        p1.move(new Location(0, 1), new Location(0, 2), grid);
        p1.build(new Location(1, 0), grid);
        p2.move(new Location(3, 1), new Location(3, 2), grid);
        p2.build(new Location(4, 2), grid);
        p1.move(loc1, new Location(1, 0), grid);
        p1.build(loc2, grid);
        res = p2.move(new Location(3, 2), new Location(4, 2), grid);
        assertFalse(res == "success");
        p2.move(new Location(3, 2), new Location(3, 1), grid);
        p2.build(new Location(4, 2), grid);
        res = p1.move(new Location(0, 2), new Location(1, 0), grid);
        assertFalse(res == "success");
        p1.move(new Location(0, 2), loc2, grid);
        p1.build(loc1, grid);
        res = p2.move(new Location(3, 1), new Location(4, 2), grid);
        assertFalse(res == "success");
        p2.move(new Location(3, 3), new Location(3, 2), grid);
        p2.build(new Location(4, 1), grid);
        p1.move(loc2, new Location(0, 1), grid);
        p1.build(loc1, grid);
        p1.move(new Location(1, 0), loc2, grid);
        p1.build(loc1, grid);
        p1.move(loc2, new Location(1, 0), grid);
        res = p1.move(new Location(1, 0), loc1, grid);
        assertFalse(res == "success");
    }
}