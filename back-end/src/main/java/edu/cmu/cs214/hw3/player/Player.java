package edu.cmu.cs214.hw3.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for player of the game. 
 * 
 * When the user attempts actions, if there is an error, a message will be set to the game instruction,
 * and the user will be allowed to retry that action (no action will happen as a result of a user error).
 */
public abstract class Player {
    
    private Integer id;
    // Using Maps for extensibility
    private Map<Integer, Worker> workers;
    private Map<Integer, Location> workerPositions;

    private static Map<Integer, Player> players = new HashMap<Integer, Player>(); // to access each player

    /**
     * Creates a new {@link Player} instance.
     * Also stores attribute of its {@link Worker} objects' {@link Location}
     *
     * @param id Player id
     * @param w1 The player's first {@link Worker}
     * @param w2 The player's second {@link Worker}
     */
    public Player(int id, Worker w1, Worker w2) {
        this.id = id;
        this.workers = new HashMap<Integer, Worker>();
        this.workers.put(w1.getID(), w1);
        this.workers.put(w2.getID(), w2);
        this.workerPositions = new HashMap<Integer, Location>();
    }

    public static Map<Integer, Player> getPlayers() {
        return players;
    }

    public Integer getID() {
        return this.id;
    }

    public Map<Integer, Worker> getWorkers() {
        return this.workers;
    }

    public Map<Integer, Location> getWorkerPositions() {
        return this.workerPositions;
    }
    
    public Collection<Location> getAllPositions() {
        return this.workerPositions.values();
    }

    public Worker getWorker(Integer wid) {
        return this.workers.get(wid);
    }

    public Location getWorkerPosition(Integer wid) {
        return this.workerPositions.get(wid);
    }

    /**
     * Checks if location is adjacent to any of the player's workers.
     *
     * @param loc The {@link Location} to check
     * @return {@code true} if any of the player's workers is adjacent to the location.
     */
    protected boolean isAdj(Location loc) {
        for (Integer key: this.workers.keySet()) {
            if (getWorker(key).isAdjLocation(loc)) return true;
        }
        return false;
    }

    /**
     * Finds if a worker is at a certain location.
     *
     * @param loc The location
     * @return {@code Integer} the worker's id or
     *                         -1 if none of the current player's workers are at the location.
     */
    public Integer getWorkerFromLocation(Location loc) {
        for (Integer wid: this.workers.keySet()) {
            if (getWorker(wid).getPosition().equals(loc))
                return wid;
        }
        return -1;
    }

    /**
     * Moves worker to location and updates worker positions.
     *
     * @param wid The id of the {@link Worker}
     * @param loc The destination {@link Location}
     */
    public void place(Integer wid, Location loc) {
        Worker worker = getWorker(wid);
        worker.move(loc);
        this.workerPositions.put(wid, loc);
    }

    /**
     * Checks if the win condition has been satisfied: a worker is on a level 3 tower.
     *
     * @param grid The game board
     * @return {@code true} if a player has won the game.
     */
    public boolean checkWin(Grid grid) {
        Collection<Location> plocs = this.getAllPositions();
        for (Location loc: plocs) {
            // checks if any of the player's workers are on a level 3 tower
            if (grid.highest(loc)) return true;
        }
        return false;
    }

    /**
     * Place workers in the beginning 2 locations and switches to the next player's turn.
     * Sets instruction to next action if successful or user error
     *
     * @param loc1 The {@link Location} to put the first worker
     * @param loc2 The {@link Location} to put the second worker
     * @param grid The game board
     * @return {@link String} indicating action success or error
     * @error If the locations are not valid, there will be no action.
     * @error If the locations are equal, there will be no action.
     * @error If the locations are previously occupied, there will be no action.
     */
    public String placeWorkers(Location loc1, Location loc2, Grid grid) {
        if (!loc1.checkValidLocation() || !loc2.checkValidLocation()) {
            return String.format("Player %d: Please input valid location (rows 0-4, cols 0-4)", this.id);
        } else if (loc1.equals(loc2)) {
            return String.format("Player %d: Please input 2 distinct locations", this.id);
        }
        if (!grid.tryPlace(loc1, loc2)) {
            return String.format("Player %d: Please input unoccupied locations", this.id);
        }
        this.place(1, loc1);
        this.place(2, loc2);
        return "success";
    }

    /**
     * Moves player's worker to location.
     * Then checks if the current player has won, and if not, goes to build action.
     * Sets instruction to next action if successful or user error
     *
     * @param curr The current {@link Location} of the worker to move
     * @param next The destination {@link Location}
     * @param grid The game board
     * @return {@link String} indicating action success or error
     * @error If the locations are not valid, there will be no action.
     * @error If the first location selected is not occupied by worker, there will be no action.
     * @error If the location is not adjacent to the worker, there will be no action.
     * @error If the location is previously occupied or unclimbable, there will be no action.
     */
    public String move(Location curr, Location next, Grid grid) {
        if (!curr.checkValidLocation() || !next.checkValidLocation()) {
            return String.format("Player %d: Please input valid location (rows 0-4, cols 0-4)", this.id);
        }
        Integer wid = this.getWorkerFromLocation(curr);
        if (wid == -1) {
            return String.format("Player %d: Please select a worker to move", this.id);
        }
        if (!getWorker(wid).isAdjLocation(next)) {
            return String.format("Player %d: Please input adjacent location", this.id);
        }
        Location prevPos = this.getWorkerPosition(wid);
        if (!grid.tryMove(prevPos, next)) {
            return String.format("Player %d: Please input unoccupied climbable location", this.id);
        }
        this.place(wid, next);
        return "success";
    }

    /**
     * Builds at the location. Then, switches over to other player's turn.
     * Sets instruction to next action if successful or user error
     *
     * @param loc The target {@link Location}
     * @param grid The game board
     * @return {@link String} indicating action success or error
     * @error If the locations are not valid, there will be no action.
     * @error If the location is not adjacent to any of the player's workers, there will be no action.
     * @error If the location is previously occupied, there will be no action.
     */
    public String build(Location loc, Grid grid) {
        if (!loc.checkValidLocation()) {
            return String.format("Player %d: Please input valid location (rows 0-4, cols 0-4)", this.id);
        }
        if (!this.isAdj(loc)) {
            return String.format("Player %d: Please input adjacent location to a worker", this.id);
        }
        if (!grid.tryBuild(loc)) {
            return String.format("Player %d: Grid is occupied at that location or the tower is domed", this.id);
        }
        return "success";
    }

}
