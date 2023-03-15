package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.state.Tower;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testNormalGame() {
        setUp();
        assertTrue(game.getPlayerTurn() == 1);
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        game.placeWorkers(loc1, loc2);
        assertTrue(game.getPlayerTurn() == 2);
        Location loc3 = new Location(2, 2);
        Location loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        assertTrue(game.getPlayerTurn() == 1);
        game.move(1, new Location(0, 1));
        game.build(loc1);
        assertTrue(game.getPlayerTurn() == 2);
        assertFalse(game.getHasGameEnded());
        game.move(1, new Location(2, 3));
        game.build(loc3);
        game.move(1, loc1);
        Location step1 = new Location(1, 0);
        game.build(step1);
        game.move(1, loc3);
        Location step2 = new Location(3, 2);
        game.build(step2);
        game.move(2, new Location(0, 1));
        game.build(step1);
        game.move(2, new Location(2, 3));
        game.build(step2);
        game.move(1, step1);
        game.build(loc2);
        game.move(1, step2);
        game.build(loc4);
        game.move(2, new Location(0, 2));
        game.build(loc2);
        game.move(2, new Location(2, 4));
        game.build(loc4);
        game.move(2, new Location(0, 1));
        game.build(loc2);
        game.move(2, new Location(2, 3));
        game.build(loc4);
        assertFalse(game.getHasGameEnded());
        game.move(1, loc2);
        game.build(new Location(0, 2));
        assertTrue(game.getHasGameEnded());
        Location endMove = new Location(2, 4);
        game.move(2, endMove);
        assertFalse(grid.isOccupied(endMove));
        Location endBuild = new Location(1, 4);
        game.build(endBuild);
        Tower t = grid.getTower(endBuild);
        assertTrue(t.getLevel() == 0);
        Location loc5 = new Location(0, 4);
        Location loc6 = new Location(4, 0);
        game.placeWorkers(loc5, loc6);
        assertFalse(grid.isOccupied(loc5));
        assertFalse(grid.isOccupied(loc6));
    }

    @Test
    public void testGameWithMistakes() {
        setUp();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        game.placeWorkers(loc1, loc2);
        assertTrue(game.getPlayerTurn() == 2);
        Location loc3 = new Location(2, 2);
        Location loc4 = new Location(0, 0);
        game.placeWorkers(loc3, loc4);
        assertTrue(game.getPlayerTurn() == 2);
        loc4 = new Location(3, 3);
        game.placeWorkers(loc3, loc4);
        assertTrue(game.getPlayerTurn() == 1);
        game.move(1, new Location(0, 1));
        game.build(new Location(-1, -1));
        assertTrue(game.getPlayerTurn() == 1);
        game.build(loc1);
        assertTrue(game.getPlayerTurn() == 2);
        game.move(1, new Location(3, 3));
        game.move(1, new Location(3, 2));
        game.build(new Location(4, 2));
        assertTrue(game.getPlayerTurn() == 1);
        game.move(2, loc1);
        game.build(new Location(1, 0));
        game.move(1, new Location(3, 1));
        game.build(new Location(4, 2));
        game.move(1, new Location(0, 2));
        game.build(new Location(1, 0));
        game.move(1, new Location(3, 2));
        game.build(new Location(4, 2));
        game.move(2, new Location(1, 0));
        game.build(loc2);
        game.move(1, new Location(3, 1));
        game.build(new Location(4, 2));
        game.move(1, new Location(1, 0));
        game.move(1, loc1);
        game.build(loc2);
        game.move(1, new Location(4, 2));
        game.move(2, new Location(3, 2));
        game.build(new Location(4, 1));
        assertTrue(game.getPlayerTurn() == 1);
    }
}