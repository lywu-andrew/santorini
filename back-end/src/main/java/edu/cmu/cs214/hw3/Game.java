package edu.cmu.cs214.hw3;

import java.util.Collection;

import edu.cmu.cs214.hw3.player.Player;
import edu.cmu.cs214.hw3.player.Worker;
import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * Controller class for Santorini game. 
 * This implementation is assuming the players are switching off when interacting with the system.
 * When the user invokes the game methods, if there is an error, a message will be output to the console,
 * and the user will be allowed to retry that method (no action will happen as a result of a user error).
 */
public class Game {

    private boolean hasGameEnded;
    private Integer playerTurn;
    private Grid grid;
    private Player p1;
    private Player p2;
    private Integer nextAction; // -1 = none, 0 = place workers, 1 = move, 2 = build

    /**
     * Creates a new {@link Game} instance, which contains the players.
     * Also stores attribute of if game has ended and the current player's turn.
     *
     * @param p1 The first {@link Player}
     * @param p2 The second {@link Player}
     */
    public Game() {
        this.p1 = new Player(1, new Worker(1), new Worker(2));
        this.p2 = new Player(2, new Worker(1), new Worker(2));
        this.grid = new Grid();
        this.hasGameEnded = false;
        this.playerTurn = p1.getID();
        this.nextAction = 0;
    }

    public Integer getPlayerTurn() {
        return this.playerTurn;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Player getPlayer(Integer id) {
        if (id == 1) return p1;
        return p2;
    }

    public Integer getNextAction() {
        return this.nextAction;
    }

    public boolean getHasGameEnded() {
        return this.hasGameEnded;
    }

    private void nextPlayer() {
        if (this.playerTurn == p1.getID()) this.playerTurn = p2.getID();
        else this.playerTurn = p1.getID();
    }

    private Player getCurrentPlayer() {
        if (this.playerTurn == 1) return p1;
        return p2;
    }

    /**
     * Checks if the win condition has been satisfied: a worker is on a level 3 tower.
     *
     * @return {@code true} if a player has won the game and prints a message.
     */
    public boolean checkWin() {
        Player currPlayer = getCurrentPlayer();
        Collection<Location> plocs = currPlayer.getAllPositions();
        for (Location loc: plocs) {
            // checks if any of the player's workers are on a level 3 tower
            if (grid.highest(loc))  {
                this.hasGameEnded = true;
                this.nextAction = -1;
                System.out.printf("Player %d has won!\n", playerTurn);
            }
        }
        return this.hasGameEnded;
    }

    /**
     * Place workers in the beginning 2 locations and switches to the next player's turn.
     *
     * @param loc1 The {@link Location} to put the first worker
     * @param loc2 The {@link Location} to put the second worker
     * @error If game has ended, there will be no action.
     * @error If the locations are not valid, there will be no action.
     * @error If the locations are equal, there will be no action.
     * @error If the locations are previously occupied, there will be no action.
     */
    public void placeWorkers(Location loc1, Location loc2) {
        if (this.hasGameEnded) {
            System.out.printf("Player %d has won!\n", playerTurn);
            return;
        }
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
        if (getPlayerTurn() == 2) this.nextAction = 1;
        nextPlayer();
        return;
    }

    /**
     * Moves player's worker to location.
     *
     * @param curr The current {@link Location} of the worker to move
     * @param next The destination {@link Location}
     * @error If game has ended, there will be no action.
     * @error If the locations are not valid, there will be no action.
     * @error If the first location selected is not occupied by worker, there will be no action.
     * @error If the location is not adjacent to the worker, there will be no action.
     * @error If the location is previously occupied or unclimbable, there will be no action.
     */
    public void move(Location prev, Location next) {
        if (this.hasGameEnded) {
            System.out.printf("Player %d has won!\n", playerTurn);
            return;
        }
        if (!prev.checkValidLocation() || !next.checkValidLocation()) {
            System.out.println("Please input valid location (rows 0-4, cols 0-4)");
            return;
        }
        Player currPlayer = getCurrentPlayer();
        Integer wid = currPlayer.getWorkerFromLocation(prev);
        if (wid == -1) {
            System.out.println("Please select a worker to move");
            return;
        }
        if (!currPlayer.isAdjLocation(wid, next)) {
            System.out.println("Please input adjacent location");
            return;
        }
        Location prevPos = currPlayer.getWorkerPosition(wid);
        if (!grid.tryMove(prevPos, next)) {
            System.out.println("Please input unoccupied climbable location");
            return;
        }
        currPlayer.place(wid, next);
        this.nextAction = 2;
        return;
    }

    /**
     * Builds at the location. Then checks if the current player has won, and if not, switches to the next player's turn.
     *
     * @param loc The target {@link Location}
     * @error If game has ended, there will be no action.
     * @error If the locations are not valid, there will be no action.
     * @error If the location is not adjacent to any of the player's workers, there will be no action.
     * @error If the location is previously occupied, there will be no action.
     */
    public void build(Location loc) {
        if (this.hasGameEnded) {
            System.out.printf("Player %d has won!\n", playerTurn);
            return;
        }
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
        } else if (!checkWin()) {          // check if current player has won
            nextPlayer();                  // if player has not won on this turn, switch players
        }
        this.nextAction = 1;
        return;
    }
}