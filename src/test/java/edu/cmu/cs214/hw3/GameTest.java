package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;
import edu.cmu.cs214.hw3.state.Tower;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Worker w1;
    private Worker w2;
    private Worker w3;
    private Worker w4;
    private Player p1;
    private Player p2;

    @Before
    public void setUp() {
        w1 = new Worker(1);
        w2 = new Worker(2);
        p1 = new Player(1, w1, w2);
        w3 = new Worker(1);
        w4 = new Worker(2);
        p2 = new Player(2, w3, w4);
        game = new Game(p1, p2);
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
        game.move(w1.getID(), new Location(0, 1));
        game.build(loc1);
        assertTrue(game.getPlayerTurn() == 2);
        assertFalse(game.getHasGameEnded());
        game.move(w3.getID(), new Location(2, 3));
        game.build(loc3);
        game.move(w1.getID(), loc1);
        Location step1 = new Location(1, 0);
        game.build(step1);
        game.move(w3.getID(), loc3);
        Location step2 = new Location(3, 2);
        game.build(step2);
        game.move(w2.getID(), new Location(0, 1));
        game.build(step1);
        game.move(w4.getID(), new Location(2, 3));
        game.build(step2);
        game.move(w1.getID(), step1);
        game.build(loc2);
        game.move(w3.getID(), step2);
        game.build(loc4);
        game.move(w2.getID(), new Location(0, 2));
        game.build(loc2);
        game.move(w4.getID(), new Location(2, 4));
        game.build(loc4);
        game.move(w2.getID(), new Location(0, 1));
        game.build(loc2);
        game.move(w4.getID(), new Location(2, 3));
        game.build(loc4);
        assertFalse(game.getHasGameEnded());
        game.move(w1.getID(), loc2);
        game.build(new Location(0, 2));
        assertTrue(game.getHasGameEnded());
        Location endMove = new Location(2, 4);
        game.move(w4.getID(), endMove);
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
        game.move(w1.getID(), new Location(0, 1));
        game.build(new Location(-1, -1));
        assertTrue(game.getPlayerTurn() == 1);
        game.build(loc1);
        assertTrue(game.getPlayerTurn() == 2);
        game.move(w3.getID(), new Location(3, 3));
        game.move(w3.getID(), new Location(3, 2));
        game.build(new Location(4, 2));
        assertTrue(game.getPlayerTurn() == 1);
        game.move(w2.getID(), loc1);
        game.build(new Location(1, 0));
        game.move(w3.getID(), new Location(3, 1));
        game.build(new Location(4, 2));
        game.move(w1.getID(), new Location(0, 2));
        game.build(new Location(1, 0));
        game.move(w3.getID(), new Location(3, 2));
        game.build(new Location(4, 2));
        game.move(w2.getID(), new Location(1, 0));
        game.build(loc2);
        game.move(w3.getID(), new Location(3, 1));
        game.build(new Location(4, 2));
        game.move(w1.getID(), new Location(1, 0));
        game.move(w1.getID(), loc1);
        game.build(loc2);
        game.move(w3.getID(), new Location(4, 2));
        game.move(w4.getID(), new Location(3, 2));
        game.build(new Location(4, 1));
        assertTrue(game.getPlayerTurn() == 1);
    }
}