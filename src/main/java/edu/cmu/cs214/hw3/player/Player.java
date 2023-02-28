package edu.cmu.cs214.hw3.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

import edu.cmu.cs214.hw3.state.Location;

public class Player {
    
    private Integer id;
    private Dictionary<Integer, Worker> workers;
    private Dictionary<Integer, Location> workerPositions;

    public Player(int id, Worker w1, Worker w2) {
        this.id = id;
        this.workers = new Hashtable<>();
        this.workers.put(w1.getID(), w1);
        this.workers.put(w2.getID(), w2);
        this.workerPositions = new Hashtable<>(); //new Location[this.workers.size()];
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

    // is this an adj location to the worker
    public boolean isAdjLocation(Integer wid, Location loc) {
        Location pos = getWorkerPosition(wid);
        if (loc.adjacent(pos)) return true;
        else return false;
    }

    public boolean isAdj(Location loc) {
        ArrayList<Integer> keys = Collections.list(this.workers.keys());
        for (Integer key: keys) {
            if (isAdjLocation(key, loc)) return true;
        }
        return false;
    }

    public void place(Integer wid, Location loc) {
        Worker worker = getWorker(wid);
        worker.move(loc);
        this.workerPositions.put(wid, loc);
    }

}
