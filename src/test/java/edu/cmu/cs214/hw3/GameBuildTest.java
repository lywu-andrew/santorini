package edu.cmu.cs214.hw3;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

import static org.junit.Assert.*;

public class GameBuildTest {

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
    public void testBuild() {
        setUp();
    }
}