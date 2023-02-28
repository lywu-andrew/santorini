package edu.cmu.cs214.hw3.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for player of the game.
 */
public class Player {
    
    private Integer id;
    // Using Dictionaries for extensibility
    private Dictionary<Integer, Worker> workers;
    private Dictionary<Integer, Location> workerPositions;

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
        this.workers = new Hashtable<>();
        this.workers.put(w1.getID(), w1);
        this.workers.put(w2.getID(), w2);
        this.workerPositions = new Hashtable<>();
    }

    public Integer getID() {
        return this.id;
    }

    public Dictionary<Integer, Worker> getWorkers() {
        return this.workers;
    }

    public Dictionary<Integer, Location> getWorkerPositions() {
        return this.workerPositions;
    }
    
    public ArrayList<Location> getAllPositions() {
        return Collections.list(this.workerPositions.elements());
    }

    private Worker getWorker(Integer wid) {
        return this.workers.get(wid);
    }

    public Location getWorkerPosition(Integer wid) {
        return this.workerPositions.get(wid);
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
        ArrayList<Integer> keys = Collections.list(this.workers.keys());
        for (Integer key: keys) {
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
