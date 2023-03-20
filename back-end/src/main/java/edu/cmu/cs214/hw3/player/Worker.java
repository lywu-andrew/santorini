package edu.cmu.cs214.hw3.player;

import edu.cmu.cs214.hw3.state.Location;

/**
 * Class for workers of the players.
 */
public class Worker {
    
    private Integer id;
    private Location position;

    /**
     * Creates a new {@link Worker} instance.
     *
     * @param id Worker id
     */
    public Worker(Integer id) {
        this.id = id;
        this.position = new Location(-1, -1); // default: hasn't been placed
    }

    public Integer getID() {
        return this.id;
    }

    public Location getPosition() {
        return this.position;
    }

    /**
     * Checks if location is adjacent to worker.
     *
     * @param loc The {@link Location} to check
     * @return {@code true} if worker is adjacent to location.
     */
    public boolean isAdjLocation(Location loc) {
        if (loc.adjacent(position)) return true;
        else return false;
    }

    /**
     * Sets the worker's position to location.
     *
     * @param newpos {@link Location} The destination location.
     */
    public void move(Location newpos) {
        this.position = newpos;
    }
}
