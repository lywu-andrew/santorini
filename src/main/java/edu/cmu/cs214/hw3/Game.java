package edu.cmu.cs214.hw3;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * This implementation is assuming the players are switching off when interacting with the system.
 *
 */
public class Game {

    private boolean hasGameEnded;
    private Integer playerTurn;
    private Grid grid;
    private Dictionary<Integer, Player> players;
    private Integer p1ID;
    private Integer p2ID;

    public Game(Player p1, Player p2) {
        this.p1ID = p1.getID();
        this.p2ID = p2.getID();
        this.players = new Hashtable<>();
        this.players.put(p1ID, p1);
        this.players.put(p2ID, p2);
        this.grid = new Grid();
        this.hasGameEnded = false;
        this.playerTurn = p1.getID();
    }

    public Integer getPlayerTurn() {
        return this.playerTurn;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Player getPlayer(Integer id) {
        return this.players.get(id);
    }

    public Dictionary<Integer, Player> getPlayers() {
        return this.players;
    }
    
    public boolean getHasGameEnded() {
        return this.hasGameEnded;
    }

    private void nextPlayer() {
        if (this.playerTurn == p1ID) this.playerTurn = p2ID;
        else this.playerTurn = p1ID;
    }

    private Player getCurrentPlayer() {
        return this.players.get(this.playerTurn);
    }

    private boolean checkWin() {
        Player currPlayer = getCurrentPlayer();
        ArrayList<Location> plocs = currPlayer.getAllPositions();
        for (Location loc: plocs) {
            if (grid.highest(loc)) this.hasGameEnded = true;
        }
        return this.hasGameEnded;
    }

    public void placeWorkers(Location loc1, Location loc2) {
        if (!loc1.checkValidLocation() || !loc2.checkValidLocation()) {
            System.out.println("Please input valid location (rows 0-4, cols 0-4)");
            return;
        } else if (loc1.equals(loc2)) {
            System.out.println("Please input 2 distinct locations");
            return;
        }
        Player currPlayer = getCurrentPlayer();
        if (!grid.tryPlace(loc1, loc2)) {
            System.out.println("Please input unoccupied locations");
            return;
        }
        currPlayer.place(1, loc1);
        currPlayer.place(2, loc2);
        nextPlayer();
    }

    public void move(Integer wid, Location loc) {
        if (!loc.checkValidLocation()) {
            System.out.println("Please input valid location (rows 0-4, cols 0-4)");
            return;
        }
        Player currPlayer = getCurrentPlayer();
        if (!currPlayer.isAdjLocation(wid, loc)) {
            System.out.println("Please input adjacent location");
            return;
        }
        Location prevPos = currPlayer.getWorkerPosition(wid);
        if (!grid.tryMove(prevPos, loc)) {
            System.out.println("Please input unoccupied climbable location");
            return;
        }
        currPlayer.place(wid, loc);
    }

    public void build(Location loc) {
        if (!loc.checkValidLocation()) {
            System.out.println("Please input valid location (rows 0-4, cols 0-4)");
            return;
        }
        Player currPlayer = getCurrentPlayer();
        if (!currPlayer.isAdj(loc)) {
            System.out.println("Please input adjacent location to a worker");
            return;
        }
        if (!grid.tryBuild(loc)) {
            System.out.println("Grid is occupied at that location or the tower is domed.");
            return;
        } else if (checkWin()) {          // check if current player has won
            this.hasGameEnded = true;
        } else {                        // if player has not won on this turn, switch players
            nextPlayer();
        }
    }
}