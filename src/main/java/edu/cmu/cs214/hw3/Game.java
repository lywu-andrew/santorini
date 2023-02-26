package main.java.edu.cmu.cs214.hw3;

public class Game {

    private boolean hasGameEnded;
    private Player.id playerTurn;
    public Grid grid;
    public Player p1;
    public Player p2;

    public Game() {
        this.p1 = new Player(1);
        this.p2 = new Player(2);
        this.grid = new Grid();
        this.hasGameEnded = false;
        this.playerTurn = 1;
    }

    public boolean checkWin() {
        // check board for win conditions
        // hasGameEnded = ?;
        return hasGameEnded;
    }

    private void nextPlayer() {
        if (playerTurn == 1) playerTurn = 2;
        else playerTurn = 1;
    }

    public void build(Location loc) {
        boolean success = grid.tryBuild(loc);
        if (!success) {
            System.out.println("Grid is occupied at that location.");
        } else if (checkWin) {          // check if current player has won
            this.hasGameEnded = true;
        } else {                        // if current player has not won on this turn, switch players
            nextPlayer();
        }
    }

    public void move() {
        // should probably not have parameter of workerID bc violates low coupling (law of demeter)
    }
}