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
        assertTrue(game.getNextAction() == 3);
        game.pickCard(3);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 3);
        game.pickCard(3);
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 0);
        Grid grid = game.getGrid();
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        game.selectLocation(loc1);
        game.selectLocation(loc2);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 0);
        Location loc3 = new Location(2, 2);
        Location loc4 = new Location(3, 3);
        game.selectLocation(loc3);
        game.selectLocation(loc4);
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(loc1);
        game.selectLocation(new Location(0, 1));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(loc1);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        assertFalse(game.getHasGameEnded());
        game.selectLocation(loc3);
        game.selectLocation(new Location(2, 3));
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(loc3);
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(0, 1));
        game.selectLocation(loc1);
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        Location step1 = new Location(1, 0);
        game.selectLocation(step1);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(2, 3));
        game.selectLocation(loc3);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 2);
        Location step2 = new Location(3, 2);
        game.selectLocation(step2);
        game.selectLocation(loc2);
        game.selectLocation(new Location(0, 1));
        game.selectLocation(step1);
        game.selectLocation(loc4);
        game.selectLocation(new Location(2, 3));
        game.selectLocation(step2);
        game.selectLocation(loc1);
        game.selectLocation(step1);
        game.selectLocation(loc2);
        game.selectLocation(loc3);
        game.selectLocation(step2);
        game.selectLocation(loc4);
        game.selectLocation(new Location(0, 1));
        game.selectLocation(new Location(0, 2));
        game.selectLocation(loc2);
        game.selectLocation(new Location(2, 3));
        game.selectLocation(new Location(2, 4));
        game.selectLocation(loc4);
        game.selectLocation(new Location(0, 2));
        game.selectLocation(new Location(0, 1));
        game.selectLocation(loc2);
        game.selectLocation(new Location(2, 4));
        game.selectLocation(new Location(2, 3));
        game.selectLocation(loc4);
        assertFalse(game.getHasGameEnded());
        game.selectLocation(step1);
        game.selectLocation(loc2);
        game.selectLocation(new Location(0, 2));
        assertTrue(game.getHasGameEnded());
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == -1);
        Location endMove = new Location(2, 4);
        game.selectLocation(new Location(2, 3));
        game.selectLocation(endMove);
        assertFalse(grid.isOccupied(endMove));
        Location endBuild = new Location(1, 4);
        game.selectLocation(endBuild);
        Tower t = grid.getTower(endBuild);
        assertTrue(t.getLevel() == 0);
        Location loc5 = new Location(0, 4);
        Location loc6 = new Location(4, 0);
        game.selectLocation(loc5);
        game.selectLocation(loc6);
        assertFalse(grid.isOccupied(loc5));
        assertFalse(grid.isOccupied(loc6));
    }

    @Test
    public void testGameWithMistakes() {
        setUp();
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 3);
        game.pickCard(3);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 3);
        game.pickCard(3);
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        game.selectLocation(loc1);
        game.selectLocation(loc2);
        assertTrue(game.getPlayerTurn() == 2);
        Location loc3 = new Location(2, 2);
        Location loc4 = new Location(0, 0);
        game.selectLocation(loc3);
        game.selectLocation(loc4);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 0);
        loc4 = new Location(3, 3);
        game.selectLocation(loc3);
        game.selectLocation(loc4);
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getPlayerTurn() == 1);
        game.selectLocation(loc1);
        game.selectLocation(new Location(0, 1));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(new Location(-1, -1));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(loc1);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(loc3);
        game.selectLocation(new Location(3, 3));
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(loc3);
        game.selectLocation(new Location(3, 2));
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(new Location(4, 2));
        assertTrue(game.getPlayerTurn() == 1);
        game.selectLocation(loc2);
        game.selectLocation(loc1);
        game.selectLocation(loc1);
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(loc3);
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(new Location(1, 0));
        game.selectLocation(new Location(3, 2));
        game.selectLocation(new Location(3, 1));
        game.selectLocation(new Location(4, 2));
        game.selectLocation(new Location(0, 1));
        game.selectLocation(new Location(0, 2));
        game.selectLocation(new Location(1, 0));
        game.selectLocation(new Location(3, 1));
        game.selectLocation(new Location(3, 2));
        game.selectLocation(new Location(4, 2));
        game.selectLocation(loc1);
        game.selectLocation(new Location(1, 0));
        game.selectLocation(loc2);
        game.selectLocation(new Location(3, 2));
        game.selectLocation(new Location(4, 2));
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(3, 2));
        game.selectLocation(new Location(3, 1));
        game.selectLocation(new Location(4, 2));
        game.selectLocation(new Location(0, 2));
        game.selectLocation(new Location(1, 0));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(0, 2));
        game.selectLocation(loc2);
        game.selectLocation(loc1);
        game.selectLocation(new Location(3, 1));
        game.selectLocation(new Location(4, 2));
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(3, 3));
        game.selectLocation(new Location(3, 2));
        game.selectLocation(new Location(4, 1));
        assertTrue(game.getPlayerTurn() == 1);
    }

    @Test
    public void testGodCard() {
        setUp();
        game.pickCard(0); // Demeter
        game.pickCard(3);
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        game.selectLocation(loc1);
        game.selectLocation(loc2);
        Location loc3 = new Location(2, 2);
        Location loc4 = new Location(3, 3);
        game.selectLocation(loc3);
        game.selectLocation(loc4);
        game.selectLocation(loc1);
        game.selectLocation(new Location(0, 1));
        game.selectLocation(loc1);
        assertTrue(game.getNextAction() == 2); // optional build
        game.selectLocation(loc1);
        assertTrue(game.getNextAction() == 2); // error same location
        game.selectLocation(new Location(0, 2));
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(loc3);
        game.selectLocation(new Location(2, 3));
        game.selectLocation(new Location(2, 4));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(0, 1));
        game.selectLocation(loc1);
        game.selectLocation(new Location(1, 0));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(loc1);             // cancel optional build
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(loc4);
        game.selectLocation(new Location(2, 4));
        game.selectLocation(loc4);
        game.selectLocation(loc2);
        game.selectLocation(new Location(2, 1));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(new Location(1, 0));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(new Location(2, 0));
    }

    @Test
    public void testGodCards() {
        setUp();
        game.pickCard(1); // Minotaur
        game.pickCard(2); // Pan
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);
        game.selectLocation(loc1);
        game.selectLocation(loc2);
        Location loc3 = new Location(2, 2);
        Location loc4 = new Location(3, 3);
        game.selectLocation(loc3);
        game.selectLocation(loc4);
        game.selectLocation(loc2);
        game.selectLocation(loc3);
        assertTrue(game.getPlayerTurn() == 1);
        assertFalse(game.getNextAction() == 2); // worker on loc4
        game.selectLocation(loc2);
        game.selectLocation(new Location(2, 1));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        game.selectLocation(new Location(1, 0));
        game.selectLocation(loc4);
        game.selectLocation(new Location(3, 2));
        game.selectLocation(loc4);
        game.selectLocation(new Location(2, 1));
        game.selectLocation(loc3);
        game.selectLocation(loc2);
        game.selectLocation(new Location(2, 3));
        game.selectLocation(loc4);
        game.selectLocation(new Location(4, 3));
        game.selectLocation(loc3);
        game.selectLocation(loc4);
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 2);
        assertTrue(game.getPlayer(2).getAllPositions().contains(new Location(4, 4)));
        game.selectLocation(new Location(1, 0));
        game.selectLocation(new Location(3, 2));
        game.selectLocation(new Location(4, 3));
        game.selectLocation(new Location(3, 4));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(loc1);
        game.selectLocation(loc2);
        game.selectLocation(loc1);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(4, 3));
        game.selectLocation(new Location(4, 2));
        game.selectLocation(new Location(4, 3));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(loc4);
        game.selectLocation(new Location(3, 4));
        game.selectLocation(loc1);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(4, 2));
        game.selectLocation(loc4);
        game.selectLocation(new Location(4, 2));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(loc2);
        game.selectLocation(new Location(1, 0));
        game.selectLocation(loc1);
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(loc4);
        game.selectLocation(new Location(4, 3));
        game.selectLocation(new Location(4, 2));
        assertTrue(game.getPlayerTurn() == 1);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(3, 4));
        game.selectLocation(loc4);
        game.selectLocation(new Location(3, 4));
        assertTrue(game.getPlayerTurn() == 2);
        assertTrue(game.getNextAction() == 1);
        game.selectLocation(new Location(4, 3));
        game.selectLocation(new Location(3, 2));
        assertTrue(game.getHasGameEnded());
    }
}