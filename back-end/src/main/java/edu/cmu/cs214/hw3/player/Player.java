package edu.cmu.cs214.hw3.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for player of the game.
 */
public class Player {
    
    private Integer id;
    // Using Maps for extensibility
    private Map<Integer, Worker> workers;
    private Map<Integer, Location> workerPositions;

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

    public Integer getWorkerFromLocation(Location loc) {
        for (Integer wid: this.workers.keySet()) {
            if (getWorker(wid).getPosition().equals(loc))
                return wid;
        }
        return -1;
    }

    /**
     * Checks if location is adjacent to worker.
     *
     * @param wid The id of the {@link Worker}
     * @param loc The {@link Location} to check
     * @return {@code true} if the worker is adjacent to the location.
     */
    public boolean isAdjLocation(Integer wid, Location loc) {
        Location pos = getWorkerPosition(wid);
        if (loc.adjacent(pos)) return true;
        else return false;
    }

    /**
     * Checks if location is adjacent to any of the player's workers.
     *
     * @param loc The {@link Location} to check
     * @return {@code true} if any of the player's workers is adjacent to the location.
     */
    public boolean isAdj(Location loc) {
        for (Integer key: this.workers.keySet()) {
            if (isAdjLocation(key, loc)) return true;
        }
        return false;
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

}
