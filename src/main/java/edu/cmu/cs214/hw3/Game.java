package main.java.edu.cmu.cs214.hw3;

import java.util.Dictionary;
import java.util.Hashtable;

// in this implementation, i'm assuming the players are switching off when interacting with the system
public class Game {

    private boolean hasGameEnded;
    private Player.id playerTurn;
    public Grid grid;
    public Dictionary<Player.id, Player> players;

    public Game() {
        Player p1 = new Player(1);
        Player p2 = new Player(2);
        this.players = new Hashtable<>();
        this.players.put(1, p1);
        this.players.put(2, p2);
        this.grid = new Grid();
        this.hasGameEnded = false;
        this.playerTurn = 1;
    }

    private void nextPlayer() {
        if (this.playerTurn == 1) this.playerTurn = 2;
        else this.playerTurn = 1;
    }

    private Player getCurrentPlayer() {
        return this.players.get(this.playerTurn);
    }

    private boolean checkValidLocation(Location loc) {
        int row = loc.row;
        int col = loc.col;
        if (row < 0 || row > 4 || col < 0 || col > 4) return false;
        else return true;
    }

    private boolean checkWin() {
        Player currPlayer = getCurrentPlayer();
        Location[] plocs = currPlayer.getWorkerPositions();
        for (Location loc: plocs) {
            Tower tower = grid.getTower(loc);
            if (tower.getLevel() == 3) this.hasGameEnded = true;
        }
        return this.hasGameEnded;
    }

    public void placeWorkers(Location loc1, Location loc2) {
        if (!checkValidLocation(loc1) || !checkValidLocation(loc2)) {
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

    public void move(Worker.id id, Location loc) {
        if (!checkValidLocation(loc)) {
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
        if (!checkValidLocation(loc)) {
            System.out.println("Please input valid location (rows 0-4, cols 0-4)");
        }
        boolean success = grid.tryBuild(loc);
        if (!success) {
            System.out.println("Grid is occupied at that location.");
        } else if (checkWin) {          // check if current player has won
            this.hasGameEnded = true;
        } else {                        // if player has not won on this turn, switch players
            nextPlayer();
        }
    }
}