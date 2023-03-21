package edu.cmu.cs214.hw3.player;

import java.util.Collection;
import java.util.Map;

import edu.cmu.cs214.hw3.state.Grid;
import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for default player
 */
public class DefaultPlayer extends Player {

    /**
     * Creates a new {@link Player} instance.
     * Also stores attribute of its {@link Worker} objects' {@link Location}
     *
     * @param id Player id
     * @param w1 The player's first {@link Worker}
     * @param w2 The player's second {@link Worker}
     */
    public DefaultPlayer(int id, Worker w1, Worker w2) {
        super(id, w1, w2);
    }

    public static Map<Integer, Player> getPlayers() {
        return players;
    }

    public Integer getID() {
        return super.getID();
    }

    public Map<Integer, Worker> getWorkers() {
        return super.getWorkers();
    }

    public Map<Integer, Location> getWorkerPositions() {
        return super.getWorkerPositions();
    }
    
    public Collection<Location> getAllPositions() {
        return super.getAllPositions();
    }

    public Worker getWorker(Integer wid) {
        return super.getWorker(wid);
    }

    public Location getWorkerPosition(Integer wid) {
        return super.getWorkerPosition(wid);
    }

    /**
     * Checks if location is adjacent to any of the player's workers.
     *
     * @param loc The {@link Location} to check
     * @return {@code true} if any of the player's workers is adjacent to the location.
     */
    protected boolean isAdj(Location loc) {
        return super.isAdj(loc);
    }

    /**
     * Finds if a worker is at a certain location.
     *
     * @param loc The location
     * @return {@code Integer} the worker's id or
     *                         -1 if none of the current player's workers are at the location.
     */
    public Integer getWorkerFromLocation(Location loc) {
        return super.getWorkerFromLocation(loc);
    }

    /**
     * Moves worker to location and updates worker positions.
     *
     * @param wid The id of the {@link Worker}
     * @param loc The destination {@link Location}
     */
    public void place(Integer wid, Location loc) {
        super.place(wid, loc);
    }

    /**
     * Checks if the win condition has been satisfied: a worker is on a level 3 tower.
     *
     * @param grid The game board
     * @return {@code true} if a player has won the game.
     */
    public boolean checkWin(Grid grid) {
        return super.checkWin(grid);
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
        return super.placeWorkers(loc1, loc2, grid);
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
        return super.move(curr, next, grid);
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
        return super.build(loc, grid);
    }

}
