package main.java.edu.cmu.cs214.hw3;

import java.util.Dictionary;
import java.util.Hashtable;

import main.java.edu.cmu.cs214.hw3.player.Player;
import main.java.edu.cmu.cs214.hw3.state.Grid;
import main.java.edu.cmu.cs214.hw3.state.Location;

public class Game {

    private boolean hasGameEnded;
    private Integer playerTurn;
    private Grid grid;
    private Dictionary<Integer, Player> players;

    public Game(Player p1, Player p2) {
        this.players = new Hashtable<>();
        this.players.put(p1.getID(), p1);
        this.players.put(p2.getID(), p2);
        this.grid = new Grid();
        this.hasGameEnded = false;
        this.playerTurn = 1;
    }

    public Integer getPlayerTurn() {
        return this.playerTurn;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Dictionary<Integer, Player> getPlayers() {
        return this.players;
    }

    private void nextPlayer() {
        if (this.playerTurn == 1) this.playerTurn = 2;
        else this.playerTurn = 1;
    }

    private Player getCurrentPlayer() {
        return this.players.get(this.playerTurn);
    }

    private boolean checkWin() {
        Player currPlayer = getCurrentPlayer();
        Location[] plocs = currPlayer.getWorkerPositions();
        for (Location loc: plocs) {
            if (grid.highest(loc)) this.hasGameEnded = true;
        }
        return this.hasGameEnded;
    }

    public void placeWorkers(Location loc1, Location loc2) {
        if (!loc1.checkValidLocation() || !loc2.checkValidLocation()) {
            System.out.println("Please input valid location (rows 0-4, cols 0-4)");
            return;
        }
        Player currPlayer = getCurrentPlayer();
        if (!grid.tryOccupy(loc1)) {
            System.out.println("Please input unoccupied location");
            return;
        }
        if (!grid.tryOccupy(loc2)) {
            System.out.println("Please input unoccupied location");
            return;
        }
        currPlayer.place(1, loc1);
        currPlayer.place(2, loc2);
        nextPlayer();
    }

    public void move(Integer id, Location loc) {
        if (!loc.checkValidLocation()) {
            System.out.println("Please input valid location (rows 0-4, cols 0-4)");
        }
        Player currPlayer = getCurrentPlayer();
        if (!currPlayer.isAdjLocation(id, loc)) {
            System.out.println("Please input adjacent location");
            return;
        }
        Location prevPos = currPlayer.getWorkerPosition(id);
        if (!grid.tryMove(prevPos, loc)) {
            System.out.println("Please input unoccupied location");
            return;
        }
        currPlayer.place(id, loc);
    }

    public void build(Location loc) {
        if (!loc.checkValidLocation()) {
            System.out.println("Please input valid location (rows 0-4, cols 0-4)");
        }
        if (!grid.tryBuild(loc)) {
            System.out.println("Grid is occupied at that location.");
        } else if (checkWin()) {          // check if current player has won
            this.hasGameEnded = true;
        } else {                        // if player has not won on this turn, switch players
            nextPlayer();
        }
    }
}